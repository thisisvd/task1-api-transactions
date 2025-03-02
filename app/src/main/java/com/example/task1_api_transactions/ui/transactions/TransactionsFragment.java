package com.example.task1_api_transactions.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.task1_api_transactions.core.Resource;
import com.example.task1_api_transactions.databinding.FragmentTransactionsBinding;
import com.example.task1_api_transactions.ui.adapter.TransactionsAdapter;
import com.example.task1_api_transactions.ui.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class TransactionsFragment extends Fragment {

    // view binding
    private FragmentTransactionsBinding binding;

    // view model
    private MainViewModel mainViewModel;

    // transaction adapter
    private TransactionsAdapter transactionsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init view model
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // call transactions api
        mainViewModel.getTransactions();

        // logout button click listener
        binding.logout.setOnClickListener(v -> mainViewModel.logout());

        // initialize view models
        initViewModels();

        // initialize recycler view
        initRecyclerView();
    }

    // init view models
    private void initViewModels() {
        mainViewModel.logout.observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                if (resource instanceof Resource.Loading) {
                    binding.progressCircular.setVisibility(View.VISIBLE);
                    Timber.d("Logout Loading!");
                } else if (resource instanceof Resource.Success) {
                    binding.progressCircular.setVisibility(View.GONE);
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                } else if (resource instanceof Resource.Error) {
                    binding.progressCircular.setVisibility(View.GONE);
                    Timber.d("Login Error : %s", resource.getMessage());
                    Snackbar.make(binding.getRoot(), "Error occurred while logout!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        mainViewModel.transactions.observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                if (resource instanceof Resource.Loading) {
                    binding.progressCircular.setVisibility(View.VISIBLE);
                    Timber.d("Transaction Loading!");
                } else if (resource instanceof Resource.Success) {
                    binding.progressCircular.setVisibility(View.GONE);
                    if (!resource.getData().isEmpty()) {
                        transactionsAdapter.submitList(resource.getData());
                    }
                } else if (resource instanceof Resource.Error) {
                    binding.progressCircular.setVisibility(View.GONE);
                    Timber.d("Transaction Error : %s", resource.getMessage());
                    Snackbar.make(binding.getRoot(), "Error occurred while logout!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    // init recycler view
    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionsAdapter = new TransactionsAdapter();
        binding.recyclerView.setAdapter(transactionsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}