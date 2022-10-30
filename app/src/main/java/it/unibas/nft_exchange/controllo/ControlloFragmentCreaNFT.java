package it.unibas.nft_exchange.controllo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.asyncTask.AsyncTaskMintNFT;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.FragmentCreaNFT;

public class ControlloFragmentCreaNFT {

    private static String TAG = ControlloFragmentCreaNFT.class.getSimpleName();

    private View.OnClickListener azioneSelezionaImmagine = new AzioneSelezionaImmagine();
    private Spinner.OnItemSelectedListener azioneCollezioneSelezionata = new AzioneCollezioneSelezionata();
    private View.OnClickListener azioneCreaNFT = new AzioneCreaNFT();

    public View.OnClickListener getAzioneSelezionaImmagine() {
        return azioneSelezionaImmagine;
    }

    public Spinner.OnItemSelectedListener getAzioneCollezioneSelezionata() {
        return azioneCollezioneSelezionata;
    }

    public View.OnClickListener getAzioneCreaNFT() {
        return azioneCreaNFT;
    }

    private class AzioneSelezionaImmagine implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            activityPrincipale.imageChooser();
        }
    }

    private class AzioneCollezioneSelezionata implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            List<Collezione> listaCollezioniProfilo = profiloCorrente.getListaCollezioni();
            Collezione collezioneSelezionata = listaCollezioniProfilo.get(pos);
            Applicazione.getInstance().getModello().putBean(Costanti.COLLEZIONE_SELEZIONATA, collezioneSelezionata);
            Log.d(TAG, "Collezione selezionata al momento: " + collezioneSelezionata);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Applicazione.getInstance().getModello().putBean(Costanti.COLLEZIONE_SELEZIONATA, null);
            Log.d(TAG, "Collezione selezionata al momento: " + null);
        }
    }

    private class AzioneCreaNFT implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            FragmentCreaNFT fragmentCreaNFT = activityPrincipale.getFragmentCreaNFT();
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            Bitmap bitmapImmagineSelezionata = (Bitmap) Applicazione.getInstance().getModello().getBean(Costanti.BITMAP_IMMAGINE_SELEZIONATA);
            Uri uriImmagineSelezionata = (Uri) Applicazione.getInstance().getModello().getBean(Costanti.URI_IMMAGINE_SELEZIONATA);
            if(bitmapImmagineSelezionata == null){
                activityPrincipale.mostraMessaggioToast("L'immagine è obbligatoria");
                return;
            }
            String nome = fragmentCreaNFT.getCampoNome();
            String descrizione = fragmentCreaNFT.getCampoDescrizione();
            Collezione collezioneSelezionata = (Collezione) Applicazione.getInstance().getModello().getBean(Costanti.COLLEZIONE_SELEZIONATA);
            if(collezioneSelezionata == null){
                activityPrincipale.mostraMessaggioToast("La collezione è obbligatoria");
                return;
            }
            boolean errori = convalida(fragmentCreaNFT, nome, descrizione);
            if(errori){
                return;
            }
            NFT nuovoNFT = new NFT(nome, descrizione);
            new AsyncTaskMintNFT(bitmapImmagineSelezionata, nuovoNFT, collezioneSelezionata, profiloCorrente).execute();
        }

        private Boolean convalida(FragmentCreaNFT fragmentCreaNFT, String nome, String descrizione) {
            boolean errori = false;
            if(nome.trim().isEmpty()){
                fragmentCreaNFT.setErroreCampoNome("Il nome è obbligatorio");
                errori = true;
            }
            if(descrizione.trim().isEmpty()){
                fragmentCreaNFT.setErroreCampoDescrizione("La descrizione è obbligatoria");
                errori = true;
            }
            return errori;
        }
    }
}
