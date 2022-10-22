package it.unibas.nft_exchange.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.vista.VistaLogin;

public class ActivityLogin extends AppCompatActivity {

    public static final String TAG = ActivityLogin.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public VistaLogin getVistaLogin(){
        return (VistaLogin) getSupportFragmentManager().findFragmentById(R.id.vistaLogin);
    }

    public void mostraMessaggioToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void mostraMessaggioAlert(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NFT Exchange");
        builder.setMessage(msg);
        builder.create().show();
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