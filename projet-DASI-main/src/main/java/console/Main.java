package console;

import dao.JpaUtil;
import exceptions.AuthIncorrect;
import exceptions.EntityDoesNotExist;
import metier.modele.Contributor;
import metier.modele.Person;
import metier.modele.Statistic;
import metier.modele.Student;
import metier.modele.Support;
import metier.modele.enums.ESchoolSubject;
import metier.service.AppService;
import metier.service.AuthService;
import metier.service.InitService;
import metier.service.UserService;
import util.DateManager;

public class Main {
   
     public static void main(String[] args) throws InterruptedException {
        JpaUtil.creerFabriquePersistance();
        //InitService.initContributor();
        UserStory();
        
        JpaUtil.fermerFabriquePersistance();
    }
    public static void UserStory()throws InterruptedException{
         Person person;

        Student alice = null;
        printlnConsoleIHMUserStory("==== STEP 1.1: Register Student (Alice) Wrong school ====");
        AliceUserStory.registerStudentWithSchool("0691664JWrong");

        printlnConsoleIHMUserStory("==== STEP 1.2: Register Student (Alice) ====");
        AliceUserStory.registerStudentWithSchool("0691664J");
        
        printlnConsoleIHMUserStory("==== STEP 1.3: Register Student (Alice) again ====");
        AliceUserStory.registerStudentWithSchool("0691664J");

        printlnConsoleIHMUserStory("==== STEP 2.1: Login as Alice with bad password ====");
        CommonUserStory.testLogin("Alice.Shehab@gmail.com","1234"+"wrong");
        
        printlnConsoleIHMUserStory("==== STEP 2.1: Login as Alice with good password ====");
        person = CommonUserStory.testLogin("Alice.Shehab@gmail.com","1234");
        if(person instanceof Student){
            alice = (Student)person;
        }
        
        printlnConsoleIHMUserStory("==== STEP 3.1: Request Support No contributor exist ====");
        AliceUserStory.getSubjects();

        AliceUserStory.requestSupport(alice.getId());
        
        printlnConsoleIHMUserStory("==== Creating Contributor ====");
        Contributor camille = null;
        
        CamilleUserStory.testRegisterContributor();
        printlnConsoleIHMUserStory("==== Login Contributor ====");

        person = CommonUserStory.testLogin("Camille.Shahood@gmail.com","1234");
        if(person instanceof Contributor){
            camille = (Contributor)person;
        }
         
        printlnConsoleIHMUserStory("==== STEP 4.1: Simulate Contributor Finding the Support to Do (no support to do) ====");
        CamilleUserStory.findSupportTodo(camille.getId());

        printlnConsoleIHMUserStory("==== STEP 5.2: Contributor get all supports ====");
        CamilleUserStory.getSupports(camille.getId());
        
        printlnConsoleIHMUserStory("==== STEP 3.2: Request Support ====");
        AliceUserStory.requestSupport(alice.getId());
        
                 
        printlnConsoleIHMUserStory("==== STEP 4.2: Simulate Contributor Finding the Support to Do ====");
        Support supportToDo = CamilleUserStory.findSupportTodo(camille.getId());


        printlnConsoleIHMUserStory("==== STEP 5.2: Contributor get all supports ====");
        CamilleUserStory.getSupports(camille.getId());
        
        Thread.sleep(60000l);
        
        printlnConsoleIHMUserStory("==== STEP 6: Contributor Submits Bilan ====");
        CamilleUserStory.submitBilan(supportToDo.getId(),camille.getId());
        Support supportValidated = CommonUserStory.testGetSupportById(supportToDo.getId());
        printlnConsoleIHM("Support bilan validé : ");
        printlnConsoleIHM(DateManager.getDateAsHourMinute( supportValidated.getStartDate())+ " avec " + supportValidated.getStudent().getFirstName() + " : " + supportValidated.getBilan());

        

        printlnConsoleIHMUserStory("==== STEP 7.1: Student Rates the Support id dont exist ====");
        AliceUserStory.rateSupport(10L);
        
        printlnConsoleIHMUserStory("==== STEP 7.2: Student Rates the Support ====");
        AliceUserStory.rateSupport(supportToDo.getId());
        Support supportRated = CommonUserStory.testGetSupportById(supportToDo.getId());
        printlnConsoleIHM("Support evalué : ");
        printlnConsoleIHM(DateManager.getDateAsHourMinute( supportRated.getStartDate())+ " avec " + supportRated.getStudent().getFirstName() + " : " + supportRated.getRate());

        printlnConsoleIHMUserStory("==== STEP 7.3: Student Rerates the Support ====");
        AliceUserStory.rateSupport(supportToDo.getId());  
      
        printlnConsoleIHMUserStory("\n==== STEP 8: Check Alice's Support History ====");
        AliceUserStory.getSupports(alice.getId());
        
        printlnConsoleIHMUserStory("\n==== STEP 9.1: Contributor Statistics ====");
        CamilleUserStory.getContributorStatictic(camille.getId());
        
        printlnConsoleIHMUserStory("\n==== STEP 9.2: Common Statistics ====");
        CommonUserStory.getAppStatictic();
    }
    
    
/* ======================== Functionnal test  ============================== */
    public static void testGetContributorStatictic() {
        UserService userService = new UserService();
        Statistic statistic = null;
        try {
            statistic = userService.getContributorStatictic(3L);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            
        }

        printlnConsoleIHM("Contributor statistic: ");
        printlnConsoleIHM("Number of supports: " + statistic);
    }
    
