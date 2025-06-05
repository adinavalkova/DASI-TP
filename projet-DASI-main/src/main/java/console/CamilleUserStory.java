/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import static console.Main.printlnConsoleIHM;
import exceptions.EntityDoesNotExist;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Contributor;
import metier.modele.Statistic;
import metier.modele.Support;
import metier.modele.enums.ESchoolLevel;
import metier.service.AuthService;
import metier.service.UserService;
import util.DateManager;

/**
 *
 * @author rkhedair
 */
public class CamilleUserStory {
    public static Boolean testRegisterContributor() {
        Boolean rep = false;
        AuthService authService = new AuthService();
        Contributor contributor = new Contributor();
        contributor.setFirstName("Camille");
        contributor.setLastName("Shahood");
        contributor.setEmailAddress("Camille.Shahood@gmail.com");
        contributor.setPassword("1234");
        contributor.setPhoneNumber("0767586587");

        contributor.setBusy(false);
        contributor.setMinLevel(ESchoolLevel.SEVENTH);
        contributor.setMaxLevel(ESchoolLevel.ELEVENTH);

        try {
            rep = authService.createContributor(contributor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rep;

    }
    
     public static Support findSupportTodo(Long contributorId) {
        UserService userService = new UserService();
        Support support = null;
        try {
            support = userService.getSupportToDo(contributorId);
            if (support == null) {
                printlnConsoleIHM("No support to do for this contributor.");
            } else {
                printlnConsoleIHM("Support to do: ");
                
                printlnConsoleIHM(DateManager.getDateAsHourMinute( support.getStartDate())+ " avec " + support.getStudent().getFirstName() + " : " + support.getDescription());
            }
        } catch (exceptions.QueryReturnZeroResult e) {
            printlnConsoleIHM("No support found (QueryReturnZeroResult).");
        } catch (exceptions.QueryReturnMoreThanOneResult e) {
            printlnConsoleIHM("More than one support found (QueryReturnMoreThanOneResult).");
        }
        return support;
    }
     
      public static List<Support> getSupports(Long contributorId) {
        UserService userService = new UserService();
        List<Support> supports = null;
        
        try {
            supports = userService.getSupportsByContributor(contributorId);
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
     
    public static void submitBilan(Long supportId, Long contributerId) {
      UserService userService = new UserService();

      try {
          userService.submitBilan(supportId,contributerId, "Everything is perfect");
      } catch (EntityDoesNotExist e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
    
      
    public static void getContributorStatictic(Long contributerId) {
        UserService userService = new UserService();
        Statistic statistic = null;
        try {
            statistic = userService.getContributorStatictic(contributerId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        printlnConsoleIHM("Contributor statistic: ");
        printlnConsoleIHM("Average rate: " + statistic.getAverageRating());
        printlnConsoleIHM("Number of support: " + statistic.getNbSupports());
    }
    
}
