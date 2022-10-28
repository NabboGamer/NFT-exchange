package it.unibas.nft_exchange.modello;

public class NFT {

    private String nome;
    private String descrizione;
    private int id;

    public NFT(String nome, String descrizione, int id) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NFT{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
