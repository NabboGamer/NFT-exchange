package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;

public class VistaDettagliCollezione extends Fragment {

    private TextView labelNomeCollezioneDettagli;
    private TextView labelCreatoreCollezioneDettagli;
    private TextView labelDescrizioneCollezioneDettagli;
    private ListView listViewNFT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.vista_dettagli_collezione, container, false);
        this.labelNomeCollezioneDettagli = vista.findViewById(R.id.labelNomeCollezioneDettagli);
        this.labelCreatoreCollezioneDettagli = vista.findViewById(R.id.labelCreatoreCollezioneDettagli);
        this.labelDescrizioneCollezioneDettagli = vista.findViewById(R.id.labelDescrizioneCollezioneDettagli);
        this.listViewNFT = vista.findViewById(R.id.listViewNFT);
        inizializzaLabel();
        inizializzaListaNFT();
        return vista;
    }

    private void inizializzaLabel() {
        Collezione collezioneSelezionata = (Collezione) Applicazione.getInstance().getModello().getBean(Costanti.COLLEZIONE_SELEZIONATA_DA_LISTA);
        String nome = collezioneSelezionata.getNome();
        String usernameCreatore = collezioneSelezionata.getUsernameCreatore();
        String descrizione = collezioneSelezionata.getDescrizione();
        this.labelNomeCollezioneDettagli.setText(nome);
        this.labelCreatoreCollezioneDettagli.setText(usernameCreatore);
        this.labelDescrizioneCollezioneDettagli.setText(descrizione);
    }

    private void inizializzaListaNFT() {
        Collezione collezioneSelezionata = (Collezione) Applicazione.getInstance().getModello().getBean(Costanti.COLLEZIONE_SELEZIONATA_DA_LISTA);
        List<NFT> listaNFT = collezioneSelezionata.getListaNFT();

    }
}
