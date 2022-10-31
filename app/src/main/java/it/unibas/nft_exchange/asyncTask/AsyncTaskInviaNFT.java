package it.unibas.nft_exchange.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.contract.ThesisToken;
import it.unibas.nft_exchange.modello.ArchivioProfili;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;
import it.unibas.nft_exchange.modello.Profilo;

public class AsyncTaskInviaNFT extends AsyncTask<Void, Void, Void> {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private static String TAG = AsyncTaskInviaNFT.class.getSimpleName();
    private Profilo profiloCorrente;
    private String indirizzoDestinatario;
    private NFT nftCorrente;

    public AsyncTaskInviaNFT(Profilo profiloCorrente, String indirizzoDestinatario, NFT nftCorrente) {
        this.profiloCorrente = profiloCorrente;
        this.indirizzoDestinatario = indirizzoDestinatario;
        this.nftCorrente = nftCorrente;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:8545"));
        TransactionManager transactionManager = new RawTransactionManager(web3j, Credentials.create(profiloCorrente.getChiavePrivata()));
        try {
            Collezione collezioneCorrente = profiloCorrente.getCollezioneByIdCollezione(nftCorrente.getIdCollezione());
            String indirizzoMittente =  Credentials.create(profiloCorrente.getChiavePrivata()).getAddress();
            String indirizzoDestintario = this.indirizzoDestinatario;
            BigInteger tokenId = nftCorrente.getId();

            ////////// PARTE NECESSARIA A LIVELLO APPLICATIVO PER RENDERE CONSISTENTE IL TRASFERIMENTO DEL NFT //////////////////////////////////////////////////
            ArchivioProfili archivioProfili = (ArchivioProfili) Applicazione.getInstance().getModelloPersistente().getPersistentBean(Costanti.ARCHIVIO_PROFILI, ArchivioProfili.class);
            Profilo profiloDestinatario = archivioProfili.getProfiloByAddress(this.indirizzoDestinatario);
            Log.d(TAG, "Profilo Destinatario: " + profiloDestinatario);
            Log.d(TAG, "Archivio profili: " + archivioProfili);
            if(profiloDestinatario == null){
                throw new Exception("Profilo inesistente");
            }
            Log.d(TAG, "Profilo mittente: " + profiloCorrente);
            Log.d(TAG, "Profilo destinatario: " + profiloDestinatario);
            Collezione nuovaCollezione = new Collezione(collezioneCorrente.getNome(), collezioneCorrente.getDescrizione());
            nuovaCollezione.setContractAddress(collezioneCorrente.getContractAddress());
            nuovaCollezione.setUsernameCreatore(profiloCorrente.getUsername());
            nuovaCollezione.aggiungiNFT(nftCorrente);
            profiloDestinatario.aggiungiCollezione(nuovaCollezione);
            collezioneCorrente.rimuoviNFT(nftCorrente);
            Applicazione.getInstance().getModelloPersistente().saveBean(Costanti.ARCHIVIO_PROFILI,archivioProfili);
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //////////////////// PARTE NECESSARIA A LIVELLO DI BLOCKCHAIN PER TRASFERIRE L'NFT (SAFETRANSFER) ///////////////////////////////////////////////////////////
            ThesisToken thesisToken = ThesisToken.load(collezioneCorrente.getContractAddress(), web3j, transactionManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));
            TransactionReceipt transactionReceipt = thesisToken.safeTransferFrom(indirizzoMittente, indirizzoDestintario, tokenId).sendAsync().get();
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            Log.d(TAG, "Profilo mittente: " + profiloCorrente);
            Log.d(TAG, "Profilo destinatario: " + profiloDestinatario);
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("NFT inviato correttamente");
                    //activityPrincipale.aggiornaFragment(activityPrincipale.getFragmentInviaNFT());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Impossibile inviare l'NFT");
                }
            });
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}
