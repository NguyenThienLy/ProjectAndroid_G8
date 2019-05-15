package com.example.designapptest.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.designapptest.R;

public class accountView extends AppCompatActivity implements View.OnClickListener {

    private Button btnEditAccount;
    private Button btnMyRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_view);

        initControl();
    }

    private void initControl(){
        btnEditAccount = (Button)findViewById(R.id.btn_edit_account);
        btnMyRoom = findViewById(R.id.btn_my_Room);

        btnEditAccount.setOnClickListener(this);
        btnMyRoom.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_edit_account:
                Intent intent = new Intent(accountView.this,personalPage.class);
                startActivity(intent);
                break;
            case R.id.btn_my_Room:
                Intent intent1 = new Intent(accountView.this,roomManagementModel.class);
                startActivity(intent1);
                break;
        }
    }
}
