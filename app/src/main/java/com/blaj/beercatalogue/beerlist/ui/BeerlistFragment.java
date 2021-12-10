package com.blaj.beercatalogue.beerlist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blaj.beercatalogue.accounts.ui.UserActivity;
import com.blaj.beercatalogue.beerlist.service.BeerListAdapter;
import com.blaj.beercatalogue.databinding.FragmentBeerlistBinding;

public class BeerlistFragment extends Fragment {
    private FragmentBeerlistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBeerlistBinding.inflate(inflater, container, false);

        RecyclerView beerListUI = binding.beerList;

        BeerListAdapter beerListAdapter = UserActivity.beerListAdapter;

        beerListUI.setAdapter(beerListAdapter);
        beerListUI.setLayoutManager(new LinearLayoutManager(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}