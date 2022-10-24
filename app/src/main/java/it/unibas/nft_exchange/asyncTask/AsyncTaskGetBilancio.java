package it.unibas.nft_exchange.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.vista.FragmentInviaETH;

public class AsyncTaskGetBilancio extends AsyncTask<Void, Void, BigDecimal> {

    private static String TAG = AsyncTaskGetBilancio.class.getSimpleName();
    private String address;

    public AsyncTaskGetBilancio(String address) {
        this.address = address;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected BigDecimal doInBackground(Void... voids) {
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:8545"));
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3j.ethGetBalance(this.address, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (Exception e) {
            e.printStackTrace();
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Impossibile stabilire una connessione con la BlockChain");
                }
            });
            return BigDecimal.ZERO;
        }
        BigInteger wei = ethGetBalance.getBalance();
        Log.d(TAG, "Bilancio in wei: " + wei);
        BigDecimal bilancioETH = Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
        Log.d(TAG, "Bilancio in ETH: " + bilancioETH);
        return bilancioETH;
    }

    @Override
    protected void onPostExecute(BigDecimal bilancioETH) {
        super.onPostExecute(bilancioETH);
        StringBuilder stringaBilancioETH = new StringBuilder();
        stringaBilancioETH.append(bilancioETH).append(" ETH");
        Log.d(TAG, "Bilancio in ETH: " + stringaBilancioETH.toString());
        Applicazione.getInstance().getModello().putBean(Costanti.STRINGA_BILANCIO_ETH, stringaBilancioETH.toString());
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        FragmentInviaETH fragmentInviaETH = activityPrincipale.getFragmentInviaETH();
        fragmentInviaETH.getLabelBilancioInETHInviaETH().setText(stringaBilancioETH);
    }
}
