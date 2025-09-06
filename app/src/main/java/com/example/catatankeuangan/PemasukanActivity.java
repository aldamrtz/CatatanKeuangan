package com.example.catatankeuangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PemasukanActivity extends AppCompatActivity {

    private EditText amountEditText, descriptionEditText, dateEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        databaseHelper = new DatabaseHelper(this);

        amountEditText = findViewById(R.id.amountEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePemasukan();
            }
        });
    }

    private void savePemasukan() {
        String amount = amountEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();

        if (amount.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Transaction transaction = new Transaction("Pemasukan", amount, description, date);
        long id = databaseHelper.addTransaction(transaction);

        if (id != -1) {
            Toast.makeText(this, "Pengeluaran added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PemasukanActivity.this, HomeActivity.class);
            intent.putExtra("update_fragments", true);
            setResult(RESULT_OK);
            finish(); // This will call onResume in HomeActivity
        } else {
            Toast.makeText(this, "Failed to add pemasukan", Toast.LENGTH_SHORT).show();
        }
    }
}