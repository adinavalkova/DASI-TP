/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;
import static console.Main.printlnConsoleIHM;
import exceptions.AuthIncorrect;
import metier.modele.Person;
import metier.modele.Statistic;
import metier.modele.Support;
import metier.service.AppService;
import metier.service.AuthService;

import metier.service.UserService;

/**
 *
 * @author rkhedair
 */
public class CommonUserStory {
    
    public static Support testGetSupportById(Long supportId) {
        UserService userService = new UserService();
        return userService.getSupportById(supportId);
    }
     
    public static Person testLogin(String mail, String password) {
      AuthService authService = new AuthService();
      Person person = null;
      try {
          person = authService.login(mail, password);
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
      return person;
    }
    
    public static void getAppStatictic() {
        AppService appService = new AppService();
        Statistic statistic = null;
        try {
            statistic = appService.getGlobalStatictic();
        } catch (Exception e) {
            e.printStackTrace();
        }

        printlnConsoleIHM("Global statistic: ");
        printlnConsoleIHM("Average duration: " + statistic.getAverageDuration());
        printlnConsoleIHM("Number of support: " + statistic.getNbSupports());
    }
    
}
