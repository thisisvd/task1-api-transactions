package com.example.task1_api_transactions.ui.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.task1_api_transactions.R;
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

    // on back pressed counter
    private int onBackPressCount = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init view model
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // init menu provider
        addMenuProvider();

        // call transactions api
        mainViewModel.getTransactions();

        // initialize view models
        initViewModels();

        // initialize recycler view
        initRecyclerView();

        // handling on back pressed
        handleOnBackPressed();
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
                    Snackbar.make(binding.getRoot(), resource.getMessage(), Snackbar.LENGTH_SHORT).show();
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

    // handle on back pressed
    private void handleOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressCount++;
                if (onBackPressCount == 1) {
                    Snackbar.make(binding.getRoot(), "Press back again to close the app.", Snackbar.LENGTH_SHORT).show();
                } else {
                    requireActivity().finish();
                }
            }
        });
    }

    private void addMenuProvider() {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu, menu);

                // Get the search item and its SearchView
                MenuItem searchItem = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) searchItem.getActionView();

                // Handle search query input
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mainViewModel.searchTransactions(newText);
                        return true;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_logout) {
                    mainViewModel.logout();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}