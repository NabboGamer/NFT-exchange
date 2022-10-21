package it.unibas.nft_exchange;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import java.util.ArrayList;

import it.unibas.nft_exchange.controllo.ControlloVistaLogin;
import it.unibas.nft_exchange.modello.ArchivioProfili;
import it.unibas.nft_exchange.modello.Modello;
import it.unibas.nft_exchange.modello.ModelloPersistente;
import it.unibas.nft_exchange.modello.Profilo;

public class Applicazione extends MultiDexApplication {

    public static final String TAG = Applicazione.class.getSimpleName();

    private static Applicazione singleton;

    public static Applicazione getInstance() {
        return singleton;
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Applicazione creata...");
        singleton = (Applicazione) getApplicationContext();
        singleton.registerActivityLifecycleCallbacks(new GestoreAttivita());
        Applicazione.getInstance().getModelloPersistente().saveBean(Costanti.ARCHIVIO_PROFILI, new ArchivioProfili(new ArrayList<Profilo>()));
    }

    /////////////////////////////////////////////

    private Activity currentActivity = null;

    private Modello modello = new Modello();
    private ModelloPersistente modelloPersistente = new ModelloPersistente();
    private ControlloVistaLogin controlloVistaLogin = new ControlloVistaLogin();

    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    public Modello getModello() {
        return modello;
    }

    public ControlloVistaLogin getControlloVistaLogin() {
        return controlloVistaLogin;
    }

    public ModelloPersistente getModelloPersistente() {
        return modelloPersistente;
    }

    //////////////////////////////////////////////
    //////////////////////////////////////////////

    private class GestoreAttivita implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            //Log.i(TAG, "onActivityCreated: " + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //Log.i(TAG, "onActivityDestroyed: " + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            //Log.d(TAG, "onActivityStarted: " + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, "currentActivity initialized: " + activity);
            currentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //Log.d(TAG, "onActivityPaused: " + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (currentActivity == activity) {
                Log.d(TAG, "currentActivity stopped: " + activity);
                currentActivity = null;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            //Log.d(TAG, "onActivitySaveInstanceState: " + activity);
        }
    }
}
