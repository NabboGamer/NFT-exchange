package it.unibas.nft_exchange.controllo;

import android.view.View;

import org.web3j.crypto.WalletUtils;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.asyncTask.AsyncTaskInviaETH;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.FragmentInviaETH;

public class ControlloFragmentInviaETH {

    private View.OnClickListener azioneInviaETH = new AzioneInviaETH();

    public View.OnClickListener getAzioneInviaETH() {
        return azioneInviaETH;
    }

    private class AzioneInviaETH implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
            FragmentInviaETH fragmentInviaETH = activityPrincipale.getFragmentInviaETH();
            Profilo profilo = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            if(fragmentInviaETH.getLabelBilancioInETHInviaETH().equals("0 ETH")){
                activityPrincipale.mostraMessaggioToast("Impossibile inviare somma, non si possiedono fondi");
            }
            String indirizzoDestinatario = fragmentInviaETH.getIndirizzoDiInvio();
            Boolean errori = convalida(fragmentInviaETH, indirizzoDestinatario);
            if(errori){
                return;
            }
            String chiavePrivataMittente = profilo.getChiavePrivata();
            new AsyncTaskInviaETH(chiavePrivataMittente, indirizzoDestinatario).execute();
        }

        private Boolean convalida(FragmentInviaETH fragmentInviaETH, String indirizzoDestinatario) {
            boolean errori = false;
            if(indirizzoDestinatario.trim().isEmpty()){
                fragmentInviaETH.setErroreIndirizzoInvioETH("L'indirizzo è obbligatorio");
                errori = true;
            }else{
                boolean isIndirizzoValido = WalletUtils.isValidAddress(indirizzoDestinatario);
                if(!isIndirizzoValido){
                    fragmentInviaETH.setErroreIndirizzoInvioETH("Indirizzo non valido");
                    errori = true;
                }
            }
            return errori;
        }
    }
}
