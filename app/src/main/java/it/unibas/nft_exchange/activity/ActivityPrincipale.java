package it.unibas.nft_exchange.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

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
                        return true;
                    case R.id.page_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCreaNFT).commit();
                        return true;
                    case R.id.page_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInviaNFT).commit();
                        return true;
                    case R.id.page_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentInviaETH).commit();
                        return true;
                }
                return false;
            }
        });
    }

}