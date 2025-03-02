package com.example.task1_api_transactions.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.task1_api_transactions.R;
import com.example.task1_api_transactions.core.Resource;
import com.example.task1_api_transactions.databinding.FragmentLoginBinding;
import com.example.task1_api_transactions.domain.User;
import com.example.task1_api_transactions.ui.viewmodel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    // view binding
    private FragmentLoginBinding binding;

    // view model
    private MainViewModel mainViewModel;

    // biometric vars
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        executor = ContextCompat.getMainExecutor(requireContext());

        // setup biometric prompt
        setupBiometricPrompt();

        // login click listener
        binding.login.setOnClickListener(v -> mainViewModel.loginUser(new User("admin", "A7ge#hu&dt(wer")));

        // biometric listener
        binding.biometric.setOnClickListener(v -> biometricPrompt.authenticate(promptInfo));

        // initialize view models
        initViewModels();
    }

    // view model observers
    private void initViewModels() {
        mainViewModel.login.observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                if (resource instanceof Resource.Loading) {
                    binding.progressCircular.setVisibility(View.VISIBLE);
                    Timber.d("Login Loading!");
                } else if (resource instanceof Resource.Success) {
                    binding.progressCircular.setVisibility(View.GONE);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_transactionsFragment);
                    mainViewModel.clearLoginState();
                } else if (resource instanceof Resource.Error) {
                    binding.progressCircular.setVisibility(View.GONE);
                    Timber.d("Login Error : %s", resource.getMessage());
                    Snackbar.make(binding.getRoot(), "Error occurred while login!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    // biometric prompt initialization
    private void setupBiometricPrompt() {

        biometricPrompt = new BiometricPrompt(requireActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Timber.d("Authentication Failed!: %s", errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_transactionsFragment);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Timber.d("Authentication Failed!: No biometric found!");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for this app").setSubtitle("Log in using your biometric credential").setNegativeButtonText("Use password").build();

        if (mainViewModel.hasToken()) {
            binding.biometricLayout.setVisibility(View.VISIBLE);
            new Handler(Looper.getMainLooper()).post(() -> biometricPrompt.authenticate(promptInfo));
        } else {
            binding.biometricLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}