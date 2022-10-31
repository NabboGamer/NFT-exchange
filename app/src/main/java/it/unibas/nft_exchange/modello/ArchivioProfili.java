package it.unibas.nft_exchange.modello;

import android.util.Log;

import org.web3j.crypto.Credentials;

import java.util.ArrayList;
import java.util.List;

public class ArchivioProfili {

    private static String TAG = ArchivioProfili.class.getSimpleName();
    private List<Profilo> listaProfili = new ArrayList<>();

    public ArchivioProfili(List<Profilo> listaProfili) {
        this.listaProfili = listaProfili;
    }

    public List<Profilo> getListaProfili() {
        return listaProfili;
    }

    public void setListaProfili(List<Profilo> listaProfili) {
        this.listaProfili = listaProfili;
    }

    public void aggiungiProfilo(Profilo profilo){
        this.listaProfili.add(profilo);
    }

    public boolean isProfiloEsistente(Profilo profiloDaVerificare){
        for (Profilo profilo : listaProfili) {
            if(profilo.equals(profiloDaVerificare)){
                return true;
            }
        }
        return false;
    }

    public boolean isProfiloStessoUsername(Profilo profiloDaVerificare){
        for (Profilo profilo : listaProfili) {
            if(profilo.getUsername().equals(profiloDaVerificare.getUsername())){
                return true;
            }
        }
        return false;
    }

    public Profilo getProfiloByAddress(String indirizzo){
        Log.d(TAG, "indirizzo passato: " + indirizzo);
        for (Profilo profilo : this.listaProfili) {
            Credentials credentials = Credentials.create(profilo.getChiavePrivata());
            Log.d(TAG, "indirizzo profilo: " + credentials.getAddress());
            if(credentials.getAddress().equalsIgnoreCase(indirizzo)){
                return profilo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArchivioProfili{");
        sb.append("listaProfili=").append(listaProfili);
        sb.append('}');
        return sb.toString();
    }
}
