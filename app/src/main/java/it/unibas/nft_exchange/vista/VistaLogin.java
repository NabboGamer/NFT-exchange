package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.R;

public class VistaLogin extends Fragment {

    private EditText campoUsername;
    private EditText campoPassword;
    private Button bottoneLogin;
    private Button bottoneMostraVistaRegistrazione;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_login, container, false);
        this.campoUsername = vista.findViewById(R.id.campoUsername);
        this.campoPassword = vista.findViewById(R.id.campoPassword);
        this.bottoneLogin = vista.findViewById(R.id.bottoneLogin);
        this.bottoneMostraVistaRegistrazione = vista.findViewById(R.id.bottoneMostraVistaRegistrazione);
        this.bottoneLogin.setOnClickListener(Applicazione.getInstance().getControlloVistaLogin().getAzioneLogin());
        this.bottoneMostraVistaRegistrazione.setOnClickListener(Applicazione.getInstance().getControlloVistaLogin().getAzioneMostraVistaRegistrazione());
        return vista;
    }

    public Button getBottoneMostraVistaRegistrazione() {
        return bottoneMostraVistaRegistrazione;
    }

    public String getUsername(){
        return this.campoUsername.getText().toString();
    }

    public String getPassword(){
        return this.campoPassword.getText().toString();
    }

    public void setErroreUsername(String err){
        this.campoUsername.setError(err);
    }

    public void setErrorePassword(String err){
        this.campoPassword.setError(err);
    }
}
