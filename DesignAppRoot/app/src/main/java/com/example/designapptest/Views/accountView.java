package com.example.designapptest.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.designapptest.R;

public class accountView extends AppCompatActivity {

    private Button btnEditAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);

        btnEditAccount = (Button)findViewById(R.id.btn_edit_account);
        EditInformation();
    }

    private void EditInformation(){
        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(accountView.this,personalPage.class);
                startActivity(intent);

            }
        });

    }
}
