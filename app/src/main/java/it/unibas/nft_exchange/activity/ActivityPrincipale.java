package it.unibas.nft_exchange.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.vista.VistaPrincipale;

public class ActivityPrincipale extends AppCompatActivity {

    public static final String TAG = ActivityPrincipale.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
    }

    public VistaPrincipale getVistaPrincipale(){
        return (VistaPrincipale) getSupportFragmentManager().findFragmentById(R.id.vistaPrincipale);
    }

}