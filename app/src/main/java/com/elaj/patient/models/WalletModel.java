package com.elaj.patient.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletModel {

    @SerializedName("current_balance")
    @Expose
    public int currentBalance;
    @SerializedName("withdrawals_balance")
    @Expose
    public int withdrawalsBalance;
    @SerializedName("total_balance")
    @Expose
    public int totalBalance;

}
