package com.example.catatankeuangan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private Context context;
    private List<Transaction> transactions;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Transaction transaction = transactions.get(position);
        holder.typeTextView.setText(transaction.getType());
        holder.amountTextView.setText(transaction.getAmount());
        holder.descriptionTextView.setText(transaction.getDescription());
        holder.dateTextView.setText(transaction.getDate());

        // Handle edit button click
        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = transactions.get(position);
                Intent intent = new Intent(context, EditTransactionActivity.class);
                intent.putExtra("transaction_id", String.valueOf(transaction.getId())); // Pastikan transaction.getId() mengembalikan String
                context.startActivity(intent);
            }
        });

        // Handle delete button click
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transaction transaction = transactions.get(position);
                // Example: Delete transaction from database
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteTransaction(transaction.getId());
                // Remove transaction from list and notify adapter
                transactions.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, transactions.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged(); // Penting untuk memastikan RecyclerView di-update
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        public TextView typeTextView, amountTextView, descriptionTextView, dateTextView;
        public ImageView editImageView, deleteImageView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            editImageView = itemView.findViewById(R.id.editImageView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
        }
    }
}