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
public enum ESchoolSubject {
    MATHEMATICS, 
    PHYSICS, 
    CHEMISTRY, 
    BIOLOGY, 
    COMPUTER_SCIENCE, 
    HISTORY, 
    GEOGRAPHY, 
    CIVICS, 
    LITERATURE, 
    FRENCH, 
    ENGLISH, 
    SPANISH, 
    GERMAN, 
    PHILOSOPHY, 
    ECONOMICS, 
    ART, 
    MUSIC, 
    PHYSICAL_EDUCATION, 
    TECHNOLOGY;

    public String toFrench() {
        switch (this) {
            case MATHEMATICS: return "Mathématiques";
            case PHYSICS: return "Physique";
            case CHEMISTRY: return "Chimie";
            case BIOLOGY: return "Biologie";
            case COMPUTER_SCIENCE: return "Informatique";
            case HISTORY: return "Histoire";
            case GEOGRAPHY: return "Géographie";
            case CIVICS: return "Éducation civique";
            case LITERATURE: return "Littérature";
            case FRENCH: return "Français";
            case ENGLISH: return "Anglais";
            case SPANISH: return "Espagnol";
            case GERMAN: return "Allemand";
            case PHILOSOPHY: return "Philosophie";
            case ECONOMICS: return "Économie";
            case ART: return "Arts plastiques";
            case MUSIC: return "Musique";
            case PHYSICAL_EDUCATION: return "Éducation physique";
            case TECHNOLOGY: return "Technologie";
            default: return this.name();
        }
    }
}
