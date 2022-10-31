package it.unibas.nft_exchange.vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;

public class AdapterNFTs extends BaseAdapter {

    private static String TAG = AdapterNFTs.class.getSimpleName();

    private List<NFT> listaNFT;

    public AdapterNFTs(List<NFT> listaNFT) {
        this.listaNFT = listaNFT;
    }

    @Override
    public int getCount() {
        if(listaNFT == null){
            return 0;
        }
        return listaNFT.size();
    }

    @Override
    public Object getItem(int i) {
        return listaNFT.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View recycledView, ViewGroup parent) {
        View riga;
        if(recycledView != null){
            riga = recycledView;
        }else{
            LayoutInflater layoutInflater = (LayoutInflater) Applicazione.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            riga = layoutInflater.inflate(R.layout.riga_nft,parent,false);
        }
        NFT nft = this.listaNFT.get(pos);

        ImageView boxImmagineMostraNFT = riga.findViewById(R.id.boxImmagineMostraNFT);
        Collezione collezioneSelezionata = (Collezione) Applicazione.getInstance().getModello().getBean(Costanti.COLLEZIONE_SELEZIONATA_DA_LISTA);
        //new AsyncTaskCaricaImmagine(collezioneSelezionata.getContractAddress(), nft.getId(), boxImmagineMostraNFT).execute();

        TextView labelNomeNFTMostraNFT = riga.findViewById(R.id.labelNomeNFTMostraNFT);

        TextView labelDescrizioneNFTMostraNFT = riga.findViewById(R.id.labelDescrizioneNFTMostraNFT);

        return riga;
    }

    public void aggiornaDati(){
        this.notifyDataSetChanged();
    }
}
