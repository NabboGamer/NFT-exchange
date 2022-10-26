package it.unibas.nft_exchange.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.contract.ThesisToken;
import it.unibas.nft_exchange.modello.ArchivioProfili;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.Profilo;

public class AsyncTaskDeployContract extends AsyncTask<Void, Void, Void> {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private static String TAG = AsyncTaskDeployContract.class.getSimpleName();
    private Collezione collezione;
    private Profilo profiloCorrente;

    public AsyncTaskDeployContract(Collezione collezione, Profilo profiloCorrente) {
        this.collezione = collezione;
        this.profiloCorrente = profiloCorrente;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:8545"));
        TransactionManager transactionManager = new RawTransactionManager(web3j, this.getCredentialsFromPrivateKey());
        try {
            CompletableFuture<ThesisToken> thesisTokenCompletableFuture = ThesisToken.deploy(web3j, transactionManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT)).sendAsync();
            String contractAddress = thesisTokenCompletableFuture.get().getContractAddress();

            collezione.setContractAddress(contractAddress);
            profiloCorrente.aggiungiCollezione(collezione);

            ArchivioProfili archivioProfili = (ArchivioProfili) Applicazione.getInstance().getModelloPersistente().getPersistentBean(Costanti.ARCHIVIO_PROFILI, ArchivioProfili.class);
            Applicazione.getInstance().getModelloPersistente().saveBean(Costanti.ARCHIVIO_PROFILI,archivioProfili);

            Log.d(TAG, "Profilo corrente Async: " + profiloCorrente);
            Log.d(TAG, "Collezione corrente Async: " + collezione);
            Log.d(TAG, "ThesisToke corrente Async: " + contractAddress);

            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Collezione creata correttamente");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Impossibile creare la collezione");
                }
            });
        }
        return null;
    }

    private Credentials getCredentialsFromPrivateKey() {
        return Credentials.create(profiloCorrente.getChiavePrivata());
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}
