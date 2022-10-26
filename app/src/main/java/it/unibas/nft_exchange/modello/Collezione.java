package it.unibas.nft_exchange.modello;

public class Collezione {

    private String nome;
    private String descrizione;
    private String contractAddress;

    public Collezione(String nome, String descrizione) {
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

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Collezione{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append(", contractAddress='").append(contractAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}