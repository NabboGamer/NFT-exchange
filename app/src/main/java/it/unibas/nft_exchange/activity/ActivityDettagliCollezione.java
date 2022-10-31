package it.unibas.nft_exchange.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.nft_exchange.R;

public class ActivityDettagliCollezione extends AppCompatActivity {

    public static final String TAG = ActivityDettagliCollezione.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli_collezione);
    }

}