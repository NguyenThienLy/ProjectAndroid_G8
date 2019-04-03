package com.example.designapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class postRoomStep4 extends Activity {
    Button btnStep4PostRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_room_step_4_view);

        initControl();

        switchStep();
    }

    private void initControl() {
        btnStep4PostRoom = (Button) findViewById(R.id.btn_nextStep4_post_room);
    }

    private  void switchStep() {
        btnStep4PostRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
