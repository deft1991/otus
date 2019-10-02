package ru.deft.homework.testobjects;

public class Adress {
    private String city;
    private String street;

    public Adress() {
    }

    public String getCity() {
        return city;
    }

    public Adress(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override public String toString() {
        return "Adress{\"city='" + city + '\'' + ", street='" + street + '\'' + '}';
    }
}
