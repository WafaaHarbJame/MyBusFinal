package com.elaj.patient.Utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.elaj.patient.classes.Constants
import com.elaj.patient.R
import com.elaj.patient.RootApplication
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object ActivityHandler {
    fun isPackageExist(
        context: Context,
        packageName: String
    ): Boolean {
        val packages: List<ApplicationInfo>
        val pm: PackageManager
        pm = context.packageManager
        packages = pm.getInstalledApplications(0)
        for (packageInfo in packages) {
            if (packageInfo.packageName == packageName) return true
        }
        return false
    }

    fun OpenGooglePlay(context: Context) {
        val appPackageName =
            context.packageName // getPackageName() from Context or Activity object
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    fun OpenBrowser(context: Context, url: String) {
        var url = url
        if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://$url"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun OpenSendEmail(
        context: Context,
        subject: String,
        body: String,
        receiver: String
    ) {
        val intent = Intent(Intent.ACTION_SENDTO) // it's not ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject + "")
        intent.putExtra(Intent.EXTRA_TEXT, body + "")
        intent.data = Uri.parse("mailto:$receiver") // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    fun makeCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    }

    fun OpenDialerIntent(
        context: Context,
        phoneNumber: String
    ) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }

    fun shareTextUrl(
        context: Context,
        title: String?,
        url: String?,
        shareChannel: String?
    ) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        if (shareChannel != null && shareChannel != "") share.setPackage(shareChannel)
        share.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val appStr: String =
            RootApplication.instance!!.getString(R.string.by_app).toString() + "\n" + Constants.Paly_Link
        // Add data to the intent, the receiving app will decide
// what to do with it.
//        share.putExtra(Intent.EXTRA_SUBJECT, title + "");
        var body: String? = ""
        if (title != null && !title.isEmpty()) {
            body += title
        }
        if (url != null && !url.isEmpty()) {
            body += "\n" + url
        }
        share.putExtra(Intent.EXTRA_TEXT, body /*+ "\n\n" + appStr*/)
        context.startActivity(
            Intent.createChooser(
                share,
                context.resources.getString(R.string.share_with)
            )
        )
    }

    fun shareTwitter(context: Context, message: String) {
        val tweetIntent = Intent(Intent.ACTION_SEND)
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message)
        tweetIntent.type = "text/plain"
        val packManager = context.packageManager
        val resolvedInfoList =
            packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var resolved = false
        for (resolveInfo in resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name
                )
                resolved = true
                break
            }
        }
        if (resolved) {
            context.startActivity(tweetIntent)
        } else {
            val i = Intent()
            i.putExtra(Intent.EXTRA_TEXT, message)
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse(
                "https://twitter.com/intent/tweet?text=" + urlEncode(
                    message
                )
            )
            context.startActivity(i)
            //            GlobalData.Toast("Twitter app isn't found");
//            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private fun urlEncode(s: String): String {
        return try {
            URLEncoder.encode(s, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Log.wtf("TAG", "UTF-8 should always be supported", e)
            ""
        }
    }
}