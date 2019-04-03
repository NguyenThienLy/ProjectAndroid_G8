package com.example.designapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class postRoomStep2 extends Activity {
    Button btnStep2PostRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_room_step_2_view);

        initControl();

        nextStep();
    }

    private void initControl() {
        btnStep2PostRoom = (Button) findViewById(R.id.btn_nextStep2_post_room);
    }

    private  void nextStep() {
        btnStep2PostRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), postRoomStep3.class);
                startActivity(intent);
            }
        });
    }
}