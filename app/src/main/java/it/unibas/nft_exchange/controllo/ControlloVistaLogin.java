package it.unibas.nft_exchange.controllo;

import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.activity.ActivityLogin;
import it.unibas.nft_exchange.modello.ArchivioProfili;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.VistaLogin;

public class ControlloVistaLogin {

    private static String TAG = ControlloVistaLogin.class.getSimpleName();
    private View.OnClickListener azioneLogin = new AzioneLogin();
    private View.OnClickListener azioneMostraVistaRegistrazione = new AzioneMostraVistaRegistrazione();

    public View.OnClickListener getAzioneLogin() {
        return azioneLogin;
    }

    public View.OnClickListener getAzioneMostraVistaRegistrazione() {
        return azioneMostraVistaRegistrazione;
    }

    private class AzioneLogin implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityLogin activityLogin = (ActivityLogin) Applicazione.getInstance().getCurrentActivity();
            VistaLogin vistaLogin = activityLogin.getVistaLogin();
            String username = vistaLogin.getUsername();
            String password = vistaLogin.getPassword();
            boolean errori = convalida(vistaLogin, username, password);
            if(errori){
                return;
            }
            ArchivioProfili archivioPorfili = (ArchivioProfili) Applicazione.getInstance().getModelloPersistente().getPersistentBean(Costanti.ARCHIVIO_PROFILI, ArchivioProfili.class);
            List<Profilo> listaProfili = archivioPorfili.getListaProfili();
            for (Profilo profilo : listaProfili) {
                if(profilo.getUsername().equals(username) && profilo.getPassword().equals(password)){
                    activityLogin.mostraMessaggioToast("Login effettuato correttamente");
                    activityLogin.mostraActivityPrincipale();
                    return;
                }
            }
            activityLogin.mostraMessaggioToast("Username e/o password errati");
        }

        private boolean convalida(VistaLogin vistaLogin, String username, String password) {
            boolean errori = false;
            if(username.trim().isEmpty()){
                errori = true;
                vistaLogin.setErroreUsername("Lo username è obbligatorio");
            }
            if(password.trim().isEmpty()){
                errori = true;
                vistaLogin.setErrorePassword("La password è obbligatoria");
            }
            return errori;
        }
    }

    private class AzioneMostraVistaRegistrazione implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ActivityLogin activityLogin = (ActivityLogin) Applicazione.getInstance().getCurrentActivity();
            VistaLogin vistaLogin = activityLogin.getVistaLogin();
            Button bottoneMostraVistaRegistrati = vistaLogin.getBottoneMostraVistaRegistrazione();
            bottoneMostraVistaRegistrati.setTextColor(ContextCompat.getColor(Applicazione.getInstance().getCurrentActivity().getBaseContext(), R.color.bottone_arrotondato_colore_finale));
            activityLogin.mostraActivityRegistrazione();
        }
    }
}
