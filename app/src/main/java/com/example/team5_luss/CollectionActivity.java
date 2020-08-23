package com.example.team5_luss;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class CollectionActivity extends AppCompatActivity implements CollectionPointList.SingleChoiceListner {
    TextView currentCP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_points);

        currentCP = findViewById(R.id.current_cp_description);

        Button updateCPBtn = findViewById(R.id.updateBtn);
        updateCPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment collectionPointList = new CollectionPointList();
                collectionPointList.setCancelable(false);
                collectionPointList.show(getSupportFragmentManager(), "Collection Points List");
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        currentCP.setText(list[position]);
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}
