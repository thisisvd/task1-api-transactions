package com.example.task1_api_transactions.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task1_api_transactions.core.Utils;
import com.example.task1_api_transactions.databinding.TransactionItemBinding;
import com.example.task1_api_transactions.domain.Transactions;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {

    private final AsyncListDiffer<Transactions> differ;

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {
        private final TransactionItemBinding binding;

        public TransactionsViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public TransactionsAdapter() {
        DiffUtil.ItemCallback<Transactions> differCallback = new DiffUtil.ItemCallback<Transactions>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transactions oldItem, @NonNull Transactions newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull Transactions oldItem, @NonNull Transactions newItem) {
                return oldItem.equals(newItem);
            }
        };
        differ = new AsyncListDiffer<>(this, differCallback);
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TransactionItemBinding binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Transactions transaction = differ.getCurrentList().get(position);
        holder.binding.transactionId.setText(String.valueOf(transaction.getId()));
        holder.binding.description.setText(transaction.getDescription());
        holder.binding.amount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.category.setText(transaction.getCategory());
        holder.binding.date.setText(Utils.getDataFormat(transaction.getDate()));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<Transactions> newList) {
        differ.submitList(newList);
    }
}