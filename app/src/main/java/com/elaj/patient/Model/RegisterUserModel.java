package com.elaj.patient.Model;

import java.io.Serializable;

/**
 * Created by ameer on 1/9/2018.
 */

public class RegisterUserModel implements Serializable {

    public String full_name;
    public int countryCode;
    public int countryId;
//    public int cityId;
    public String email;
    public String mobile;
    public String password;
    public boolean isCustomer;
    public String password_confirm;
    public String fcm_token;
    public int type;

}
