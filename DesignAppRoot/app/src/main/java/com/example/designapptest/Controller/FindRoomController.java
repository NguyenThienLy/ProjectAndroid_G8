package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerFindRoom;
import com.example.designapptest.Controller.Interfaces.IFindRoomModel;
import com.example.designapptest.Model.CommentModel;
import com.example.designapptest.Model.FindRoomFilterModel;
import com.example.designapptest.Model.FindRoomModel;
import com.example.designapptest.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FindRoomController {
    Context context;
    FindRoomModel findRoomModel;

    public FindRoomController(Context context) {
        this.context = context;
        findRoomModel = new FindRoomModel();
    }

    public void ListFindRoom(RecyclerView recyclerFindRoom, ProgressBar progressBarFindRoom,
                             TextView txtResultReturn, LinearLayout lLayHaveResultReturn) {
        final List<FindRoomModel> findRoomModelist = new ArrayList<>();

        //Hiện progress bar
        progressBarFindRoom.setVisibility(View.VISIBLE);
        // Ẩn thi kết quả trả vể.
        lLayHaveResultReturn.setVisibility(View.GONE);

        //Tạo layout cho danh sách tìm phòng trọ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerFindRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
         AdapterRecyclerFindRoom adapterRecyclerFindRoom = new AdapterRecyclerFindRoom(context, findRoomModelist, R.layout.element_list_find_room_view);
        //Cài adapter cho recycle
        recyclerFindRoom.setAdapter(adapterRecyclerFindRoom);
        //End tạo layout cho danh sách tìm phòng trọ.

        //Tạo interface để truyền dữ liệu lên từ model
        IFindRoomModel iFindRoomModel = new IFindRoomModel() {
            @Override
            public void getListFindRoom(FindRoomModel valueRoom) {

                //Thêm vào trong danh sách trọ
                findRoomModelist.add(valueRoom);

                sortListComments(findRoomModelist);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerFindRoom.notifyDataSetChanged();
                progressBarFindRoom.setVisibility(View.GONE);

                // Hiển thị kết quả trả về.
                lLayHaveResultReturn.setVisibility(View.VISIBLE);
                txtResultReturn.setText(String.valueOf(findRoomModelist.size()));
            }

            @Override
            public void addSuccessNotify() {

            }

            @Override
            public void getSuccessNotify() {
                // Tải xong dữ liệu
                progressBarFindRoom.setVisibility(View.GONE);

                // Hiển thị kết quả trả về.
                lLayHaveResultReturn.setVisibility(View.VISIBLE);
                txtResultReturn.setText(String.valueOf(findRoomModelist.size()));
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        findRoomModel.ListFindRoom(iFindRoomModel);
    }

    public void ListFindRoomFilter(RecyclerView recyclerFindRoom, FindRoomFilterModel findRoomFilterModel,
                                   ProgressBar progressBarFindRoom, TextView txtResultReturn, LinearLayout lLayHaveResultReturn) {

        final List<FindRoomModel> findRoomModelistFilter = new ArrayList<>();

        //Hiện progress bar
        progressBarFindRoom.setVisibility(View.VISIBLE);
        // Ẩn thi kết quả trả vể.
        lLayHaveResultReturn.setVisibility(View.GONE);

        //Tạo layout cho danh sách tìm phòng trọ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        //((LinearLayoutManager) layoutManager).setReverseLayout(true);
       // ((LinearLayoutManager) layoutManager).setStackFromEnd(false);
        recyclerFindRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerFindRoom adapterRecyclerFindRoomFilter = new AdapterRecyclerFindRoom(context, findRoomModelistFilter, R.layout.element_list_find_room_view);


        //adapterRecyclerFindRoomFilter.clearApplications();
        //Cài adapter cho recycle
        recyclerFindRoom.setAdapter(adapterRecyclerFindRoomFilter);
        //End tạo layout cho danh sách tìm phòng trọ.

        //Tạo interface để truyền dữ liệu lên từ model
        IFindRoomModel iFindRoomModel = new IFindRoomModel() {
            @Override
            public void getListFindRoom(FindRoomModel valueRoom) {
                boolean flagCondition = true;

                // Xem thử người dùng có đặt điều kiện cho tiện nghi không.
                if (findRoomFilterModel.getLstConvenients().size() > 0) {
                    // Nếu trong vị trí cần tìm không khớp thì bỏ qua giá trí này.
                    if (!checkTwoList(findRoomFilterModel.getLstConvenients(), valueRoom.getConvenients()))
                        flagCondition = false;
                }

                // Xem thử người dùng có đặt điều kiện cho vị trí cần tìm không.
                if (findRoomFilterModel.getLstLocationSearchs().size() > 0) {
                    // Nếu trong vị trí cần tìm không khớp thì bỏ qua giá trí này.
                    if (!checkTwoList(findRoomFilterModel.getLstLocationSearchs(), valueRoom.getLocation()))
                        flagCondition = false;
                }

                // Xem thử người dùng có đặt điều kiện cho giới tính không.
                if (findRoomFilterModel.getGender() != 2) {
                    // Cần tìm nam là nam mà ra nữ hoặc cần tìm nữa hoặc ra nam.
                    int filterGender = findRoomFilterModel.getGender(); // Giới tính cần tìm
                    boolean valueGender = valueRoom.getFindRoomOwner().isGender(); // Giới tính của data
                    if (filterGender == 0 && valueGender == false
                            || filterGender == 1 && valueGender == true)
                        flagCondition = false;
                }

                // Kiểm tra điều kiện khoản giá.
                // Khoảng giá nhỏ nhất cần tìm lớn hớn khoản giá nhỏ nhất hoặc
                // Khoảng lớn lớn nhất cần tìm nhỏ hớn khoản giá lớn nhất
                double minFilter = findRoomFilterModel.getMinPrice();
                double minValue = valueRoom.getMinPrice();

                double maxFilter = findRoomFilterModel.getMaxPrice();
                double maxValue = valueRoom.getMaxPrice();

                if (minFilter > minValue || maxFilter < maxValue)
                    flagCondition = false;

                // Nếu bộ lọc thỏa mãn thì thêm vào recycle
                if (flagCondition == true) {
                    // Tăng lên số lượng kết quả trả về.

                    //Thêm vào trong danh sách trọ
                    findRoomModelistFilter.add(valueRoom);

                    sortListComments(findRoomModelistFilter);

                    //Thông báo là đã có thêm dữ liệu
                    adapterRecyclerFindRoomFilter.notifyDataSetChanged();
                    progressBarFindRoom.setVisibility(View.GONE);

                    // Hiện thi kết quả trả vể.
                    lLayHaveResultReturn.setVisibility(View.VISIBLE);
                    txtResultReturn.setText(String.valueOf(findRoomModelistFilter.size()));
                }
            }

            @Override
            public void addSuccessNotify() {

            }

            @Override
            public void getSuccessNotify() {
                // Tải xong dữ liệu
                progressBarFindRoom.setVisibility(View.GONE);

                // Hiện thi kết quả trả vể.
                lLayHaveResultReturn.setVisibility(View.VISIBLE);
                txtResultReturn.setText(String.valueOf(findRoomModelistFilter.size()));
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        findRoomModel.ListFindRoom(iFindRoomModel);
    }

    // Kiểm tra xem hai lst có phần tử nào giống nhau ko
    private boolean checkTwoList(List<String> lst1, List<String> lst2) {
        int i, j;

        // Nếu lst data không có giá trị
        if (lst2 == null) {
            return false;
        }
        // Nếu hai list có số lượng phần từ bằng nhau;
        else if (lst1.size() == lst2.size()) {
            lst1 = new ArrayList<String>(lst1);
            lst2 = new ArrayList<String>(lst2);

            return lst1.equals(lst2);
        } else if (lst1.size() > lst2.size()) {
            // Nếu lst cần tìm có số lượng phần từ lớn hơn.

            return false;
        } else {
            // Nếu lst cần tìm có số lượng phần từ lớn hơn.
            for (i = 0; i < lst1.size(); i++) {
                for (j = 0; j < lst2.size(); j++) {
                    if (lst1.get(i).equals(lst2.get(j))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void sortListComments(List<FindRoomModel> listComments) {
        Collections.sort(listComments, new Comparator<FindRoomModel>() {
            @Override
            public int compare(FindRoomModel findRoomModel1, FindRoomModel findRoomModel2) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = df.parse(findRoomModel1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date date2 = null;
                try {
                    date2 = df.parse(findRoomModel2.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date2.compareTo(date1);
            }
        });
    }
}
