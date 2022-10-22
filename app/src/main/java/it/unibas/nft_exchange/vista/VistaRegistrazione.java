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

public class VistaRegistrazione extends Fragment {

    private EditText campoUsernameRegistrazione;
    private EditText campoPasswordRegistrazione;
    private EditText campoChiavePrivataRegistrazione;
    private Button bottoneRegistrati;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_registrazione, container, false);
        this.campoUsernameRegistrazione = vista.findViewById(R.id.campoUsernameRegistrazione);
        this.campoPasswordRegistrazione = vista.findViewById(R.id.campoPasswordRegistrazione);
        this.campoChiavePrivataRegistrazione = vista.findViewById(R.id.campoChiavePrivataRegistrazione);
        this.bottoneRegistrati = vista.findViewById(R.id.bottoneRegistrati);
        this.bottoneRegistrati.setOnClickListener(Applicazione.getInstance().getControlloVistaRegistrazione().getAzioneRegistrati());
        return vista;
    }

    public String getUsernameRegistrazione(){
        return this.campoUsernameRegistrazione.getText().toString();
    }

    public String getPasswordRegistrazione(){
        return this.campoPasswordRegistrazione.getText().toString();
    }

    public String getChiavePrivataRegistrazione(){
        return this.campoChiavePrivataRegistrazione.getText().toString();
    }

    public void setErroreUsername(String err){
        this.campoUsernameRegistrazione.setError(err);
    }

    public void setErrorePassword(String err){
        this.campoPasswordRegistrazione.setError(err);
    }

    public void setErroreChiavePrivata(String err){
        this.campoChiavePrivataRegistrazione.setError(err);
    }
}
