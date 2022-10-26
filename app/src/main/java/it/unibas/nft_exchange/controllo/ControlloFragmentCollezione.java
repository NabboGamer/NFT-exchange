package it.unibas.nft_exchange.controllo;

import android.util.Log;
import android.view.View;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.asyncTask.AsyncTaskDeployContract;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.FragmentCollezione;

public class ControlloFragmentCollezione {

    private static String TAG = ControlloFragmentCollezione.class.getSimpleName();

    private View.OnClickListener azioneCreaCollezione = new AzioneCreaCollezione();

    public View.OnClickListener getAzioneCreaCollezione() {
        return azioneCreaCollezione;
    }

    private class AzioneCreaCollezione implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            FragmentCollezione fragmentCollezione = activityPrincipale.getFragmentCollezione();
            String nome = fragmentCollezione.getNome();
            String descrizione  = fragmentCollezione.getDescrizione();
            Boolean errori = convalida(fragmentCollezione, nome, descrizione);
            if(errori){
                return;
            }
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            if(profiloCorrente.isCollezioneEsistente(nome)){
                activityPrincipale.mostraMessaggioToast("Esiste già una collezione con questo nome");
                Log.d(TAG, "Collezione attuale: " + profiloCorrente.getListaCollezioni());
                return;
            }
            Collezione collezione = new Collezione(nome, descrizione);
            new AsyncTaskDeployContract(collezione, profiloCorrente).execute();
        }

        private Boolean convalida(FragmentCollezione fragmentCollezione, String nome, String descrizione) {
            boolean errori = false;
            if(nome.trim().isEmpty()){
                fragmentCollezione.setErroreNome("Il nome è obbligatorio");
                errori = true;
            }
            if(descrizione.trim().isEmpty()){
                fragmentCollezione.setErroreDescrizione("La descrizione è obbligatoria");
                errori = true;
            }
            return errori;
        }
    }
}