package it.unibas.nft_exchange.controllo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ControlloFragmentInviaNFT {

    private Spinner.OnItemSelectedListener azioneSelezionaNFT = new AzioneSelezionaNFT();

    public Spinner.OnItemSelectedListener getAzioneSelezionaNFT() {
        return azioneSelezionaNFT;
    }

    private class AzioneSelezionaNFT implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
