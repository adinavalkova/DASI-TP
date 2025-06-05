package dao;

import java.util.List;

import metier.modele.School;

public class SchoolDAO {
    public School getSchoolBySchoolCode(String schoolCode) {
        return JpaUtil.obtenirContextePersistance().createQuery("SELECT s FROM School s WHERE s.schoolCode = :schoolCode", School.class)
            .setParameter("schoolCode", schoolCode)
            .getSingleResult();
        
    }

    public List<School> getSchools() {
        return JpaUtil.obtenirContextePersistance().createQuery("SELECT s FROM School s", School.class)
                .getResultList();
    }

    public void create(School school) {
        JpaUtil.obtenirContextePersistance().persist(school);
    }
}