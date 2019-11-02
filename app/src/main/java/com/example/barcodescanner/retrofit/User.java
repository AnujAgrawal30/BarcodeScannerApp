package com.example.barcodescanner.retrofit;

public class User {
    private String name;
    private String mi_number;
//    private String google_id;
    private String email;
//    private String cr_referral_code;
    private String mobile_number;
//    private String postal_address;
//    private String permanent_address;
//    private int zip_code;
    private String gender;
//    private String dob;
    private String year_of_study;
//    private int checkedin;
    private String present_city;
    private String present_college;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMi_number() {
        return mi_number;
    }

    public void setMi_number(String mi_number) {
        this.mi_number = mi_number;
    }

//    public String getGoogle_id() {
//        return google_id;
//    }

//    public void setGoogle_id(String google_id) {
//        this.google_id = google_id;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getCr_referral_code() {
//        return cr_referral_code;
//    }
//
//    public void setCr_referral_code(String cr_referral_code) {
//        this.cr_referral_code = cr_referral_code;
//    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

//    public String getPostal_address() {
//        return postal_address;
//    }
//
//    public void setPostal_address(String postal_address) {
//        this.postal_address = postal_address;
//    }
//
//    public String getPermanent_address() {
//        return permanent_address;
//    }
//
//    public void setPermanent_address(String permanent_address) {
//        this.permanent_address = permanent_address;
//    }
//
//    public int getZip_code() {
//        return zip_code;
//    }
//
//    public void setZip_code(int zip_code) {
//        this.zip_code = zip_code;
//    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }

    public String getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(String year_of_study) {
        this.year_of_study = year_of_study;
    }

//    public int getCheckedin() {
//        return checkedin;
//    }
//
//    public void setCheckedin(int checkedin) {
//        this.checkedin = checkedin;
//    }

    public String getPresent_city() {
        return present_city;
    }

    public void setPresent_city(String present_city) {
        this.present_city = present_city;
    }

    public String getPresent_college() {
        return present_college;
    }

    public void setPresent_college(String present_college) {
        this.present_college = present_college;
    }

    public User(String name, String mi_number, String google_id, String email, String cr_referral_code, String mobile_number, String postal_address, String permanent_address, int zip_code, String gender, String dob, String year_of_study, int checkedin, String present_city, String present_college) {
        this.name = name;
        this.mi_number = mi_number;
//        this.google_id = google_id;
        this.email = email;
//        this.cr_referral_code = cr_referral_code;
        this.mobile_number = mobile_number;
//        this.postal_address = postal_address;
//        this.permanent_address = permanent_address;
//        this.zip_code = zip_code;
        this.gender = gender;
//        this.dob = dob;
        this.year_of_study = year_of_study;
//        this.checkedin = checkedin;
        this.present_city = present_city;
        this.present_college = present_college;
    }
}
