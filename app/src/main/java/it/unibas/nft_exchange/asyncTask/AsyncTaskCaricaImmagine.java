package it.unibas.nft_exchange.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.contract.ThesisToken;
import it.unibas.nft_exchange.modello.Profilo;

public class AsyncTaskCaricaImmagine extends AsyncTask<Void, Void, Void> {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private static String TAG = AsyncTaskCaricaImmagine.class.getSimpleName();
    private String contractAddress;
    private BigInteger id;
    private ImageView boxImmagineMostraNFT;

    public AsyncTaskCaricaImmagine(String contractAddress, BigInteger id, ImageView boxImmagineMostraNFT) {
        this.contractAddress = contractAddress;
        this.id = id;
        this.boxImmagineMostraNFT = boxImmagineMostraNFT;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);
        ActivityPrincipale activityPrincipale = (ActivityPrincipale) Applicazione.getInstance().getCurrentActivity();
        Web3j web3j = Web3j.build(new HttpService("http://10.0.2.2:8545"));
        TransactionManager transactionManager = new RawTransactionManager(web3j, Credentials.create(profiloCorrente.getChiavePrivata()));
        try {
            ThesisToken thesisToken = ThesisToken.load(contractAddress, web3j, transactionManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));
            String uri = thesisToken.tokenURI(id).sendAsync().get();
            Log.d(TAG, "Uri caricato dal contratto: " + uri);

            // Recupero il file da IPFS
            IPFS ipfs = new IPFS("/ip4/10.0.2.2/tcp/5001");
            String hash = uri;
            Multihash multihash = Multihash.fromBase58(hash);
            byte[] content = ipfs.cat(multihash);
            String bitMapImmagine = new String(content);
            Log.d(TAG, "Content of " + hash + ": " + bitMapImmagine);
            // Setto l'immagine
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}
