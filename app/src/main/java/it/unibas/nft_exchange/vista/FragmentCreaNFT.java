package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.Profilo;

public class FragmentCreaNFT extends Fragment {

    private ImageView boxImmagineCreaNFT;
    private EditText campoNomeCreaNFT;
    private EditText campoDescrizioneCreaNFT;
    private Spinner spinnerCollezioni;
    private Button bottoneCreaNFT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_crea_n_f_t, container, false);
        this.boxImmagineCreaNFT = vista.findViewById(R.id.boxImmagineCreaNFT);
        this.campoNomeCreaNFT = vista.findViewById(R.id.campoNomeCreaNFT);
        this.campoDescrizioneCreaNFT = vista.findViewById(R.id.campoDescrizioneCreaNFT);
        this.spinnerCollezioni = vista.findViewById(R.id.spinnerCollezioni);
        this.bottoneCreaNFT = vista.findViewById(R.id.bottoneCreaNFT);
        this.inizializzaSpinner();
        this.boxImmagineCreaNFT.setOnClickListener(Applicazione.getInstance().getControlloFragmentCreaNFT().getAzioneSelezionaImmagine());
        this.spinnerCollezioni.setOnItemSelectedListener(Applicazione.getInstance().getControlloFragmentCreaNFT().getAzioneCollezioneSelezionata());
        this.bottoneCreaNFT.setOnClickListener(Applicazione.getInstance().getControlloFragmentCreaNFT().getAzioneCreaNFT());
        return vista;
    }

    private void inizializzaSpinner(){
        Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
        List<Collezione> listaCollezioni = profiloCorrente.getListaCollezioni();
        AdapterCollezioni adapterCollezioni = new AdapterCollezioni(profiloCorrente.getListaCollezioni());
        this.spinnerCollezioni.setAdapter(adapterCollezioni);
    }

    public String getCampoNome(){
        return this.campoNomeCreaNFT.getText().toString();
    }

    public String getCampoDescrizione(){
        return this.campoDescrizioneCreaNFT.getText().toString();
    }

    public void setErroreCampoNome(String err){
        this.campoNomeCreaNFT.setError(err);
    }

    public void setErroreCampoDescrizione(String err){
        this.campoDescrizioneCreaNFT.setError(err);
    }

    public ImageView getBoxImmagineCreaNFT(){
        return this.boxImmagineCreaNFT;
    }

}