/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import dao.ContributorDAO;
import dao.JpaUtil;
import dao.PersonDAO;
import dao.StudentDAO;
import dao.SupportDAO;
import exceptions.CanNotResigneTheAttribute;
import exceptions.EntityDoesNotExist;
import exceptions.IdDontMatchWithForgienKey;
import exceptions.QueryReturnMoreThanOneResult;
import exceptions.QueryReturnZeroResult;
import metier.modele.Contributor;
import metier.modele.Person;
import metier.modele.Statistic;
import metier.modele.Student;
import metier.modele.Support;
import metier.modele.enums.ESchoolSubject;
import metier.modele.enums.EStatus;
import util.DateManager;
import static util.Message.envoyerMail;
import static util.Message.envoyerNotification;

/**
 *
 * @author rkhedair
 */
public class UserService {
      
    public Person getUserById(Long id) throws EntityDoesNotExist{
        PersonDAO personDAO = new PersonDAO();
        Person p;
        try {
            JpaUtil.creerContextePersistance();
            p = personDAO.findByID(id);
        } catch (IllegalArgumentException e) {
            throw new EntityDoesNotExist();
        }

        JpaUtil.fermerContextePersistance();
        return p;
    }

    public List<String> getSubjects(){
        return Arrays.stream(ESchoolSubject.values())
                                        .map(Enum::name)
                                        .collect(Collectors.toList());
    }

    public Support getSupportToDo(Long contributorId) throws QueryReturnZeroResult, QueryReturnMoreThanOneResult {
        JpaUtil.creerContextePersistance();
        SupportDAO supportDAO = new SupportDAO();

        Support s = null;

        try {
            s = supportDAO.findToDo(contributorId);
    
            JpaUtil.fermerContextePersistance();
        } catch (NonUniqueResultException e) {
            JpaUtil.fermerContextePersistance();
            throw new QueryReturnMoreThanOneResult("The query returned more than one result, expected only one.");
        } catch (NoResultException e) {
            JpaUtil.fermerContextePersistance();
            throw new QueryReturnZeroResult("The query returned no results, expected at least one.");
        }

        return s;
    }

    public Support getSupportById(Long id) {
        JpaUtil.creerContextePersistance();
        SupportDAO  supportDAO = new SupportDAO();
        Support s = supportDAO.findByID(id);

        JpaUtil.fermerContextePersistance();

        return s;
    }

    public List<Support> getSupportsByStudent(Long id) throws EntityDoesNotExist {
        JpaUtil.creerContextePersistance();
        SupportDAO supportDAO = new SupportDAO();
        StudentDAO studentDAO = new StudentDAO();
        List<Support> s;

        if (studentDAO.findByID(id) == null) {
            JpaUtil.fermerContextePersistance();
            throw new EntityDoesNotExist("Student does not exist");
        }
        
        s = supportDAO.findByStudentId(id);

        JpaUtil.fermerContextePersistance();
        return s;

    }
    
    
    public List<Support> getSupportsByContributor(Long id) throws EntityDoesNotExist {
        JpaUtil.creerContextePersistance();
        SupportDAO supportDAO = new SupportDAO();
        ContributorDAO contributorDAO = new ContributorDAO();
        List<Support> s;

        if (contributorDAO.findByID(id) == null) {
            JpaUtil.fermerContextePersistance();
            throw new EntityDoesNotExist();
        }
        
        s = supportDAO.findByContibutorId(id);

        JpaUtil.fermerContextePersistance();
        return s;

    }
   

    public void rateSupport(Long idSupport, int rate) throws CanNotResigneTheAttribute, EntityDoesNotExist, Exception{
        JpaUtil.creerContextePersistance();
        SupportDAO supportDAO = new SupportDAO();
        Support supportToRate = supportDAO.findByID(idSupport);
        if(supportToRate == null ){
            throw new EntityDoesNotExist("The support does not exist");
        }
        if(supportToRate.getRate() != null ){
            throw new CanNotResigneTheAttribute("The rate is already rated");
        }
        JpaUtil.ouvrirTransaction();
        supportToRate.setRate(rate);
        JpaUtil.validerTransaction();
    }

