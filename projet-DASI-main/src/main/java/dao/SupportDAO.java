/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import metier.modele.Support;
import metier.modele.enums.EStatus;

/**
 *
 * @author rkhedair
 */
public class SupportDAO {
    public void create(Support support) {
        JpaUtil.obtenirContextePersistance().persist(support);
    }
    
    public Support findByID(Long id){
        return JpaUtil.obtenirContextePersistance().find(Support.class, id);
    }
     
    public List<Support> findByStudentId(Long idStudent) {
            return JpaUtil.obtenirContextePersistance().createQuery("SELECT s FROM Support s WHERE s.student.id = :id", Support.class)
                    .setParameter("id", idStudent)
                    .getResultList();
    }

    public int findNumberOfSupports() throws NoResultException, NonUniqueResultException {
        return ((Number) JpaUtil.obtenirContextePersistance().createQuery("SELECT COUNT(s) FROM Support s")
                .getSingleResult()).intValue();
    }
    
    private Long calculateDuration(Support s){
        long differenceInTime = s.getEndDate().getTime() - s.getStartDate().getTime();
       
        return TimeUnit.MILLISECONDS.toMinutes(differenceInTime) % 60;
    }
        
    public double findAverageDuration() throws NoResultException, NonUniqueResultException {
        
        List<Support> supports = JpaUtil.obtenirContextePersistance()
            .createQuery("SELECT s FROM Support s", Support.class)
            .getResultList();

        return  supports.stream()
                    .filter(s -> s.getStartDate() != null && s.getEndDate() != null)
                    .mapToLong(s -> calculateDuration(s)
                    ).average()
                    .orElse(0.0);
    }

    public double findAverageRatingByContributor(Long contributorId) throws NoResultException, NonUniqueResultException {
        return ((Number) JpaUtil.obtenirContextePersistance().createQuery("SELECT AVG(s.rate) FROM Support s WHERE s.contributor.id = :contributorId")
                .setParameter("contributorId", contributorId)
                .getSingleResult()).doubleValue();
    }

    public int findNumberOfSupportsByContributor(Long contributorId) throws NoResultException, NonUniqueResultException {
        return ((Number) JpaUtil.obtenirContextePersistance().createQuery("SELECT COUNT(s) FROM Support s WHERE s.contributor.id = :contributorId")
                .setParameter("contributorId", contributorId)
                .getSingleResult()).intValue();
    }

    public Support findToDo(Long contributorId) throws NoResultException, NonUniqueResultException {
        return JpaUtil.obtenirContextePersistance().createQuery("SELECT s FROM Support s WHERE s.contributor.id = :contributorId AND s.status = :status", Support.class)
                .setParameter("contributorId", contributorId)
                .setParameter("status", EStatus.CREATED)
                .getSingleResult();
    }
    
      public List<Support> findByContibutorId(Long idContibutor) {
            return JpaUtil.obtenirContextePersistance().createQuery("SELECT s FROM Support s WHERE s.contributor.id = :id ORDER BY s.status ASC", Support.class)
                    .setParameter("id", idContibutor)
                    .getResultList();
    }
}