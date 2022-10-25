package it.unibas.nft_exchange.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.vista.FragmentInviaETH;

public class AsyncTaskInviaETH extends AsyncTask<Void, Void, Void> {

    private static String TAG = AsyncTaskInviaETH.class.getSimpleName();
    private String SENDER_PRIVATE_KEY;
    private String RECEIVER_ADDRESS;
    private BigDecimal AMOUNT;
    private BigInteger GAS_PRICE;
    private BigInteger GAS_LIMIT;

    public AsyncTaskInviaETH(String SENDER_PRIVATE_KEY, String RECEIVER_ADDRESS, BigDecimal AMOUNT, BigInteger GAS_PRICE, BigInteger GAS_LIMIT) {
        this.SENDER_PRIVATE_KEY = SENDER_PRIVATE_KEY;
        this.RECEIVER_ADDRESS = RECEIVER_ADDRESS;
        this.AMOUNT = AMOUNT;
        this.GAS_PRICE = GAS_PRICE;
        this.GAS_LIMIT = GAS_LIMIT;
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
        Transfer transfer = new Transfer(web3j, transactionManager);
        try {
            CompletableFuture<TransactionReceipt> transactionReceipt = transfer.sendFunds(RECEIVER_ADDRESS, AMOUNT, Convert.Unit.ETHER, GAS_PRICE, GAS_LIMIT).sendAsync();
            Log.d(TAG, "Transaction = " + transactionReceipt.get().getTransactionHash());
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Invio effettuato correttamente");
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage());
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Impossibile effettuare la transazione");
                }
            });
        }
        return null;
    }

    private Credentials getCredentialsFromPrivateKey() {
        return Credentials.create(SENDER_PRIVATE_KEY);
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        try{
            FragmentInviaETH fragmentInviaETH = activityPrincipale.getFragmentInviaETH();
            activityPrincipale.aggiornaFragment(fragmentInviaETH);
        } catch (ClassCastException ccex){
            Log.e(TAG,"Non è stato possibile inviare la somma poichè la finestra è stata cambiata prima di aver ottenuto la risposta dalla blockchain");
        }

    }
}
