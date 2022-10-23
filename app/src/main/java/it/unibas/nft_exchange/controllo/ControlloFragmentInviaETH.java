package it.unibas.nft_exchange.controllo;

import android.view.View;

import org.web3j.crypto.WalletUtils;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
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
            String indirizzoInvio = fragmentInviaETH.getIndirizzoDiInvio();
            Boolean errori = convalida(fragmentInviaETH, indirizzoInvio);
            if(errori){
                return;
            }
        }

        private Boolean convalida(FragmentInviaETH fragmentInviaETH, String indirizzoInvio) {
            boolean errori = false;
            if(indirizzoInvio.trim().isEmpty()){
                fragmentInviaETH.setErroreIndirizzoInvioETH("L'indirizzo è obbligatorio");
                errori = true;
            }else{
                boolean isIndirizzoValido = WalletUtils.isValidAddress(indirizzoInvio);
                if(!isIndirizzoValido){
                    fragmentInviaETH.setErroreIndirizzoInvioETH("Indirizzo non valido");
                    errori = true;
                }
            }
            return errori;
        }
    }
}
