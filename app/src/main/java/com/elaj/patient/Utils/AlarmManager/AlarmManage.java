package com.elaj.patient.Utils.AlarmManager;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by iSoft4is on 10/13/2016.
 */

public class AlarmManage {

    private AlarmManagerBroadcastReceiver alarm;

    public AlarmManage() {
        alarm = new AlarmManagerBroadcastReceiver();

    }

    public void afterFifteenMinTimer(Context context) {
        if (alarm != null) {
            alarm.setFifteenTimer(context);
        } else {
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void setLocationTimer(Context context, int orderId) {
        if (alarm != null) {
            alarm.setLocationTimer(context, orderId);
        } else {
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }
}
