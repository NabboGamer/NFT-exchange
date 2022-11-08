package it.unibas.nft_exchange.controllo;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.modello.NFT;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.AdapterNFTsSpinner;
import it.unibas.nft_exchange.vista.FragmentInviaNFT;

public class ControlloFragmentInviaNFT {

    private static String TAG = AdapterNFTsSpinner.class.getSimpleName();

    private Spinner.OnItemSelectedListener azioneSelezionaNFT = new AzioneSelezionaNFT();
    private View.OnClickListener azioneInviaNFT = new AzioneInviaNFT();

    public Spinner.OnItemSelectedListener getAzioneSelezionaNFT() {
        return azioneSelezionaNFT;
    }

    public View.OnClickListener getAzioneInviaNFT() {
        return azioneInviaNFT;
    }

    private class AzioneSelezionaNFT implements Spinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            List<NFT> listaNFT = (List<NFT>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_TOTALE_NFT_PROFILO_CORRENTE);
            NFT nftSelezionato = listaNFT.get(i);
            Applicazione.getInstance().getModello().putBean(Costanti.NFT_SELEZIONATO_SPINNER, nftSelezionato);
            Log.d(TAG, "NFT corrente: " + nftSelezionato);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Applicazione.getInstance().getModello().putBean(Costanti.NFT_SELEZIONATO_SPINNER, null);
            Log.d(TAG, "NFT corrente: " + null);
        }
    }

    private class AzioneInviaNFT implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            FragmentInviaNFT fragmentInviaNFT = activityPrincipale.getFragmentInviaNFT();
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            String indirizzoDestinatario = fragmentInviaNFT.getIndirizzoDestinatario();
            boolean errori = convalida(fragmentInviaNFT, profiloCorrente, indirizzoDestinatario);
            if(errori){
                return;
            }
            NFT nftSelezionato = (NFT) Applicazione.getInstance().getModello().getBean(Costanti.NFT_SELEZIONATO_SPINNER);
            if(nftSelezionato == null){
                activityPrincipale.mostraMessaggioToast("L'NFT è obbligatorio");
                return;
            }
            Applicazione.getInstance().getModello().putBean(Costanti.INDIRIZZO_DESTINATARIO, indirizzoDestinatario);
            Applicazione.getInstance().getModello().putBean(Costanti.NFT_SELEZIONATO, nftSelezionato);
            BigInteger gasPrice = BigInteger.valueOf(20000000000L);
            BigInteger gasLimit = BigInteger.valueOf(6721975L);
            BigDecimal commissioni = new BigDecimal(gasPrice.multiply(gasLimit));
            BigDecimal commissioniInETH = Convert.fromWei(commissioni, Convert.Unit.ETHER);
            activityPrincipale.mostraMessaggioAlertInviaNFT("ATTENZIONE inviare un NFT richiederà al massimo " + commissioniInETH + " ETH");
        }

        private boolean convalida(FragmentInviaNFT fragmentInviaNFT, Profilo profiloCorrente, String indirizzoDestinatario) {
            boolean errori = false;
            if(indirizzoDestinatario.trim().isEmpty()){
                fragmentInviaNFT.setErroreCampoIndirizzoDestinatario("L'indirizzo è obbligatorio");
                errori = true;
            }else{
                boolean isIndirizzoValido = WalletUtils.isValidAddress(indirizzoDestinatario);
                if(!isIndirizzoValido){
                    fragmentInviaNFT.setErroreCampoIndirizzoDestinatario("Indirizzo non valido");
                    errori = true;
                }else{
                    Credentials credentials = Credentials.create(profiloCorrente.getChiavePrivata());
                    if(indirizzoDestinatario.equalsIgnoreCase(credentials.getAddress())){
                        fragmentInviaNFT.setErroreCampoIndirizzoDestinatario("Hai inserito il tuo indirizzo \uD83D\uDE42");
                        errori = true;
                    }
                }
            }
            return errori;
        }
    }
}
