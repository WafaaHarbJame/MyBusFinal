package com.elaj.patient.Utils.AlarmManager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;


import com.elaj.patient.MainActivityBottomNav;
import com.elaj.patient.Utils.SharedPManger;

import java.util.Calendar;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    SharedPManger sharedPManger;

    @Override
    public void onReceive(final Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        sharedPManger = new SharedPManger(context);
        wl.acquire();

        //You can do the processing here.
        Bundle extras = intent.getExtras();
        String type = extras.getString("TYPE");

        //Release the lock
        wl.release();
    }

    public void SetAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5, pi);
    }

    public void CancelAlarm(Context context, int orderId) {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, (orderId * -1), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setFifteenTimer(Context context) {

//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
//            String dateInString = (time) + "";
//            String dateInString2 = (eventTime) + "";
//
//            Date date = sdf2.parse(dateInString);
//
//            System.out.println(dateInString);
//            System.out.println("Date - Time in milliseconds : " + date.getTime());
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());


        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + AlarmManager.INTERVAL_FIFTEEN_MINUTES), pi);
//            (int) System.currentTimeMillis()

    }

    public void setLocationTimer(Context context, int orderId) {

        Calendar calendar = Calendar.getInstance();
        System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra("orderId", orderId);
        PendingIntent pi = PendingIntent.getBroadcast(context, (orderId * -1), intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pi);

//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    private void Notify(Context context, String subject, int eventId, int event_type) {
        Intent intent = new Intent(context, MainActivityBottomNav.class);
        intent.putExtra("Type", "Login");
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
//        Notification n = mBuilder.setSmallIcon(R.drawable.ic_launcher_48)
//                .setAutoCancel(true)
//                //.setContentTitle(title)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(subject))
//                .setContentIntent(pIntent)
//                .setSound(alarmSound)
//                .setContentText(subject).build();

//        NotificationManager notificationManager =
//                (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


//        notificationManager.notify((int) System.currentTimeMillis(), n);
    }
}