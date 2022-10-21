package it.unibas.nft_exchange.modello;

public class Profilo {

    private String username;
    private String password;
    private String chiavePrivata;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profilo{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", chiavePrivata='").append(chiavePrivata).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
