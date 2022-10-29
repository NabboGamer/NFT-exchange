package it.unibas.nft_exchange.controllo;

import android.view.View;

import org.web3j.crypto.WalletUtils;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityRegistrazione;
import it.unibas.nft_exchange.modello.ArchivioProfili;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.VistaRegistrazione;

public class ControlloVistaRegistrazione {

    private static String TAG = ControlloVistaRegistrazione.class.getSimpleName();
    private View.OnClickListener azioneRegistrati = new AzioneRegistrati();

    public View.OnClickListener getAzioneRegistrati() {
        return azioneRegistrati;
    }

    private class AzioneRegistrati implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityRegistrazione activityRegistrazione = (ActivityRegistrazione) Applicazione.getInstance().getCurrentActivity();
            VistaRegistrazione vistaRegistrazione = activityRegistrazione.getVistaRegistrazione();
            String username = vistaRegistrazione.getUsernameRegistrazione();
            String password = vistaRegistrazione.getPasswordRegistrazione();
            String chiavePrivata = vistaRegistrazione.getChiavePrivataRegistrazione();
            boolean errori = convalida(vistaRegistrazione, username, password, chiavePrivata);
            if(errori){
                return;
            }
            ArchivioProfili archivioProfili = (ArchivioProfili) Applicazione.getInstance().getModelloPersistente().getPersistentBean(Costanti.ARCHIVIO_PROFILI, ArchivioProfili.class);
            Profilo nuovoProfilo = new Profilo(username,password,chiavePrivata);
            if(archivioProfili.isProfiloEsistente(nuovoProfilo)){
                activityRegistrazione.mostraMessaggioToast("Profilo già esistente");
                return;
            }
            if(archivioProfili.isProfiloStessoUsername(nuovoProfilo)){
                activityRegistrazione.mostraMessaggioToast("Username già esistente");
                return;
            }
            archivioProfili.aggiungiProfilo(nuovoProfilo);
            activityRegistrazione.mostraMessaggioToast("Registrazione effettuata correttamente");
            Applicazione.getInstance().getModelloPersistente().saveBean(Costanti.ARCHIVIO_PROFILI,archivioProfili);
            activityRegistrazione.mostraActivityLogin();
        }

        private boolean convalida(VistaRegistrazione vistaRegistrazione, String username, String password, String chiavePrivata) {
            boolean errori = false;
            if(username.trim().isEmpty()){
                errori = true;
                vistaRegistrazione.setErroreUsername("Lo username è obbligatorio");
            }
            if(password.trim().isEmpty()){
                errori = true;
                vistaRegistrazione.setErrorePassword("La password è obbligatoria");
            }
            if(chiavePrivata.trim().isEmpty()){
                errori = true;
                vistaRegistrazione.setErroreChiavePrivata("La chiave privata è obbligatoria");
            }else{
                boolean isChiavePrivataValida = WalletUtils.isValidPrivateKey(chiavePrivata);
                if(!isChiavePrivataValida){
                    errori = true;
                    vistaRegistrazione.setErroreChiavePrivata("Chiave privata non valida");
                }
            }
            return errori;
        }
    }
}
