/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import static console.Main.printlnConsoleIHM;
import exceptions.CanNotResigneTheAttribute;
import exceptions.EntityDoesNotExist;
import java.util.List;
import metier.modele.Student;
import metier.modele.Support;
import metier.modele.enums.ESchoolSubject;
import metier.service.AuthService;
import metier.service.UserService;
import util.DateManager;

/**
 *
 * @author rkhedair
 */
public class AliceUserStory {
     public static Boolean registerStudentWithSchool(String schoolCode) {
        AuthService authService = new AuthService();
        Boolean rep = false;
        Student student = new Student();
        student.setFirstName("Alice");
        student.setLastName("Shehab");
        student.setEmailAddress("Alice.Shehab@gmail.com");
        student.setPassword("1234");
        student.setSchoolLevel(metier.modele.enums.ESchoolLevel.SEVENTH);
        student.setBirthDate(new java.util.Date());
        
        try {
            rep = authService.registerStudent(student, schoolCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
     
    public static void requestSupport(Long studentId) {
        UserService userService = new UserService();
        Support support = new Support("Help with Java programming", ESchoolSubject.BIOLOGY);
        
        try {
            if (userService.requestSupport(studentId, support)) {
                printlnConsoleIHM("Support request sent successfully.");
            } else {
                printlnConsoleIHM("Failed to send support request.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
      public static void getSubjects() {
        UserService userService = new UserService();
        printlnConsoleIHM("Subjects : ");
        for(String subject : userService.getSubjects()){
            printlnConsoleIHM(subject);
        }
    }
      
    public static void rateSupport(Long supportId) {
        UserService userService = new UserService();
        try {
            userService.rateSupport(supportId,4);
        }  catch (CanNotResigneTheAttribute e) {
            e.printStackTrace();
        } catch (EntityDoesNotExist e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static List<Support> getSupports(Long studentId){
        UserService userService = new UserService();
        List<Support> supports = null;
        
        try {
            supports = userService.getSupportsByStudent(studentId);
        } catch (EntityDoesNotExist e) {
            e.printStackTrace();
        }
        if (supports == null ||supports.size() <= 0 ) {
            printlnConsoleIHM("No support for this contributor.");
        } else {
            printlnConsoleIHM("Supports: ");
            for(Support support : supports){
                printlnConsoleIHM(DateManager.getDateAsHourMinute( support.getStartDate())+ " avec " + support.getStudent().getFirstName() + " " +support.getSupportLink()+" : " + support.getDescription());
            }
        }
        return supports;
    }

}
