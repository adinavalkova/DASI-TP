/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author agarnier
 */
@Entity
public class School implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected Long id;
    protected String schoolCode;
    protected String schoolName;
    protected String schoolIPS;

    protected Double lat;
    protected Double lng;

    @OneToMany(mappedBy = "school")
    protected List<Student> students;

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolIPS(String schoolIPS) {
        this.schoolIPS = schoolIPS;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolIPS() {
        return schoolIPS;
    }

    public List<Student> getStudents() {
        return students;
    }
    
}
