package com.example.myapplication;

public class Offre {


    public String idoffre;
    public String title;
    public String organization;
    public String salary;
    public String jobfamily;
    public String joblevel;
    public String type;
    public String datevacancie;
    public String cite;
    public String description;


    public Offre(){}
    public Offre(String idoffre, String title, String organization, String salary, String jobfamily, String joblevel, String type, String datevacancie, String cite, String description) {
        this.idoffre = idoffre;
        this.title = title;
        this.organization = organization;
        this.salary = salary;
        this.jobfamily = jobfamily;
        this.joblevel = joblevel;
        this.type = type;
        this.datevacancie = datevacancie;
        this.cite = cite;
        this.description = description;
    }

    public String getIdoffre() {
        return idoffre;
    }

    public void setIdoffre(String idoffre) {
        this.idoffre = idoffre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobfamily() {
        return jobfamily;
    }

    public void setJobfamily(String jobfamily) {
        this.jobfamily = jobfamily;
    }

    public String getJoblevel() {
        return joblevel;
    }

    public void setJoblevel(String joblevel) {
        this.joblevel = joblevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatevacancie() {
        return datevacancie;
    }

    public void setDatevacancie(String datevacancie) {
        this.datevacancie = datevacancie;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
