package it.unibas.nft_exchange.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.vista.VistaLogin;

public class ActivityLogin extends AppCompatActivity {

    public static final String TAG = ActivityLogin.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VistaLogin vistaLogin = (VistaLogin) getSupportFragmentManager().findFragmentById(R.id.vistaLogin);
        vistaLogin.getBottoneMostraVistaRegistrazione().setTextColor(ContextCompat.getColor(Applicazione.getInstance().getCurrentActivity().getBaseContext(), R.color.bottone_arrotondato_colore_iniziale));
    }

    public VistaLogin getVistaLogin(){
        return (VistaLogin) getSupportFragmentManager().findFragmentById(R.id.vistaLogin);
    }

    public void mostraMessaggioToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void mostraActivityRegistrazione(){
        Intent intent = new Intent(this, ActivityRegistrazione.class);
        this.startActivity(intent);
    }

    public void mostraActivityPrincipale(){
        Intent intent = new Intent(this, ActivityPrincipale.class);
        this.startActivity(intent);
    }

}