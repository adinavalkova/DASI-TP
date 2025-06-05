/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import metier.modele.enums.ESchoolSubject;
import metier.modele.enums.EStatus;

/**
 *
 * @author agarnier
 */
@Entity
public class Support implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    protected Long id;
    protected String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date startDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date endDate;

    protected ESchoolSubject subject;
    protected EStatus status;
    protected String supportLink;
    protected Integer rate;
    protected String bilan;
    @ManyToOne
    protected Student student;
    @ManyToOne
    protected Contributor contributor;
    
    public Support() {
    }

    public Support(String description, ESchoolSubject subject, Date startDate) {
        this.description = description;
        this.subject = subject;
        this.startDate = startDate;
    }

    public Support(String description, ESchoolSubject subject) {
        this(description, subject, new Date());
    }

    public void setSubject(ESchoolSubject subject) {
        this.subject = subject;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public void setSupportLink(String suppportLink) {
        this.supportLink = suppportLink;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public ESchoolSubject getSubject() {
        return subject;
    }

    public EStatus getStatus() {
        return status;
    }

    public String getSupportLink() {
        return supportLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

        public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        if(rate >= 0 && rate <= 5){
            this.rate = rate;            
        }
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
