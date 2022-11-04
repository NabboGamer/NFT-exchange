package it.unibas.nft_exchange.controllo;

import android.content.DialogInterface;

import java.math.BigDecimal;
import java.math.BigInteger;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.asyncTask.AsyncTaskDeployContract;
import it.unibas.nft_exchange.asyncTask.AsyncTaskInviaETH;
import it.unibas.nft_exchange.asyncTask.AsyncTaskInviaNFT;
import it.unibas.nft_exchange.asyncTask.AsyncTaskMintNFT;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;
import it.unibas.nft_exchange.modello.Profilo;

public class ControlloPrincipale {

    private DialogInterface.OnClickListener azioneConcediConsensoCreazioneCollezione = new AzioneConcediConsensoCreazioneCollezione();
    private DialogInterface.OnClickListener azioneNegaConsensoCreazioneCollezione = new AzioneNegaConsensoCreazioneCollezione();

    private DialogInterface.OnClickListener azioneConcediConsensoCreazioneNFT = new AzioneConcediConsensoCreazioneNFT();
    private DialogInterface.OnClickListener azioneNegaConsensoCreazioneNFT = new AzioneNegaConsensoCreazioneNFT();

    private DialogInterface.OnClickListener azioneConcediConsensoInviaNFT = new AzioneConcediConsensoInviaNFT();
    private DialogInterface.OnClickListener azioneNegaConsensoInviaNFT = new AzioneNegaConsensoInviaNFT();

    private DialogInterface.OnClickListener azioneConcediConsensoInviaETH = new AzioneConcediConsensoInviaETH();
    private DialogInterface.OnClickListener azioneNegaConsensoInviaETH = new AzioneNegaConsensoInviaETH();

    public DialogInterface.OnClickListener getAzioneConcediConsensoCreazioneCollezione() {
        return azioneConcediConsensoCreazioneCollezione;
    }

    public DialogInterface.OnClickListener getAzioneNegaConsensoCreazioneCollezione() {
        return azioneNegaConsensoCreazioneCollezione;
    }

    public DialogInterface.OnClickListener getAzioneConcediConsensoCreazioneNFT() {
        return azioneConcediConsensoCreazioneNFT;
    }

    public DialogInterface.OnClickListener getAzioneNegaConsensoCreazioneNFT() {
        return azioneNegaConsensoCreazioneNFT;
    }

    public DialogInterface.OnClickListener getAzioneConcediConsensoInviaNFT() {
        return azioneConcediConsensoInviaNFT;
    }

    public DialogInterface.OnClickListener getAzioneNegaConsensoInviaNFT() {
        return azioneNegaConsensoInviaNFT;
    }

    public DialogInterface.OnClickListener getAzioneConcediConsensoInviaETH() {
        return azioneConcediConsensoInviaETH;
    }

    public DialogInterface.OnClickListener getAzioneNegaConsensoInviaETH() {
        return azioneNegaConsensoInviaETH;
    }

    private class AzioneConcediConsensoCreazioneCollezione implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Collezione collezione = (Collezione) Applicazione.getInstance().getModello().getBean(Costanti.COLLEZIONE_DA_CREARE);
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            new AsyncTaskDeployContract(collezione, profiloCorrente).execute();
        }
    }

    private class AzioneNegaConsensoCreazioneCollezione implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            return;
        }
    }

    private class AzioneConcediConsensoCreazioneNFT implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            NFT nuovoNFT = (NFT) Applicazione.getInstance().getModello().getBean(Costanti.NUOVO_NFT);
            Collezione collezioneSelezionata = (Collezione) Applicazione.getInstance().getModello().getBean(Costanti.COLLEZIONE_SELEZIONATA);
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            new AsyncTaskMintNFT(nuovoNFT, collezioneSelezionata, profiloCorrente).execute();
        }
    }

    private class AzioneNegaConsensoCreazioneNFT implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            return;
        }
    }

    private class AzioneConcediConsensoInviaNFT implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
            String indirizzoDestinatario = (String) Applicazione.getInstance().getModello().getBean(Costanti.INDIRIZZO_DESTINATARIO);
            NFT nftSelezionato = (NFT) Applicazione.getInstance().getModello().getBean(Costanti.NFT_SELEZIONATO);
            new AsyncTaskInviaNFT(profiloCorrente, indirizzoDestinatario, nftSelezionato).execute();
        }
    }

    private class AzioneNegaConsensoInviaNFT implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            return;
        }
    }

    private class AzioneConcediConsensoInviaETH implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String chiavePrivataMittente = (String) Applicazione.getInstance().getModello().getBean(Costanti.CHIAVE_PRIVATA_MITTENTE);
            String indirizzoDestinatario = (String) Applicazione.getInstance().getModello().getBean(Costanti.INDIRIZZO_DESTINATARIO_INVIO_ETH);
            BigDecimal importoAggiustato = (BigDecimal) Applicazione.getInstance().getModello().getBean(Costanti.IMPORTO_AGGIUSTATO);
            BigInteger prezzoGASAggiustato = (BigInteger) Applicazione.getInstance().getModello().getBean(Costanti.PREZZO_GAS_AGGIUSTATO);
            BigInteger limiteGASAggiustato = (BigInteger) Applicazione.getInstance().getModello().getBean(Costanti.LIMITE_GAS_AGGIUSTATO);
            new AsyncTaskInviaETH(chiavePrivataMittente, indirizzoDestinatario, importoAggiustato, prezzoGASAggiustato, limiteGASAggiustato).execute();
        }
    }

    private class AzioneNegaConsensoInviaETH implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            return;
        }
    }
}
