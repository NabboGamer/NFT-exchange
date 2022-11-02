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
import it.unibas.nft_exchange.asyncTask.AsyncTaskCaricaImmagineSpinner;
import it.unibas.nft_exchange.modello.Collezione;
import it.unibas.nft_exchange.modello.NFT;

public class AdapterNFTsSpinner extends BaseAdapter {

    private static String TAG = AdapterNFTsSpinner.class.getSimpleName();

    private List<NFT> listaNFT;

    public AdapterNFTsSpinner(List<NFT> listaNFT) {
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

        TextView labelNomeNFTMostraNFT = riga.findViewById(R.id.labelNomeNFTMostraNFT);
        labelNomeNFTMostraNFT.setText(nft.getNome());

        TextView labelDescrizioneNFTMostraNFT = riga.findViewById(R.id.labelDescrizioneNFTMostraNFT);
        labelDescrizioneNFTMostraNFT.setText(nft.getDescrizione());

        ImageView boxImmagineMostraNFT = riga.findViewById(R.id.boxImmagineMostraNFT);
        List<Collezione> listaCollezioni = (List<Collezione>) Applicazione.getInstance().getModello().getBean(Costanti.LISTA_COLLEZIONI);
        Collezione collezioneSelezionata = null;
        //Log.d(TAG, "Lista collezioni: " + listaCollezioni);
        for (Collezione collezione : listaCollezioni) {
            String idCollezione = collezione.getNome() + "Of" + collezione.getUsernameCreatore();
            if (nft.getIdCollezione().equals(idCollezione.replaceAll("\\s", ""))){
                collezioneSelezionata = collezione;
            }
        }
        new AsyncTaskCaricaImmagineSpinner(collezioneSelezionata.getContractAddress(), nft.getId(), boxImmagineMostraNFT).execute();

        return riga;
    }

    public void aggiornaDati(){
        this.notifyDataSetChanged();
    }
}
