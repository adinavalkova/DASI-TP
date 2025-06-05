package metier.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.google.maps.model.LatLng;

import dao.JpaUtil;
import dao.PersonDAO;
import dao.SchoolDAO;
import exceptions.AuthIncorrect;
import exceptions.EntityAlreadyExists;
import exceptions.EntityDoesNotExist;
import exceptions.QueryReturnMoreThanOneResult;
import exceptions.QueryReturnZeroResult;
import metier.modele.Contributor;
import metier.modele.Person;
import metier.modele.School;
import metier.modele.Student;
import util.EducNetApi;
import util.GeoNetApi;
import static util.Message.envoyerMail;

public class AuthService {
    public boolean registerStudent(Student student, String schoolCode) throws Exception {
        PersonDAO personDAO = new PersonDAO();
        SchoolDAO schoolDAO = new SchoolDAO();

        try {
            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
            School school;
            try {
                school = schoolDAO.getSchoolBySchoolCode(schoolCode);
            } catch (NoResultException e) {
                EducNetApi api = new EducNetApi();
                List<String> result = api.getInformationEtablissement(schoolCode);

                if (result == null || result.isEmpty()) {
                    envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bievenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusementéchoué ... Merci de recommencer ultérieurement");
                    throw new EntityDoesNotExist("School with code " + schoolCode + " does not exist");
                }

                String schoolName = result.get(1);
                LatLng coordsEtablissement = GeoNetApi.getLatLng(schoolName);

                school = new School();
                school.setSchoolCode(schoolCode);
                school.setSchoolName(schoolName);
                school.setSchoolIPS(result.get(8));
                school.setLat(coordsEtablissement.lat);
                school.setLng(coordsEtablissement.lng);
                schoolDAO.create(school);
            }
     
            student.setSchool(school);
            personDAO.create(student);

            JpaUtil.validerTransaction();
            envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bienvenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", nous te confirmons ton inscription sur le réseau INSTRUCT'IF. Si tu as besoin d'un soutien pour tes lecons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant ");
        }
        catch(EntityExistsException e){
            JpaUtil.annulerTransaction();
            envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bienvenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué ... Merci de recommencer ultérieurement");
            throw new EntityAlreadyExists();
        }
        catch (NoResultException e) {
            JpaUtil.annulerTransaction();
            envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bienvenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué ... Merci de recommencer ultérieurement");
            throw new QueryReturnZeroResult();
        }
        catch (NonUniqueResultException e) {
            JpaUtil.annulerTransaction();
            envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bienvenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué ... Merci de recommencer ultérieurement");
            throw new QueryReturnMoreThanOneResult();
        }
         catch(Exception e){
            JpaUtil.annulerTransaction();

            if (e instanceof EntityDoesNotExist) {
                throw e;
            }
            envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bienvenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué (mail déjà existant) ... Merci de recommencer ultérieurement");
            throw new EntityAlreadyExists();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }

        return true; 
    }
    
    
    public boolean createContributor(Contributor contributor) throws Exception {
        PersonDAO personDAO = new PersonDAO();

        try {
            JpaUtil.creerContextePersistance();

            JpaUtil.ouvrirTransaction();
  
            personDAO.create(contributor);

            JpaUtil.validerTransaction();
        }
        catch(EntityExistsException e){
            JpaUtil.annulerTransaction();
            // envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bievenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué ... Merci de recommencer ultérieurement");
            throw new EntityAlreadyExists();
        }
         catch(Exception e){
            JpaUtil.annulerTransaction();
            // envoyerMail("contact@instruct.if", student.getEmailAddress() , "Bievenue sur le reseau INSTRUCT'IF", "Bonjour " + student.getFirstName() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué (mail déjà existant) ... Merci de recommencer ultérieurement");
            throw new EntityAlreadyExists();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return true; 
    }
    
    public Person login(String mail, String motDePass) throws AuthIncorrect {
        PersonDAO  personDAO = new PersonDAO();
        Person p = null;
        try {
            JpaUtil.creerContextePersistance();
            p = personDAO.findByMail(mail);

            if(p != null && ! p.getPassword().equals(motDePass)){
                p = null;
            }
        } catch (NoResultException | NonUniqueResultException e) {
            JpaUtil.annulerTransaction();
            throw new AuthIncorrect();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return p;
    }
}
