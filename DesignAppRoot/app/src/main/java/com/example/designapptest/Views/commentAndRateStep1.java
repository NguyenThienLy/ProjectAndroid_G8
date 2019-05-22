package com.example.designapptest.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.Adapters.AdapterGridRateStar;
import com.example.designapptest.Controller.CommentController;
import com.example.designapptest.Controller.UserController;
import com.example.designapptest.Model.CommentModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.commentAndRateMain;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class commentAndRateStep1 extends Fragment {
    int scores;

    GridView grvStar;
    AdapterGridRateStar adapterGridRateStar;

    ArrayList<Integer> lstStar;

    TextView txtVRoomName;
    TextView txtVRoomAddress;
    TextView txtVScores;
    EditText txtTitle;
    EditText txtContent;
    Button btnPostComment;

    FirebaseDatabase firebaseDatabase;

    View viewCommentAndRateStep1;

    CommentController commentController;

    SharedPreferences sharedPreferences;
    String UID;

    RoomModel roomModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewCommentAndRateStep1 = inflater.inflate(R.layout.comment_and_rate_step_1_room_detail_view, container, false);
        sharedPreferences = ((commentAndRateMain)this.getActivity()).getSharedPreferences(LoginView.PREFS_DATA_NAME, Context.MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");
        roomModel = ((commentAndRateMain) this.getActivity()).getRoomModelInfo();

        initControl();

        initStar();

        setAdapter();

        chooseStar();

        return viewCommentAndRateStep1;
    }

    @Override
    public void onStart() {
        super.onStart();

        initData();
    }

    public commentAndRateStep1() {

    }

    private void initControl() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        grvStar = (GridView) viewCommentAndRateStep1.findViewById(R.id.grV_star);
        txtVRoomName = (TextView) viewCommentAndRateStep1.findViewById(R.id.txtV_name_comment_and_rate_step_1);
        txtVRoomAddress = (TextView) viewCommentAndRateStep1.findViewById(R.id.txtV_address_comment_and_rate_step_1);
        txtVScores = (TextView) viewCommentAndRateStep1.findViewById(R.id.txtV_scores);
        txtTitle = (EditText) viewCommentAndRateStep1.findViewById(R.id.txt_title_comment_and_rate_step_1);
        txtContent = (EditText) viewCommentAndRateStep1.findViewById(R.id.txt_content_comment_and_rate_step_1);
        btnPostComment = (Button) viewCommentAndRateStep1.findViewById(R.id.btn_postComment);
        commentController = new CommentController((commentAndRateMain) this.getActivity(), UID);

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });

        txtTitle.requestFocus(); // Linh thêm
    }

    private void initStar() {
        lstStar = new ArrayList<>();
        lstStar.add(R.drawable.ic_svg_star_yellow_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
    }

    private void setAdapter() {
        adapterGridRateStar = new AdapterGridRateStar((commentAndRateMain) getContext(), R.layout.rate_star_grid_view, lstStar);
        grvStar.setAdapter(adapterGridRateStar);
    }

    private void chooseStar() {
        scores = 1;
        txtVScores.setText("Đánh giá: " + String.valueOf(scores) + "/10");

        grvStar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rateStar(position);
            }
        });
    }

    private void rateStar(int position) {
        scores = position + 1;

        txtVScores.setText("Đánh giá: " + String.valueOf(scores) + "/10");
        lstStar = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i <= position) {
                lstStar.add(R.drawable.ic_svg_star_yellow_100);
            } else {
                lstStar.add(R.drawable.ic_svg_star_gray_100);
            }
        }

        adapterGridRateStar = new AdapterGridRateStar((commentAndRateMain) getContext(), R.layout.rate_star_grid_view, lstStar);
        grvStar.setAdapter(adapterGridRateStar);
    }

    private void initData() {
        //Set address for room
        String longAddress = roomModel.getApartmentNumber() +" "+roomModel.getStreet()+", "
                +roomModel.getWard()+", "+roomModel.getCounty()+", "+roomModel.getCity();
        //End set address for room

        txtVRoomName.setText(roomModel.getName());
        txtVRoomAddress.setText(longAddress);
    }

    private void addComment() {
        CommentModel newCommentModel = new CommentModel();
        String title = txtTitle.getText().toString();
        String content = txtContent.getText().toString();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        String userId = sharedPreferences.getString("currentUserId", "");

        String error = "Please enter";
        if (title.trim().length() == 0) {
            error += " title";
            Toast.makeText((commentAndRateMain) getContext(), error, Toast.LENGTH_SHORT).show();
        } else if (content.trim().length() == 0) {
            error += " content";
            Toast.makeText((commentAndRateMain) getContext(), error, Toast.LENGTH_SHORT).show();
        } else {
            newCommentModel.setTitle(title);
            newCommentModel.setContent(content);
            newCommentModel.setStars(scores);
            newCommentModel.setLikes(0);
            newCommentModel.setTime(date);
            newCommentModel.setUser(userId);

            commentController.addComment(newCommentModel, roomModel.getRoomID(), txtTitle, txtContent);

            initStar();
            setAdapter();

            txtTitle.requestFocus();
        }
    }
}
