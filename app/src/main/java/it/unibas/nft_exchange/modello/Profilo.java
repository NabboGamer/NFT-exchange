package it.unibas.nft_exchange.modello;

import java.util.ArrayList;
import java.util.List;

public class Profilo {

    private String username;
    private String password;
    private String chiavePrivata;
    private List<Collezione> listaCollezioni;

    public Profilo(String username, String password, String chiavePrivata) {
        this.username = username;
        this.password = password;
        this.chiavePrivata = chiavePrivata;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChiavePrivata() {
        return chiavePrivata;
    }

    public void setChiavePrivata(String chiavePrivata) {
        this.chiavePrivata = chiavePrivata;
    }

    public List<Collezione> getListaCollezioni() {
        if(this.listaCollezioni == null){
            this.listaCollezioni = new ArrayList<>();
        }
        return listaCollezioni;
    }

    public void setListaCollezioni(List<Collezione> listaCollezioni) {
        this.listaCollezioni = listaCollezioni;
    }

    public boolean equals(Profilo altroProfilo) {
        if(this.username.equals(altroProfilo.getUsername()) &&
           this.password.equals(altroProfilo.getPassword()) &&
           this.chiavePrivata.equals(altroProfilo.getChiavePrivata())){
            return true;
        }
        return false;
    }

    public void aggiungiCollezione(Collezione collezione){
        if(listaCollezioni == null){
            listaCollezioni = new ArrayList<>();
        }
        this.listaCollezioni.add(collezione);
    }

    public boolean isCollezioneEsistente(String nome){
        if(listaCollezioni == null){
            listaCollezioni = new ArrayList<>();
        }
        for (Collezione collezione : this.listaCollezioni) {
            if(collezione.getNome().equals(nome)){
                return true;
            }
        }
        return false;
    }

    public Collezione getCollezioneByIdCollezione(String idCollezione){
        for (Collezione collezione : this.listaCollezioni) {
            String idAltraCollezione = collezione.getNome() + "Of" + collezione.getUsernameCreatore();
            if(idCollezione.equals(idAltraCollezione.replaceAll("\\s", ""))){
                return collezione;
            }
        }
        return null;
    }

    public Collezione getCollezioneGiaEsistente(Collezione collezioneDaverificare){
        if(this.listaCollezioni == null){
            this.listaCollezioni = new ArrayList<>();
        }
        for (Collezione collezione : this.listaCollezioni) {
            if(collezione.getNome().equals(collezioneDaverificare.getNome()) &&
               collezione.getUsernameCreatore().equals(collezioneDaverificare.getUsernameCreatore())){
                return collezione;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profilo{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", chiavePrivata='").append(chiavePrivata).append('\'');
        sb.append(", listaCollezioni=").append(listaCollezioni);
        sb.append('}');
        return sb.toString();
    }
}
