package com.example.catatankeuangan;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PengeluaranFragment extends Fragment {

    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> transactionList;
    private DatabaseHelper databaseHelper;
    private TextView totalPengeluaranTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengeluaran, container, false);

        databaseHelper = new DatabaseHelper(getActivity());
        transactionList = databaseHelper.getAllTransactions("Pengeluaran");

        recyclerView = view.findViewById(R.id.recyclerViewPengeluaran);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionAdapter = new TransactionAdapter(getActivity(), transactionList);
        recyclerView.setAdapter(transactionAdapter);

        totalPengeluaranTextView = view.findViewById(R.id.totalPengeluaranTextView);

        // Hitung total pengeluaran bulan ini
        int totalPengeluaran = hitungTotalPengeluaranBulanIni();
        // Format angka dengan tanda titik sebagai pemisah ribuan
        String formattedTotalPengeluaran = formatRupiah(totalPengeluaran);
        // Set text dengan jumlah yang diformat dan gaya yang berbeda
        setFormattedText(formattedTotalPengeluaran);

        return view;
    }

    private int hitungTotalPengeluaranBulanIni() {
        int total = 0;
        for (Transaction transaction : transactionList) {
            // Misalkan getAmount() mengembalikan string seperti "Rp. 1.000.000"
            String amountString = transaction.getAmount().replaceAll("[^\\d]", ""); // Hapus karakter non-digit
            try {
                total += Integer.parseInt(amountString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle jika terjadi error parsing
            }
        }
        return total;
    }

    private String formatRupiah(int amount) {
        // Buat objek NumberFormat untuk format mata uang Indonesia
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        // Format angka
        return format.format(amount).replace("Rp", "Rp.");
    }

    private void setFormattedText(String formattedAmount) {
        String text = "Total pengeluaran: " + formattedAmount;
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        // Find the start and end indices of the amount in the string
        int start = text.indexOf(formattedAmount);
        int end = text.length();

        // Apply bold style to the amount
        spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        totalPengeluaranTextView.setText(spannable);
    }

    public void updateTransactionList(List<Transaction> transactions) {
        transactionList = transactions;
        if (transactionAdapter != null) {
            transactionAdapter.setTransactions(transactionList);
            transactionAdapter.notifyDataSetChanged();

            // Setel ulang total pengeluaran setelah daftar transaksi diperbarui
            int totalPengeluaran = hitungTotalPengeluaranBulanIni();
            // Format angka dengan tanda titik sebagai pemisah ribuan
            String formattedTotalPengeluaran = formatRupiah(totalPengeluaran);
            // Set text dengan jumlah yang diformat dan gaya yang berbeda
            setFormattedText(formattedTotalPengeluaran);
        }
    }
}