package com.example.bluebird;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<roomModel> arrRoomList = new ArrayList<>();
    private ListView grvListRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        grvListRoom = (ListView) this.findViewById(R.id.grv_ListRoom);

        romAdapter adapter = new romAdapter(this,R.layout.target,arrRoomList);

        grvListRoom.setAdapter(adapter);
    }

    private void initData() {
        arrRoomList.add(new roomModel(R.drawable.room_01,"KÍ TÚC XÁ", 2,
                3, "Phường Mỹ Bình, TP. Long Xuyên, tỉnh An Giang",
                5000000, 60,
                "Số 16C Tôn Đức Thắng, phường Mỹ Bình, TP. Long Xuyên, tỉnh An Giang"));

        arrRoomList.add(new roomModel(R.drawable.room_02,"KHU CHUNG CƯ", 2,
                315, "Phường Phước Trung, TP. Bà Rịa, tỉnh Bà Rịa – Vũng Tàu",
                6500000, 35,
                "Số 1 Phạm Văn Đồng, phường Phước Trung, TP. Bà Rịa, tỉnh Bà Rịa – Vũng Tàu"));

        arrRoomList.add(new roomModel(R.drawable.room_03,"PHÒNG TRỌ", 6,
                269, "Phường 3, TP.Bạc Liêu, tỉnh Bạc Liêu",
                3200000, 90,
                "Số 04 đường Phan Đình Phùng, phường 3, TP.Bạc Liêu, tỉnh Bạc Liêu"));

        arrRoomList.add(new roomModel(R.drawable.room_04,"PHÒNG TRỌ", 6,
                269, "Phường 3, TP.Bạc Liêu, tỉnh Bạc Liêu",
                3200000, 90,
                "Số 04 đường Phan Đình Phùng, phường 3, TP.Bạc Liêu, tỉnh Bạc Liêu"));

        arrRoomList.add(new roomModel(R.drawable.room_05,"KÍ TÚC XÁ", 8,
                125, "TP. Bắc Giang, tỉnh Bắc Giang",
                2000000, 33,
                "Số 82  đường Hùng Vương, TP. Bắc Giang, tỉnh Bắc Giang"));

        arrRoomList.add(new roomModel(R.drawable.room_06,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_07,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_08,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_09,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_10,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_11,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_12,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_13,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));

        arrRoomList.add(new roomModel(R.drawable.room_14,"PHÒNG TRỌ", 8,
                250, "Phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn",
                3000000, 44,
                "Tổ 1A, phường Phùng Chí Kiên, TX.Bắc Kạn, tỉnh Bắc Kạn"));


    }


}
