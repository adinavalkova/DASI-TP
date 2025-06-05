/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele.enums;

/**
 *
 * @author agarnier
 */
public enum ESchoolLevel {
    SIXTH,
    SEVENTH, 
    EIGHTH, 
    NINTH, 
    TENTH, 
    ELEVENTH, 
    TWELFTH;
    public String toFrench() {
       switch (this) {
           case SIXTH: return "Sixième";
           case SEVENTH: return "Cinquième";
           case EIGHTH: return "Quatrième";
           case NINTH: return "Troisième";
           case TENTH: return "Seconde";
           case ELEVENTH: return "Première";
           case TWELFTH: return "Terminale";
           default: return "";
       }
   }
}
