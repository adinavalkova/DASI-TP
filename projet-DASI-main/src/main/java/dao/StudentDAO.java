package dao;

import metier.modele.Student;

public class StudentDAO {

    public void create(Student student) {
        JpaUtil.obtenirContextePersistance().persist(student);
    }

    public Student findByID(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Student.class, id);
    }
    
}