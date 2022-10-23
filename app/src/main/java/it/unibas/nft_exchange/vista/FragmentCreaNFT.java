package it.unibas.nft_exchange.vista;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import it.unibas.nft_exchange.R;

public class FragmentCreaNFT extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_crea_n_f_t, container, false);

        return vista;
    }
}