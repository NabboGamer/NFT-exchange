package it.unibas.nft_exchange.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
                        return true;
                    case R.id.page_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCreaNFT).commit();
                        getSupportActionBar().setTitle("Crea NFT");
                        return true;
                    case R.id.page_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInviaNFT).commit();
                        getSupportActionBar().setTitle("Invia NFT");
                        return true;
                    case R.id.page_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInviaETH).commit();
                        getSupportActionBar().setTitle("Invia ETH");
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

    public void mostraMessaggioToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

}