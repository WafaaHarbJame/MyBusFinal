package com.mybus.mybusapp.Utils

import android.text.TextUtils
import android.util.Patterns
import com.mybus.mybusapp.RootApplication.Companion.instance
import io.michaelrocks.libphonenumber.android.NumberParseException
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber.PhoneNumber

object PhoneHandler {
    fun isValidPhoneNumber(phoneNumber: CharSequence?): Boolean {
        return if (!TextUtils.isEmpty(phoneNumber)) {
            Patterns.PHONE.matcher(phoneNumber).matches()
        } else false
    }

    fun validateUsing_libphonenumber(
        countryCode: String,
        phNumber: String?
    ): Boolean {
        val phoneNumberUtil = PhoneNumberUtil.createInstance(
            instance
        )
        val isoCode =
            phoneNumberUtil.getRegionCodeForCountryCode(countryCode.toInt())
        var phoneNumber: PhoneNumber? = null
        try { //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
//            System.out.println("Log isoCode " + isoCode);
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode)
        } catch (e: NumberParseException) {
            System.err.println(e)
        }
        return phoneNumberUtil.isValidNumber(phoneNumber)
        //        if (isValid) {
////            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
////            Toast.makeText(this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_SHORT).show();
//            return true;
//        } else {
////            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_SHORT).show();
//            return false;
//        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}