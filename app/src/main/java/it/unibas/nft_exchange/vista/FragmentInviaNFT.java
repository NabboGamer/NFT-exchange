package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;
import it.unibas.nft_exchange.modello.Profilo;

public class FragmentInviaNFT extends Fragment {

    private EditText campoInidirizzoDestinatarioInviaNFT;
    private Spinner spinnerNFT;
    private Button bottoneInviaNFT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_invia_n_f_t, container, false);
        this.campoInidirizzoDestinatarioInviaNFT = vista.findViewById(R.id.campoInidirizzoDestinatarioInviaNFT);
        this.spinnerNFT = vista.findViewById(R.id.spinnerNFT);
        this.bottoneInviaNFT = vista.findViewById(R.id.bottoneInviaNFT);
        inizializzaSpinner();
        this.spinnerNFT.setOnItemSelectedListener(Applicazione.getInstance().getControlloFragmentInviaNFT().getAzioneSelezionaNFT());
        return vista;
    }

    private void inizializzaSpinner() {
        Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
        List<Collezione> listaCollezioni = profiloCorrente.getListaCollezioni();
        Applicazione.getInstance().getModello().putBean(Costanti.LISTA_COLLEZIONI, listaCollezioni);
        List<NFT> listaNFT = new ArrayList<>();
        for (Collezione collezione : listaCollezioni) {
            listaNFT.addAll(collezione.getListaNFT());
        }
        AdapterNFTsSpinner adapterNFTsSpinner = new AdapterNFTsSpinner(listaNFT);
        this.spinnerNFT.setAdapter(adapterNFTsSpinner);
    }
}