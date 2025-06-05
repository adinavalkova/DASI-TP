package dao;

import metier.modele.Contributor;
import metier.modele.enums.ESchoolLevel;

public class ContributorDAO {

    public Contributor findByID(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Contributor.class, id);
    }

    public Contributor findAvailableByLevel(ESchoolLevel level){
        String jpql = "SELECT c FROM Contributor c " +
                      "LEFT JOIN Support s ON s.contributor = c " +
                      "WHERE :level >= c.minLevel " +
                      "AND :level <= c.maxLevel " +
                      "AND c.busy = false " +
                      "GROUP BY c " +
                      "ORDER BY COUNT(s) ASC";        
        return JpaUtil.obtenirContextePersistance()
            .createQuery(jpql, Contributor.class)
            .setParameter("level", level)
            .setMaxResults(1)
            .getSingleResult();
    }
}
