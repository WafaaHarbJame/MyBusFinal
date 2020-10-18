package com.elaj.patient.models;

import java.io.Serializable;

/**
 * Created by ameer on 1/9/2018.
 */

public class RegisterUserModel implements Serializable {

    public int countryCode;
    public int countryId;
    public String mobile;
    public String mobileWithCountry;
    public String password;
    public String password_confirm;
    public String fcm_token;
    public boolean isVerified;
    public int type;

}
