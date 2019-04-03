package com.example.designapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class postRoomStep1 extends Activity{
    Button btnStep1PostRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_room_step_1_view);

        initControl();

        nextStep();
    }

    private void initControl() {
        btnStep1PostRoom = (Button) findViewById(R.id.btn_nextStep1_post_room);
    }

    private  void nextStep() {
        btnStep1PostRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), postRoomStep2.class);
                startActivity(intent);
            }
        });
    }
}