    public static void testFindTodo() {
        UserService userService = new UserService();
        Support support = null;
        try {
            support = userService.getSupportToDo(2L);
            if (support == null) {
                printlnConsoleIHM("No support to do for this contributor.");
            } else {
                printlnConsoleIHM("Support to do: ");
                printlnConsoleIHM(support);
            }
        } catch (exceptions.QueryReturnZeroResult e) {
            printlnConsoleIHM("No support found (QueryReturnZeroResult).");
        } catch (exceptions.QueryReturnMoreThanOneResult e) {
            printlnConsoleIHM("More than one support found (QueryReturnMoreThanOneResult).");
        }
    }

    public static void testSubmitBilan() {
        UserService userService = new UserService();
        
        try {
            userService.submitBilan(1L,3L, "Here is the bilan for the support request.");
        } catch (EntityDoesNotExist e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testGetSchools() {
        AppService appService = new AppService();
        printlnConsoleIHM("Schools : ");
        printlnConsoleIHM(appService.getSchools());
    }

    public static void testRegisterStudentWithSchool() {
        AuthService authService = new AuthService();
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmailAddress("john.doe@gmail.com");
        student.setPassword("password123");
        student.setSchoolLevel(metier.modele.enums.ESchoolLevel.SEVENTH);
        student.setBirthDate(new java.util.Date());
        
        
        try {
            authService.registerStudent(student, "0691664J");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testRegisterStudentWithSchoolDoesNotExist() {
        AuthService authService = new AuthService();
        Student student = new Student();
        student.setFirstName("Rami");
        student.setLastName("Rami");
        student.setEmailAddress("johnde@gmail.com");
        student.setPassword("ed");
        student.setSchoolLevel(metier.modele.enums.ESchoolLevel.SEVENTH);
        student.setBirthDate(new java.util.Date());
        
        
        try {
            authService.registerStudent(student, "TEST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void testRateSupport() {
        UserService userService = new UserService();
        try {
            userService.rateSupport(1L,4);
        } catch (Exception e) {
            
        }
    }

    public static void testRequestSupport() {
        UserService userService = new UserService();
        Support support = new Support("Help with Java programming", ESchoolSubject.BIOLOGY);
        
        try {
            if (userService.requestSupport(2L, support)) {
                printlnConsoleIHM("Support request sent successfully.");
            } else {
                printlnConsoleIHM("Failed to send support request.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void testRegisterStudent() {
        AuthService authService = new AuthService();
        Student student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmailAddress("john.doe@gmail.com");
        student.setPassword("password123");
        student.setSchoolLevel(metier.modele.enums.ESchoolLevel.SEVENTH);
        student.setBirthDate(new java.util.Date());
        
        try {
            authService.registerStudent(student, "DUBY");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testLogin() {
        AuthService authService = new AuthService();
        Person person = null;
        try {
            person = authService.login("camille@gmail.com", "camille");
            if (person == null) {
                printlnConsoleIHM("Password is incorrect");
            } else {
                printlnConsoleIHM("Person : ");
                printlnConsoleIHM("#" + person.getId() + " " + person.getFirstName().toUpperCase() + " " + person.getLastName());
                printlnConsoleIHM("----");
            }
        } catch (AuthIncorrect e) {
          printlnConsoleIHM("No user found with this email.");
      } 
    }
    
      public static void testFindPersonByMail() {
        UserService userService = new UserService();
        Person person = null;
        try {
            person = userService.getUserById(1L);
            if (person == null) {
                printlnConsoleIHM("ID does not exist");
            } else {
                printlnConsoleIHM("Person : ");
                printlnConsoleIHM("#" + person.getId() + " " + person.getFirstName().toUpperCase() + " " + person.getLastName());
                printlnConsoleIHM("----");
            }
        } catch (EntityDoesNotExist e) {
            printlnConsoleIHM("ID does not exist (EntityDoesNotExist).");
        }
    }

    public static void testGetSubjects() {
        UserService userService = new UserService();
        printlnConsoleIHM("Subjects : ");
        printlnConsoleIHM(userService.getSubjects());
    }

    public static void testGetSupportById() {
        UserService userService = new UserService();
        printlnConsoleIHM("Support : ");
        printlnConsoleIHM(userService.getSupportById(new Long(1)));
    }

    public static void testGetSupportByStudentId() {
        UserService userService = new UserService();
        printlnConsoleIHM("Support : ");
        try {
            printlnConsoleIHM(userService.getSupportsByStudent(1L));
        }
        catch(EntityDoesNotExist e) {
            
        }
    }

    public static void printlnConsoleIHM(Object o) {
        String BG_CYAN = "\u001b[46m";
        String RESET = "\u001B[0m";

        System.out.print(BG_CYAN);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }
    
     public static void printlnConsoleIHMUserStory(Object o) {
        String BG = "\u001B[43m";
        String RESET = "\u001B[0m";

        System.out.print(BG);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }
}