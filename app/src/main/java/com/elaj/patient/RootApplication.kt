package com.elaj.patient

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.elaj.patient.Utils.SharedPManger
import com.elaj.patient.classes.Constants
import com.elaj.patient.classes.UtilityApp
import com.franmontiel.localechanger.LocaleChanger
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*
import kotlin.collections.ArrayList


class RootApplication : Application() {


    @get:Synchronized
    var sharedPManger: SharedPManger? = null

    companion object {

        var instance: RootApplication? = null
            private set
        var changesMap: MutableMap<String, Any> =
            HashMap()

        //        var editProviderModel: EditProviderModel? = null
        var fireStoreDB: FirebaseFirestore? = null

//        lateinit var mInterstitialAd: InterstitialAd
//        lateinit var adRequest: AdRequest

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }


    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    //    @GlideModule
//    public final class MyAppGlideModule extends AppGlideModule {}
    override fun onCreate() {
        super.onCreate()

        instance = this
        sharedPManger = SharedPManger(this)

        fireStoreDB = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .build()
        fireStoreDB!!.firestoreSettings = settings

//        MobileAds.initialize(this, getString(R.string.adMobKey))

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().name("mujamaa.realm")
//                .schemaVersion(1)
//                .deleteRealmIfMigrationNeeded()
//                .migration(new RootMigration())
//                .build();
//        Realm.setDefaultConfiguration(config);

//        adRequest = AdRequest.Builder()
//            .addTestDevice("4911F01B2C84F9B4E9B682B0EB0EE797")
//            .build()
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = getString(R.string.adMob_interstitial)
//        mInterstitialAd.loadAd(adRequest)
//
//        mInterstitialAd.adListener = object : AdListener() {
//            override fun onAdClosed() {
//                mInterstitialAd.loadAd(adRequest)
//            }
//
//        }

        val locales = ArrayList<Locale>()
        locales.add(Locale(Constants.English))
        locales.add(Locale(Constants.Arabic))

        LocaleChanger.initialize(this, locales)

        var appLanguage = UtilityApp.language
        if (appLanguage == null)
//            appLanguage = Locale.getDefault().language;
            appLanguage = Constants.English
//            UtilityApp.language = appLanguage
//        } else {
        UtilityApp.language = appLanguage
//        }

        LocaleChanger.setLocale(Locale(appLanguage))

        FirebaseMessaging.getInstance().subscribeToTopic("promotional_$appLanguage")

        //        FirebaseMessaging.getInstance().subscribeToTopic(Constants.NOTIFICATION_TOPIC);
        instance = this
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        LocaleChanger.onConfigurationChanged()

        //        LocaleUtils.updateConfig(rootApplication, newConfig);
    } //    private class RootMigration implements RealmMigration {
//        @Override
//        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
//
//        }
//    }
}