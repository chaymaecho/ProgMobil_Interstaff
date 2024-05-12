package com.example.myapplication.models;

public class Utilisateurs {

    private String name,name2;
    private String email,email2;
    private String tel,tel2;
    private String password;
    private String society,link,service,sservice,numVacancy,adresse;
    private String img;

    public Utilisateurs() {
    }

    public Utilisateurs(String name, String email, String tel, String password, String society, String img) {

        this.name = name;

        this.email = email;

        this.tel = tel;
        this.society = society;
        this.password = password;
        this.img = img;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSservice() {
        return sservice;
    }

    public void setSservice(String sservice) {
        this.sservice = sservice;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAddress() {
        return adresse;
    }

    public void setAddress(String address) {
        this.adresse = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNumVacancy() {
        return numVacancy;
    }

    public void setNumVacancy(String numVacancy) {
        this.numVacancy = numVacancy;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {  this.tel2 = tel2;   }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
