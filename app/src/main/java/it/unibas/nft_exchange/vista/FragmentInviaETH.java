package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.web3j.crypto.Credentials;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.asyncTask.AsyncTaskGetBilancio;
import it.unibas.nft_exchange.modello.Profilo;

public class FragmentInviaETH extends Fragment {

    private static String TAG = FragmentInviaETH.class.getSimpleName();
    private TextView labelUsernameInviaETH;
    private TextView labelAddressInviaETH;
    private TextView labelBilancioInETHInviaETH;
    private EditText campoIndirizzoInviaETH;
    private Button bottoneInviaETH;
    private Profilo profiloCorrente = (Profilo) Applicazione.getInstance().getModello().getBean(Costanti.PROFILO_CORRENTE);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_invia_e_t_h, container, false);
        this.labelUsernameInviaETH = vista.findViewById(R.id.labelUsernameInviaETH);
        this.labelAddressInviaETH = vista.findViewById(R.id.labelAddressInviaETH);
        this.labelBilancioInETHInviaETH = vista.findViewById(R.id.labelBilancioInETHInviaETH);
        this.campoIndirizzoInviaETH = vista.findViewById(R.id.campoIndirizzoInviaETH);
        this.bottoneInviaETH = vista.findViewById(R.id.bottoneInviaETH);
        this.bottoneInviaETH.setOnClickListener(Applicazione.getInstance().getControlloFragmentInviaETH().getAzioneInviaETH());
        this.inizializzaLabel();
        return vista;
    }

    public void inizializzaLabel(){
        this.labelUsernameInviaETH.setText(profiloCorrente.getUsername());

        Credentials credentials = Credentials.create(profiloCorrente.getChiavePrivata());
        this.labelAddressInviaETH.setText(credentials.getAddress());

        new AsyncTaskGetBilancio(credentials.getAddress()).execute();
        String stringaBilancioETH = (String) Applicazione.getInstance().getModello().getBean(Costanti.STRINGA_BILANCIO_ETH);
        Log.d(TAG,"Stringa Bilancio ETH: " + stringaBilancioETH);
        if(stringaBilancioETH == null){
            return;
        }else{
            this.labelBilancioInETHInviaETH.setText(stringaBilancioETH);
        }
    }

    public String getIndirizzoDiInvio(){
        return this.campoIndirizzoInviaETH.getText().toString();
    }

    public TextView getLabelBilancioInETHInviaETH() {
        return labelBilancioInETHInviaETH;
    }

    public void setErroreIndirizzoInvioETH(String err){
        this.campoIndirizzoInviaETH.setError(err);
    }
}