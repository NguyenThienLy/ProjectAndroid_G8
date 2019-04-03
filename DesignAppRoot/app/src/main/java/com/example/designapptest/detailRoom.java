package com.example.designapptest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class detailRoom extends Activity {
    GridView grVUtilitiyRoomDetail;
    GridView grVSameCriteriaRoomDetail;

    ListView lstVCommentRoomDetail;

    ArrayList<utilityRoomModel> lstUtilityRoom;
    ArrayList<roomModel> lstSameCriteriaRoom;
    ArrayList<commentRoomModel> lstCommentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rom_detail_view);

        initControl();

        initDataUtilitiy();

        initDataSameCriteria();

        initDataComment();

        adapter();
    }

    private void initControl() {
        grVUtilitiyRoomDetail = (GridView) findViewById(R.id.grV_utiliti_rom_detail);
        grVSameCriteriaRoomDetail = (GridView) findViewById(R.id.grV_sameCriteria_rom_detail);
        lstVCommentRoomDetail = (ListView) findViewById(R.id.lst_comment_rom_detail);
    }

    private void initDataUtilitiy() {
        lstUtilityRoom = new ArrayList<>();

        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_aircondition_100, "Máy lạnh"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_wc_100, "WC riêng"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_motobike_100, "Chỗ để xe"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_wifi_100, "Wifi"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_clock_100, "Tự do"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_keyhome_100, "Chủ riêng"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_fridge_100, "Tủ lạnh"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_washmachine_100, "Máy giặt"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_security_100, "An ninh"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_bed_100, "Giường"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_cupboard_100, "Tủ để đồ"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_window_100, "Cửa số"));
        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_waterheater_100, "Máy nước nóng"));
    }

    private void initDataSameCriteria() {
        lstSameCriteriaRoom = new ArrayList<>();

        lstSameCriteriaRoom.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 8, 256, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 6, 18, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 5, 365, "CHUNG CƯ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 4, 256, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 6, 28, "KÍ TÚC XÁ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 7, 147, "PHÒNG TRỌ"));
    }

    private void initDataComment() {
        lstCommentRoom = new ArrayList<>();

        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_01_100,"Nguyễn Thiên Lý",5,7, "Phòng rất tốt", "Mình tới tìm phòng này thì thấy phòng đăng khá đúng với thông tin trên app, khá hài lòng"));
        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_02_100,"Trần Khánh Linh",7,7, "Cảm thấy rất tuyệt vời", "Rất hài lòng...."));
        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_03_100,"Trần Nhất Sinh",12,7, "Chỗ ở không đúng mô tả", "Chỗ này mình tới rồi mọi người không đúng như mô tả đâu mn ơi"));
        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_04_100,"Lê Tường Qui",3,7, "Chỗ ở tệ", "Chỗ ở quá chật thiếu nhiều các tiện ít mà giá còn đắt hơn chỗ khác nữa chứ!!"));
    }

    private void adapter() {
        utilityRoomAdapter adapterUtilityRoom = new utilityRoomAdapter(this, R.layout.utility_element_grid_rom_detail_view, lstUtilityRoom);
        roomAdapter adapterRoom = new roomAdapter(this, R.layout.rom_element_grid_view, lstSameCriteriaRoom);
        commentRoomAdapter adapterComment = new commentRoomAdapter(this, R.layout.comment_element_grid_room_detail_view, lstCommentRoom);

        grVUtilitiyRoomDetail.setAdapter(adapterUtilityRoom);
        grVSameCriteriaRoomDetail.setAdapter(adapterRoom);
        lstVCommentRoomDetail.setAdapter(adapterComment);

        grVUtilitiyRoomDetail.setClickable(false);
        grVSameCriteriaRoomDetail.setClickable(false);
        lstVCommentRoomDetail.setClickable(false);
    }
}
