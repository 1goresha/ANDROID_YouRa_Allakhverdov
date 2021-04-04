package com.example.clubolympus.model;

public class ClubMember {

    private int id;
    private String firstName;
    private String lastName;
    private String kindOfSport;
    private int genderIndex;

    public ClubMember(String firstName, String lastName, String kindOfSport, int genderIndex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.kindOfSport = kindOfSport;
        this.genderIndex = genderIndex;
    }

    public ClubMember(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public int getGenderIndex() {
        return genderIndex;
    }

    public void setGenderIndex(int genderIndex) {
        this.genderIndex = genderIndex;
    }
}
