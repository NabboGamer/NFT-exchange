package it.unibas.nft_exchange.modello;

import java.util.ArrayList;
import java.util.List;

public class ArchivioProfili {

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ArchivioProfili{");
        sb.append("listaProfili=").append(listaProfili);
        sb.append('}');
        return sb.toString();
    }
}
