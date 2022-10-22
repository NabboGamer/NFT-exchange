package it.unibas.nft_exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.vista.VistaRegistrazione;

public class ActivityRegistrazione extends AppCompatActivity {

    public static final String TAG = ActivityRegistrazione.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
    }

    public VistaRegistrazione getVistaRegistrazione(){
        return (VistaRegistrazione) getSupportFragmentManager().findFragmentById(R.id.vistaRegistrazione);
    }

    public void mostraMessaggioToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void mostraActivityLogin(){
        Intent intent = new Intent(this, ActivityLogin.class);
        this.startActivity(intent);
    }

}