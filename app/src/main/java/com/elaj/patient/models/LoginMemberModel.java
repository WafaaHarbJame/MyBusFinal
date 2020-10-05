package com.elaj.patient.models;

/**
 * Created by ameer on 6/22/2017.
 */

public class LoginMemberModel {


    public String email;
    public String password;


    public LoginMemberModel() {
    }

    public LoginMemberModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
