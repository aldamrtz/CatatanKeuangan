package com.example.catatankeuangan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private PemasukanFragment pemasukanFragment;
    private PengeluaranFragment pengeluaranFragment;
    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = new DatabaseHelper(this);

        pemasukanFragment = new PemasukanFragment();
        pengeluaranFragment = new PengeluaranFragment();
        tabAdapter = new TabAdapter(getSupportFragmentManager());

        tabAdapter.addFragment(pemasukanFragment, "Pemasukan");
        tabAdapter.addFragment(pengeluaranFragment, "Pengeluaran");

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = tabLayout.getSelectedTabPosition();
                Intent intent;
                if (position == 0) {
                    intent = new Intent(HomeActivity.this, PemasukanActivity.class);
                } else {
                    intent = new Intent(HomeActivity.this, PengeluaranActivity.class);
                }
                startActivity(intent);
            }
        });

        // Handle intent from PemasukanActivity or PengeluaranActivity
        Intent intent = getIntent();
        if (intent.getBooleanExtra("update_fragments", false)) {
            updateFragments();
        }

        // Initial update
        updateFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update both fragments when returning to HomeActivity
        updateFragments();
    }

    private void updateFragments() {
        List<Transaction> pemasukanList = databaseHelper.getAllTransactions("Pemasukan");
        List<Transaction> pengeluaranList = databaseHelper.getAllTransactions("Pengeluaran");

        if (pemasukanFragment != null) {
            pemasukanFragment.updateTransactionList(pemasukanList);
        }

        if (pengeluaranFragment != null) {
            pengeluaranFragment.updateTransactionList(pengeluaranList);
        }
    }
}