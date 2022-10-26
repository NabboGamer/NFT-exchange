package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.R;

public class FragmentCollezione extends Fragment {

    private EditText campoNomeCreaCollezione;
    private EditText campoDescrizioneCreaCollezione;
    private Button bottoneCreaCollezione;
    private ListView listViewCollezioniProfilo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_collezione, container, false);
        this.campoNomeCreaCollezione = vista.findViewById(R.id.campoNomeCreaCollezione);
        this.campoDescrizioneCreaCollezione = vista.findViewById(R.id.campoDescrizioneCreaCollezione);
        this.bottoneCreaCollezione = vista.findViewById(R.id.bottoneCreaCollezione);
        this.listViewCollezioniProfilo = vista.findViewById(R.id.listViewCollezioniProfilo);
        this.bottoneCreaCollezione.setOnClickListener(Applicazione.getInstance().getControlloFragmentCollezione().getAzioneCreaCollezione());
        return vista;
    }

    public String getNome(){
        return this.campoNomeCreaCollezione.getText().toString();
    }

    public String getDescrizione(){
        return this.campoNomeCreaCollezione.getText().toString();
    }

    public void setErroreNome(String err){
        this.campoNomeCreaCollezione.setError(err);
    }

    public void setErroreDescrizione(String err){
        this.campoDescrizioneCreaCollezione.setError(err);
    }

}