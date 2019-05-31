package com.example.designapptest.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.designapptest.R;
import com.google.firebase.auth.FirebaseAuth;

public class accountView extends Fragment implements View.OnClickListener {

    private Button btnEditAccount;
    private Button btnMyRoom;
    private Button btnMyFavoriteRoom;
    private Button btnMyFindRoom;
    private Button btnLogout;

    FirebaseAuth firebaseAuth;

    //View để liên kết
    View layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.account_view, container, false);

        initControl();

        return layout;
    }

    //Khởi tạo control
    private void initControl() {
        btnEditAccount = (Button) layout.findViewById(R.id.btn_edit_account);
        btnMyRoom = layout.findViewById(R.id.btn_my_Room);

        btnEditAccount.setOnClickListener(this);
        btnMyRoom.setOnClickListener(this);

        btnMyFavoriteRoom = layout.findViewById(R.id.btn_my_favorite_room);
        btnMyFavoriteRoom.setOnClickListener(this);

        btnMyFindRoom = layout.findViewById(R.id.btn_my_find_room);
        btnMyFindRoom.setOnClickListener(this);

        btnLogout = layout.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_edit_account:
                Intent intent = new Intent(getContext(), personalPage.class);
                startActivity(intent);
                break;
            case R.id.btn_my_Room:
                Intent intent1 = new Intent(getContext(), roomManagementModel.class);
                startActivity(intent1);
                break;

            case R.id.btn_my_favorite_room:
                Intent intentFavoriteRooms = new Intent(getContext(), favoriteRoomsView.class);
                startActivity(intentFavoriteRooms);
                break;

            case R.id.btn_my_find_room:
                Intent intentMyFindRooms = new Intent(getContext(), FindRoomMine.class);
                startActivity(intentMyFindRooms);
                break;

            case R.id.btn_logout:
                //Khởi tạo firebaseAuth
                firebaseAuth = FirebaseAuth.getInstance();
                //Text Đăng xuất
                firebaseAuth.signOut();
                getActivity().finish();

        }
    }
}
