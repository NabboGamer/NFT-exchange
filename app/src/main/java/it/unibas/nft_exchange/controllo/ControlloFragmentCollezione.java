package it.unibas.nft_exchange.controllo;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.FragmentCollezione;

public class ControlloFragmentCollezione {

    private static String TAG = ControlloFragmentCollezione.class.getSimpleName();

    private View.OnClickListener azioneCreaCollezione = new AzioneCreaCollezione();
    private AdapterView.OnItemClickListener azioneMostraFinestraDettagliCollezione = new AzioneMostraFinestraDettagliCollezione();

    public View.OnClickListener getAzioneCreaCollezione() {
        return azioneCreaCollezione;
    }

    public AdapterView.OnItemClickListener getAzioneMostraFinestraDettagliCollezione() {
        return azioneMostraFinestraDettagliCollezione;
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
            Applicazione.getInstance().getModello().putBean(Costanti.COLLEZIONE_DA_CREARE, collezione);
            BigInteger gasPrice = BigInteger.valueOf(20000000000L);
            BigInteger gasLimit = BigInteger.valueOf(6721975L);
            BigDecimal commissioni = new BigDecimal(gasPrice.multiply(gasLimit));
            BigDecimal commissioniInETH = Convert.fromWei(commissioni, Convert.Unit.ETHER);
            activityPrincipale.mostraMessaggioAlertCreazioneCollezione("ATTENZIONE creare una collezione richiederà al massimo " + commissioniInETH + " ETH");
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

    private class AzioneMostraFinestraDettagliCollezione implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            List<Collezione> listaCollezioni = profiloCorrente.getListaCollezioni();
            Collezione collezioneSelezionataDaLista = listaCollezioni.get(i);
            Applicazione.getInstance().getModello().putBean(Costanti.COLLEZIONE_SELEZIONATA_DA_LISTA, collezioneSelezionataDaLista);
            activityPrincipale.mostraActivityDettagliCollezione();
        }
    }
}
