package com.blaj.beercatalogue.accounts.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.blaj.beercatalogue.R;
import com.blaj.beercatalogue.accounts.repository.UserRepository;
import com.blaj.beercatalogue.beerlist.repository.BeerRepository;
import com.blaj.beercatalogue.beerlist.service.BeerListAdapter;
import com.blaj.beercatalogue.databinding.ActivityUserBinding;
import com.blaj.beercatalogue.reviews.repository.ReviewRepository;
import com.google.android.material.navigation.NavigationView;

public class UserActivity extends AppCompatActivity {
    public static final String DATABASE_URL = "https://beercatalogue-f128c-default-rtdb.europe-west1.firebasedatabase.app";

    private AppBarConfiguration mAppBarConfiguration;
    public static BeerListAdapter beerListAdapter = new BeerListAdapter(BeerRepository.getInstance().getBeerList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUserBinding binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarUser.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_beerlist, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        fetchDatabase();
    }

    private void fetchDatabase() {
        UserRepository.getInstance();

        BeerRepository.getInstance();

        ReviewRepository.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user, menu);

        MenuItem menuItem = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                beerListAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_user);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}