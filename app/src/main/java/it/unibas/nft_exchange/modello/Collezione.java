package it.unibas.nft_exchange.modello;

import java.util.ArrayList;
import java.util.List;

public class Collezione {

    private String nome;
    private String descrizione;
    private String contractAddress;
    private String usernameCreatore;
    private List<NFT> listaNFT = new ArrayList<>();

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

    public String getUsernameCreatore() {
        return usernameCreatore;
    }

    public void setUsernameCreatore(String usernameCreatore) {
        this.usernameCreatore = usernameCreatore;
    }

    public List<NFT> getListaNFT() {
        return listaNFT;
    }

    public void setListaNFT(List<NFT> listaNFT) {
        this.listaNFT = listaNFT;
    }

    public void aggiungiNFT (NFT nft){
        if(listaNFT == null){
            this.listaNFT = new ArrayList<>();
        }
        this.listaNFT.add(nft);
    }

    public void rimuoviNFT (NFT nft){
        NFT nftDaRimuovere = null;
        for (NFT altroNFT: this.listaNFT) {
            if(nft.getId().equals(altroNFT.getId())){
                nftDaRimuovere = altroNFT;
            }
        }
        this.listaNFT.remove(nftDaRimuovere);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Collezione{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", descrizione='").append(descrizione).append('\'');
        sb.append(", contractAddress='").append(contractAddress).append('\'');
        sb.append(", usernameCreatore='").append(usernameCreatore).append('\'');
        sb.append(", listaNFT=").append(listaNFT);
        sb.append('}');
        return sb.toString();
    }
}
