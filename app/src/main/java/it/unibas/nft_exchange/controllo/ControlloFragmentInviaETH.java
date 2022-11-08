package it.unibas.nft_exchange.controllo;

import android.util.Log;
import android.view.View;

import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.modello.Profilo;
import it.unibas.nft_exchange.vista.FragmentInviaETH;

public class ControlloFragmentInviaETH {

    private static String TAG = ControlloFragmentInviaETH.class.getSimpleName();

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
            String indirizzoDestinatario = fragmentInviaETH.getIndirizzoDiInvio();
            String stringaImporto = fragmentInviaETH.getImportoInvio();
            String stringaPrezzoGAS = fragmentInviaETH.getPrezzoGAS();
            String stringaLimiteGAS = fragmentInviaETH.getLimiteGAS();
            Boolean errori = convalida(fragmentInviaETH, indirizzoDestinatario, stringaImporto, stringaPrezzoGAS, stringaLimiteGAS);
            if(errori){
                return;
            }

            double importo = Double.parseDouble(stringaImporto);
            int prezzoGas = Integer.parseInt(stringaPrezzoGAS);
            int limiteGAS = Integer.parseInt(stringaLimiteGAS);

            BigDecimal importoAggiustato = BigDecimal.valueOf(importo);
            BigInteger prezzoGASAggiustato =  BigInteger.valueOf((long) (prezzoGas * Math.pow(10, 9)));
            BigInteger limiteGASAggiustato =  BigInteger.valueOf(limiteGAS);

            BigDecimal commissioni = new BigDecimal(prezzoGASAggiustato.multiply(limiteGASAggiustato));
            BigDecimal commissioniInETH = Convert.fromWei(commissioni, Convert.Unit.ETHER);
            BigDecimal costoTotale = importoAggiustato.add(commissioniInETH);

            Log.d(TAG, "Prezzo del GAS in BigInteger: " +  prezzoGASAggiustato);
            Log.d(TAG, "Limite del GAS in BigInteger: " + limiteGASAggiustato);
            Log.d(TAG, "Prezzo delle commissioni in BigDecimal: " + commissioni);
            Log.d(TAG, "Prezzo delle commissioni in BigDecimal in ETH: " + commissioniInETH);
            Log.d(TAG, "Costo totale in BigDecimal in ETH: " + costoTotale);

            BigDecimal bilancioAccountInETH = (BigDecimal) Applicazione.getInstance().getModello().getBean(Costanti.BILANCIO_ETH);
            Log.d(TAG,"Bilancio account in ETH caso mancata connessione: " + bilancioAccountInETH);
            if(bilancioAccountInETH == null){
                activityPrincipale.mostraMessaggioToast("Non possiedi fondi sufficienti");
                return;
            }
            if(bilancioAccountInETH.compareTo(costoTotale) == -1){
                activityPrincipale.mostraMessaggioToast("Non possiedi fondi sufficienti");
                return;
            }
            String chiavePrivataMittente = profilo.getChiavePrivata();
            Applicazione.getInstance().getModello().putBean(Costanti.CHIAVE_PRIVATA_MITTENTE, chiavePrivataMittente);
            Applicazione.getInstance().getModello().putBean(Costanti.INDIRIZZO_DESTINATARIO_INVIO_ETH, indirizzoDestinatario);
            Applicazione.getInstance().getModello().putBean(Costanti.IMPORTO_AGGIUSTATO, importoAggiustato);
            Applicazione.getInstance().getModello().putBean(Costanti.PREZZO_GAS_AGGIUSTATO, prezzoGASAggiustato);
            Applicazione.getInstance().getModello().putBean(Costanti.LIMITE_GAS_AGGIUSTATO, limiteGASAggiustato);
            BigInteger gasPrice = BigInteger.valueOf(20000000000L);
            BigInteger gasLimit = BigInteger.valueOf(6721975L);
            BigDecimal commissioni1 = new BigDecimal(gasPrice.multiply(gasLimit));
            BigDecimal commissioniInETH1 = Convert.fromWei(commissioni1, Convert.Unit.ETHER);
            activityPrincipale.mostraMessaggioAlertInviaETH("ATTENZIONE inviare una somma richiederà al massimo " + commissioniInETH1 + " ETH");
        }

        private Boolean convalida(FragmentInviaETH fragmentInviaETH, String indirizzoDestinatario, String stringaImporto, String stringaPrezzoGAS, String stringaLimiteGAS) {
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
            if(stringaImporto.trim().isEmpty()){
                fragmentInviaETH.setErroreImportoInviaETH("L'importo è obbligatorio");
                errori = true;
            }else{
                double importo = Double.parseDouble(stringaImporto);
                if(importo == 0){
                    fragmentInviaETH.setErroreImportoInviaETH("Importo non valido");
                    errori = true;
                }
            }
            if(stringaPrezzoGAS.trim().isEmpty()){
                fragmentInviaETH.setErrorePrezzoGASInviaETH("Il prezzo del GAS è obbligatorio");
                errori = true;
            }else{
                int prezzoGAS = Integer.parseInt(stringaPrezzoGAS);
                if(prezzoGAS == 0){
                    fragmentInviaETH.setErrorePrezzoGASInviaETH("Prezzo GAS non valido");
                    errori = true;
                }
            }
            if(stringaLimiteGAS.trim().isEmpty()){
                fragmentInviaETH.setErroreLimiteGASInviaETH("Il limite del GAS è obbligatorio");
                errori = true;
            }else{
                int limiteGAS = Integer.parseInt(stringaLimiteGAS);
                if(limiteGAS == 0){
                    fragmentInviaETH.setErroreLimiteGASInviaETH("Limite GAS non valido");
                    errori = true;
                }
            }
            return errori;
        }
    }
}
