package it.unibas.nft_exchange.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;

import it.unibas.nft_exchange.Applicazione;
import it.unibas.nft_exchange.Costanti;
import it.unibas.nft_exchange.R;
import it.unibas.nft_exchange.vista.FragmentCollezione;
import it.unibas.nft_exchange.vista.FragmentCreaNFT;
import it.unibas.nft_exchange.vista.FragmentInviaETH;
import it.unibas.nft_exchange.vista.FragmentInviaNFT;

public class ActivityPrincipale extends AppCompatActivity {

    public static final String TAG = ActivityPrincipale.class.getSimpleName();
    private BottomNavigationView bottomNavigationView;
    private FragmentCollezione fragmentCollezione = new FragmentCollezione();
    private FragmentCreaNFT fragmentCreaNFT = new FragmentCreaNFT();
    private FragmentInviaNFT fragmentInviaNFT = new FragmentInviaNFT();
    private FragmentInviaETH fragmentInviaETH = new FragmentInviaETH();
    private  int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCollezione).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCollezione).commit();
                        getSupportActionBar().setTitle("Collezione");
                        Applicazione.getInstance().getModello().putBean(Costanti.BITMAP_IMMAGINE_SELEZIONATA, null);
                        return true;
                    case R.id.page_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCreaNFT).commit();
                        getSupportActionBar().setTitle("Crea NFT");
                        return true;
                    case R.id.page_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInviaNFT).commit();
                        getSupportActionBar().setTitle("Invia NFT");
                        Applicazione.getInstance().getModello().putBean(Costanti.BITMAP_IMMAGINE_SELEZIONATA, null);
                        return true;
                    case R.id.page_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInviaETH).commit();
                        getSupportActionBar().setTitle("Invia ETH");
                        Applicazione.getInstance().getModello().putBean(Costanti.BITMAP_IMMAGINE_SELEZIONATA, null);
                        return true;
                }
                return false;
            }
        });
    }

    public void aggiornaFragment(Fragment fragment){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSupportFragmentManager().beginTransaction().detach(fragment).commitNow();
            getSupportFragmentManager().beginTransaction().attach(fragment).commitNow();
        } else {
            getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
        }
    }

    public FragmentInviaETH getFragmentInviaETH(){
        return (FragmentInviaETH) getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public FragmentCollezione getFragmentCollezione(){
        return (FragmentCollezione) getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public FragmentCreaNFT getFragmentCreaNFT(){
        return (FragmentCreaNFT) getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public void mostraMessaggioToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    public void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        imageSelectionActivity.launch(i);
    }

    ActivityResultLauncher<Intent> imageSelectionActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Log.d(TAG, "URI dell'immagine selezionata: " + selectedImageUri);
                        Applicazione.getInstance().getModello().putBean(Costanti.URI_IMMAGINE_SELEZIONATA, selectedImageUri);
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            Log.d(TAG, "BitMap dell'immagine selezionata: " + selectedImageBitmap);
                            Applicazione.getInstance().getModello().putBean(Costanti.BITMAP_IMMAGINE_SELEZIONATA, selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            this.mostraMessaggioToast("Impossibile caricare l'immagine");
                        }
                        this.fragmentCreaNFT.getBoxImmagineCreaNFT().setImageBitmap(selectedImageBitmap);
                    }
                }
            }
    );

}