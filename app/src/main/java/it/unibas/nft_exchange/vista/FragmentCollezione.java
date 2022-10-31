package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.modello.Profilo;

public class FragmentCollezione extends Fragment {

    private static String TAG = FragmentCollezione.class.getSimpleName();
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
        this.listViewCollezioniProfilo.setOnItemClickListener(Applicazione.getInstance().getControlloFragmentCollezione().getAzioneMostraFinestraDettagliCollezione());
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        aggiornaContenuto();
    }

    public void aggiornaContenuto() {
        Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
        Log.d(TAG, "Profilo corrente: " + profiloCorrente);
        AdapterCollezioni adapterCollezioni = new AdapterCollezioni(profiloCorrente.getListaCollezioni());
        this.listViewCollezioniProfilo.setAdapter(adapterCollezioni);
    }

    public String getNome(){
        return this.campoNomeCreaCollezione.getText().toString();
    }

    public String getDescrizione(){
        return this.campoDescrizioneCreaCollezione.getText().toString();
    }

    public void setErroreNome(String err){
        this.campoNomeCreaCollezione.setError(err);
    }

    public void setErroreDescrizione(String err){
        this.campoDescrizioneCreaCollezione.setError(err);
    }

}