    public void submitBilan(Long idSupport,Long idContributor, String bilan) throws EntityDoesNotExist,  Exception {
        JpaUtil.creerContextePersistance();
        try {
            SupportDAO supportDAO = new SupportDAO();
            Support supportToUpdate = supportDAO.findByID(idSupport);

            if(supportToUpdate.getBilan() != null ){
                throw new CanNotResigneTheAttribute("The bilan is already submitted");
            }
            
            JpaUtil.ouvrirTransaction();
           
            supportToUpdate.setStatus(EStatus.FINISHED);
            supportToUpdate.getContributor().setBusy(false);
            supportToUpdate.setEndDate(new Date());
            supportToUpdate.setBilan(bilan);
            
            envoyerMail("contact@instruct.if", supportToUpdate.getStudent().getEmailAddress() , "Bilan de votre réunion", "Bonjour " + supportToUpdate.getStudent().getFirstName() + ", Voici le bilan :\n"+ supportToUpdate.getBilan());
            JpaUtil.validerTransaction();
        } catch (javax.persistence.NoResultException e) {
            throw new EntityDoesNotExist();
        }
    }

    public boolean requestSupport(Long studentId, Support support) throws QueryReturnZeroResult,QueryReturnMoreThanOneResult, Exception {
        JpaUtil.creerContextePersistance();

        StudentDAO studentDAO = new StudentDAO();
        ContributorDAO contributorDAO = new ContributorDAO();

        Student student = studentDAO.findByID(studentId);
        if (student == null) {
            JpaUtil.fermerContextePersistance();
            throw new EntityDoesNotExist("Student is not defined"); // Student not found
        }
        try {
            support.setStudent(student);
            support.setStatus(EStatus.CREATED);

            // get an available contributor for the support request
            Contributor contributor = contributorDAO.findAvailableByLevel(student.getSchoolLevel());
            contributor.setBusy(true);
            support.setContributor(contributor);

            support.setSupportLink("https://servif.insa-lyon.fr/InteractlF/visio.html?eleve=" + student.getEmailAddress().toLowerCase() + "&intervenant="+ contributor.getFirstName().toLowerCase().charAt(0) +contributor.getLastName().toLowerCase());

            SupportDAO supportDAO = new SupportDAO();
            JpaUtil.ouvrirTransaction();

            supportDAO.create(support);

            JpaUtil.validerTransaction();  
            envoyerNotification(contributor.getPhoneNumber(), "Bonjour "+ contributor.getFirstName() +". Merci de prendre en charge la demande de soutien en \"" + support.getSubject().toFrench() +"\" demandée à "+ DateManager.getDateAsHourMinute(support.getStartDate()) +" par " + support.getStudent().getFirstName() + " en classe de " + support.getStudent().getSchoolLevel().toFrench());

        } catch (NoResultException e) {
            JpaUtil.annulerTransaction();
            throw new QueryReturnZeroResult("No Contributor was found");
        } catch (NonUniqueResultException e) {
            JpaUtil.annulerTransaction();
            throw new QueryReturnMoreThanOneResult();
        } 
        JpaUtil.fermerContextePersistance();
        return true;
    }

    public void setBusyContributor(Long contributorId, boolean busy) throws EntityDoesNotExist {
        JpaUtil.creerContextePersistance();
        ContributorDAO contributorDAO = new ContributorDAO();
        Contributor contributor = contributorDAO.findByID(contributorId);

        if (contributor == null) {
            JpaUtil.fermerContextePersistance();
            throw new EntityDoesNotExist("Contributor does not exist");
        }

        try {
            JpaUtil.ouvrirTransaction();
            contributor.setBusy(busy);
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            JpaUtil.fermerContextePersistance();
            throw new RuntimeException("Error setting contributor busy status: " + e.getMessage());
        }
        
        JpaUtil.fermerContextePersistance();
    }
       

    public Statistic getContributorStatictic(Long contributorId) throws Exception {
        JpaUtil.creerContextePersistance();
        SupportDAO supportDAO = new SupportDAO();

        Statistic statistic = null;

        try {
            int nbSupports = supportDAO.findNumberOfSupportsByContributor(contributorId);
            double averageRating = (double) supportDAO.findAverageRatingByContributor(contributorId);
            statistic = new Statistic();
            statistic.setNbSupports(nbSupports);
            statistic.setAverageRating(averageRating);
        }
        catch (NonUniqueResultException e) {
            JpaUtil.fermerContextePersistance();
            throw new QueryReturnMoreThanOneResult("The query returned more than one result, expected only one.");
        }
        catch (NoResultException e) {
            JpaUtil.fermerContextePersistance();
            throw new QueryReturnZeroResult("The query returned no results, expected at least one.");
        }

        JpaUtil.fermerContextePersistance();
        return statistic;
    }
}