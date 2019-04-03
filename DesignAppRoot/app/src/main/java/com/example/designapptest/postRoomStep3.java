package com.example.designapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class postRoomStep3 extends Activity {
    Button btnStep3PostRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_room_step_3_view);

        initControl();

        nextStep();
    }

    private void initControl() {
        btnStep3PostRoom = (Button) findViewById(R.id.btn_nextStep3_post_room);
    }

    private  void nextStep() {
        btnStep3PostRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), postRoomStep4.class);
                startActivity(intent);
            }
        });
    }
}