/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author agarnier
 */
@Entity
public class Teacher extends Contributor implements Serializable {
    protected String teacherInstitutionType;
    
    public String getTeacherInstitutionType() {
        return teacherInstitutionType;
    }

    public void setTeacherInstitutionType(String teacherInstitutionType) {
        this.teacherInstitutionType = teacherInstitutionType;
    }
}
