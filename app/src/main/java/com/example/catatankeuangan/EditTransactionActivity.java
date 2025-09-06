package com.example.catatankeuangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditTransactionActivity extends AppCompatActivity {

    private EditText editTextAmount, editTextDescription, editTextDate;
    private Button buttonSave;
    private String transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        buttonSave = findViewById(R.id.buttonSave);

        // Retrieve transaction ID from intent extras
        Intent intent = getIntent();
        if (intent != null) {
            transactionId = intent.getStringExtra("transaction_id");
            loadTransactionDetails(transactionId);
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save edited transaction details and return to previous activity
                saveEditedTransaction();
            }
        });
    }

    private void loadTransactionDetails(String transactionId) {
        // Retrieve transaction details from database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Transaction transaction = databaseHelper.getTransaction(transactionId);
        if (transaction != null) {
            editTextAmount.setText(transaction.getAmount());
            editTextDescription.setText(transaction.getDescription());
            editTextDate.setText(transaction.getDate());
        }
    }

    private Transaction getTransactionDetails(String transactionId) {
        // Retrieve transaction details from database or storage
        // Replace with your actual logic to fetch transaction details
        return null; // Dummy return, replace with actual implementation
    }

    private void saveEditedTransaction() {
        String amount = editTextAmount.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = editTextDate.getText().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.updateTransaction(transactionId, amount, description, date);

        // Setelah menyimpan, kembali ke activity sebelumnya
        finish();
    }
}