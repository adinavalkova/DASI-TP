package metier.service;

import dao.JpaUtil;
import dao.PersonDAO;
import metier.modele.Teacher;
import metier.modele.UniversityStudent;
import metier.modele.enums.ESchoolLevel;

public class InitService {

    private static UniversityStudent initUniversityStudent(
            String firstName, 
            String lastName, 
            String emailAddress, 
            String password,
            ESchoolLevel minLevel,
            ESchoolLevel maxLevel,
            String phoneNumber,
            String universityName
    ) {
        UniversityStudent student = new UniversityStudent();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmailAddress(emailAddress);
        student.setPassword(password);
        student.setBusy(false);
        student.setUniversity(universityName);
        student.setPhoneNumber(phoneNumber);
        student.setMinLevel(minLevel);
        student.setMaxLevel(maxLevel);
        return student;
    }

    private static Teacher initTeacher(
            String firstName, String lastName, String emailAddress, String password, ESchoolLevel minLevel, ESchoolLevel maxLevel, String phoneNumber) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmailAddress(emailAddress);
        teacher.setPassword(password);
        teacher.setBusy(false);
        teacher.setPhoneNumber(phoneNumber);
        teacher.setMinLevel(minLevel);
        teacher.setMaxLevel(maxLevel);
        return teacher;
    }

    public static void initContributor() {
        JpaUtil.creerContextePersistance();

        Teacher teacher = initTeacher(
            "Nathan",
            "Lefevre",
            "nathan.lefevre@etu.univ-lille.fr",
            "nathanPwd321",
            ESchoolLevel.NINTH,
            ESchoolLevel.TWELFTH,
            "0623456789"
        );

        Teacher teacher2 = initTeacher(
            "Sophie",
            "Dubois",
            "sophie.dubois@univ-marseille.fr",
            "sophieTeach456",
            ESchoolLevel.SIXTH,
            ESchoolLevel.ELEVENTH,
            "0634567890"
        );

        Teacher teacher3 = initTeacher(
            "Julien",
            "Moreau",
            "julien.moreau@univ-lyon.fr",
            "julienTeach789",
            ESchoolLevel.EIGHTH,
            ESchoolLevel.TWELFTH,
            "0645678901"
        );

        UniversityStudent student = initUniversityStudent(
                "Alicia", 
                "Martin",
                "alicia.martin@gmail.com",
                "password123",
                ESchoolLevel.SIXTH,
                ESchoolLevel.TWELFTH,
                "0123456789",
                "Université de Paris" 
        );

        UniversityStudent student2 = initUniversityStudent(
            "Lucas",
            "Durand",
            "lucas.durand@hotmail.com",
            "lucasPass456",
            ESchoolLevel.EIGHTH,
            ESchoolLevel.TWELFTH,
            "0612345678",
            "Université de Lyon"
        );

        UniversityStudent student3 = initUniversityStudent(
            "Emma",
            "Bernard",
            "emma.bernard@yahoo.com",
            "emmaSecure789",
            ESchoolLevel.SEVENTH,
            ESchoolLevel.TWELFTH,
            "0754321987",
            "Université de Bordeaux"
        );

        UniversityStudent student4 = initUniversityStudent(
            "Thomas",
            "Petit",
            "thomas.petit@gmail.com",
            "thomasPass321",
            ESchoolLevel.NINTH,
            ESchoolLevel.TWELFTH,
            "0765432198",
            "Université de Lille"
        );

        UniversityStudent student5 = initUniversityStudent(
            "Camille",
            "Leroy",
            "camille.leroy@outlook.com",
            "camillePwd654",
            ESchoolLevel.SIXTH,
            ESchoolLevel.ELEVENTH,
            "0776543219",
            "Université de Strasbourg"
        );


        PersonDAO personDAO = new PersonDAO();
        
        try {
            JpaUtil.ouvrirTransaction();
            
            personDAO.create(teacher);
            personDAO.create(teacher2);
            personDAO.create(teacher3);
            personDAO.create(student);
            personDAO.create(student2);
            personDAO.create(student3);
            personDAO.create(student4);
            personDAO.create(student5);
            
            JpaUtil.validerTransaction();
        } catch (Exception e) {
            throw  new RuntimeException("Error initializing contributor data", e);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
}
