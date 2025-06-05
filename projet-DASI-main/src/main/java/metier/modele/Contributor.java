/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import metier.modele.enums.ESchoolLevel;

/**
 *
 * @author agarnier
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Contributor extends Person implements Serializable {
    protected Boolean busy;
    @Column(nullable = false)
    protected String phoneNumber;
    @Enumerated(EnumType.ORDINAL)
    protected ESchoolLevel minLevel;
    @Enumerated(EnumType.ORDINAL)
    protected ESchoolLevel maxLevel;

    @OneToMany(mappedBy = "contributor")
    protected List<Support> supports;

    public Boolean getBusy() {
        return busy;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ESchoolLevel getMinLevel() {
        return minLevel;
    }

    public ESchoolLevel getMaxLevel() {
        return maxLevel;
    }

    public List<Support> getSupports() {
        return supports;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setMinLevel(ESchoolLevel minLevel) {
        this.minLevel = minLevel;
    }

    public void setMaxLevel(ESchoolLevel maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }
     
}
