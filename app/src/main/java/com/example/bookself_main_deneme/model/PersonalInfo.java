package com.example.bookself_main_deneme.model;

public class PersonalInfo {

    String name, surname, birth, country, city;

    public PersonalInfo(String name, String surname, String birth, String country, String city) {
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.country = country;
        this.city = city;
    }

    public PersonalInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
