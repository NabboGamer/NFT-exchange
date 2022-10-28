package it.unibas.nft_exchange.vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.modello.Collezione;

public class AdapterCollezioni extends BaseAdapter {

    private static String TAG = AdapterCollezioni.class.getSimpleName();

    private List<Collezione> listaCollezioni;

    public AdapterCollezioni(List<Collezione> listaCollezioni) {
        this.listaCollezioni = listaCollezioni;
    }

    @Override
    public int getCount() {
        if(listaCollezioni == null){
            return 0;
        }
        return listaCollezioni.size();
    }

    @Override
    public Object getItem(int i) {
        return listaCollezioni.get(i);
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
            riga = layoutInflater.inflate(R.layout.riga_collezione_spinner,parent,false);
        }
        Collezione collezione = this.listaCollezioni.get(pos);
        TextView labelNomeCollezione = riga.findViewById(R.id.labelNomeCollezione);
        labelNomeCollezione.setText(collezione.getNome());

        TextView labelCreatoreCollezione = riga.findViewById(R.id.labelCreatoreCollezione);
        labelCreatoreCollezione.setText("by " + collezione.getUsernameCreatore());

        TextView labelDescrizioneCollezione = riga.findViewById(R.id.labelDescrizioneCollezione);
        labelDescrizioneCollezione.setText(collezione.getDescrizione());

        return riga;
    }

    public void aggiornaDati(){
        this.notifyDataSetChanged();
    }
}
