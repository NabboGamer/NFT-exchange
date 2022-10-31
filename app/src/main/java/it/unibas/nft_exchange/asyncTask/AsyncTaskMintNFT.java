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

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.activity.ActivityPrincipale;
import it.unibas.nft_exchange.contract.ThesisToken;
import it.unibas.nft_exchange.modello.ArchivioProfili;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;
import it.unibas.nft_exchange.modello.Profilo;

public class AsyncTaskMintNFT extends AsyncTask<Void, Void, Void> {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private static String TAG = AsyncTaskMintNFT.class.getSimpleName();
    private NFT nuovoNFT;
    private Collezione collezioneDiAppartenenza;
    private Profilo profiloCorrente;
    private int contatore = 0;

    public AsyncTaskMintNFT(NFT nuovoNFT, Collezione collezioneDiAppartenenza, Profilo profiloCorrente) {
        this.nuovoNFT = nuovoNFT;
        this.collezioneDiAppartenenza = collezioneDiAppartenenza;
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
        TransactionManager transactionManager = new RawTransactionManager(web3j, Credentials.create(profiloCorrente.getChiavePrivata()));
        try {
            //////////////////// PARTE NECESSARIA PER TRATTARE LA BITMAP E CARICARLA SU IPFS /////////////////////////////////////////////////////////

            // Aggiungo il file a IPFS
            IPFS ipfs = new IPFS("/ip4/10.0.2.2/tcp/5001");
            String path = (String) Applicazione.getInstance().getModello().getBean(Costanti.PATH_IMMAGINE_SELEZIONATA);
            Log.d(TAG, "Path:" + path);
            NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(path));
            MerkleNode response = ipfs.add(file).get(0);
            String hash = response.hash.toBase58(); // Hash of the file
            Log.d(TAG, "Hash (base 58): " + hash);
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            //////////////////// PARTE NECESSARIA A LIVELLO DI BLOCKCHAIN PER CREARE L'NFT (MINT) ///////////////////////////////////////////////////
            String chiavePrivataDestinatario = profiloCorrente.getChiavePrivata();
            String indirizzoDestinatario = Credentials.create(chiavePrivataDestinatario).getAddress();
            String indirizzoContratto = collezioneDiAppartenenza.getContractAddress();
            ThesisToken thesisToken = ThesisToken.load(indirizzoContratto, web3j, transactionManager, new StaticGasProvider(GAS_PRICE, GAS_LIMIT));
            TransactionReceipt transactionReceipt = thesisToken.safeMint(indirizzoDestinatario, hash).sendAsync().get();
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            ////////// PARTE NECESSARIA A LIVELLO APPLICATIVO PER RENDERE CONSISTENTE LA CREAZIONE DEL NFT ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // NOTA SE VIENE PULITA LA CACHE DELL'APP ESSA SI DESINCRONIZZA RISPETTO ALLA BLOCKCHAIN SE INVECE LA CHIUDO SOLTANTO ESSA FUNZIONA BENE:
            // CIO È DOVUTO AL FATTO CHE SE VIENE PULITA LA CACHE DELL'APP IL CONTRATTO CON IL QUALE VADO A INTERAGIRE LO RICREO PERCHÈ HO PERSO LA COLLEZIONE CHE SI OCCUPA DI MEMORIZZARLO E QUINDI AVRÀ UN DIVERSO CONTRACTADDRESS;
            // MENTRE SE CHIUDO SOLTANTO L'APP IL CONTATORE SI AZZERA MA QUESTO VA BENE PERCHÈ SI AZZERA ANCHE LA LISTA DEGLI EVENTI DI TRASFERIMENTO, PERÒ IL CONTRATTO È SEMPRE LO STESSO AVENDO IO MEMORIZZATO IN MANIERA PERMANENTE ARCHIVIOPROFILI E QUINDI TRANSITIVAMENTE ANCHE COLLEZIONE.
            List<ThesisToken.TransferEventResponse> transferEvents = thesisToken.getTransferEvents(transactionReceipt);
            BigInteger tokenId = transferEvents.get(contatore).tokenId;
            Log.d(TAG, "token id: " + tokenId);
            Log.d(TAG, "from: " + transferEvents.get(contatore).from);
            Log.d(TAG, "to: " + transferEvents.get(contatore).to);
            Log.d(TAG, "log: " + transferEvents.get(contatore).log);
            Log.d(TAG, "token uri: " + thesisToken.tokenURI(tokenId).sendAsync().get());
            contatore = contatore + 1;
            nuovoNFT.setId(tokenId);
            collezioneDiAppartenenza.aggiungiNFT(nuovoNFT);
            ArchivioProfili archivioProfili = (ArchivioProfili) Applicazione.getInstance().getModelloPersistente().getPersistentBean(Costanti.ARCHIVIO_PROFILI, ArchivioProfili.class);
            Applicazione.getInstance().getModelloPersistente().saveBean(Costanti.ARCHIVIO_PROFILI,archivioProfili);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            Log.d(TAG, "Stampa profilo corrente: " + profiloCorrente);

            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("NFT creato correttamente");
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            activityPrincipale.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityPrincipale.mostraMessaggioToast("Impossibile creare l'NFT");
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
