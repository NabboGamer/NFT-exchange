package it.unibas.nft_exchange.modello;

import java.math.BigInteger;

public class NFT {

    private String nome;
    private String descrizione;
    private BigInteger id;
    private String idCollezione;

    public NFT(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getIdCollezione() {
        return idCollezione;
    }

    public void setIdCollezione(String idCollezione) {
        this.idCollezione = idCollezione;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NFT{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append(", id=").append(id);
        sb.append(", idCollezione='").append(idCollezione).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
