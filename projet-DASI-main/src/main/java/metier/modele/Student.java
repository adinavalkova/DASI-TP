/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import metier.modele.enums.ESchoolLevel;

/**
 *
 * @author agarnier
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Student extends Person implements Serializable {
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date birthDate;
    
    @ManyToOne
    protected School school;
    @Enumerated(EnumType.ORDINAL)
    ESchoolLevel schoolLevel;

    @OneToMany(mappedBy = "student")
    protected List<Support> supportList;
    

    public void setSchoolLevel(ESchoolLevel schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Support> getSupportList() {
        return supportList;
    }

    public void setSupportList(List<Support> supportList) {
        this.supportList = supportList;
    }

    public School getSchool() {
        return school;
    }

    public ESchoolLevel getSchoolLevel() {
        return schoolLevel;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    
}
