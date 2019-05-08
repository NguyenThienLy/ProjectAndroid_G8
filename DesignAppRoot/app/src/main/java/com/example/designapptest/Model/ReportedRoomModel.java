package com.example.designapptest.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportedRoomModel {
    String reason;
    String detail;
    String time;
    String reportID, userID;

    //Chủ report
    UserModel userReport;

    //Phòng bị report
    RoomModel reportedRoom;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserModel getUserReport() {
        return userReport;
    }

    public void setUserReport(UserModel userReport) {
        this.userReport = userReport;
    }

    public RoomModel getReportedRoom() {
        return reportedRoom;
    }

    public void setReportedRoom(RoomModel reportedRoom) {
        this.reportedRoom = reportedRoom;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    public ReportedRoomModel() {
        //Trả về node comment của database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public void ListReportedRooms() {
        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Thêm danh sách phòng bị report
                DataSnapshot dataSnapshotReportedRooms = dataSnapshot.child("ReportedRoom");

                List<ReportedRoomModel> tempReportedRoomList = new ArrayList<>();

                //Duyệt tất cả các giá trị trong node tương ứng
                for (DataSnapshot ReportedRoomsValue : dataSnapshotReportedRooms.getChildren()) {
                    String roomId = ReportedRoomsValue.getKey();

                    DataSnapshot dataSnapshotReportedRoom = dataSnapshotReportedRooms.child(roomId);
                    for (DataSnapshot ReportedRoomValue : dataSnapshotReportedRoom.getChildren()) {
                        ReportedRoomModel reportedRoomModel = ReportedRoomValue.getValue(ReportedRoomModel.class);
                        reportedRoomModel.setReportID(ReportedRoomValue.getKey());

                        //Duyệt user tương ứng để lấy ra thông tin user bình luận
                        UserModel tempUser = dataSnapshot.child("Users").child(reportedRoomModel.getUserID()).getValue(UserModel.class);
                        reportedRoomModel.setUserReport(tempUser);
                        //End duyệt user tương ứng để lấy ra thông tin user bình luận

                        //Duyệt room tương ứng để lấy ra thông tin reported room
                        RoomModel tempRoom = dataSnapshot.child("Room").child(roomId).getValue(RoomModel.class);
                        reportedRoomModel.setReportedRoom(tempRoom);
                        //End duyệt room tương ứng để lấy ra thông tin reported room

                        tempReportedRoomList.add(reportedRoomModel);
                    }
                }
                //End Thêm danh sách bình luận của phòng trọ
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void addReport(ReportedRoomModel reportedRoomModel, String roomId, final Context context) {
        DatabaseReference nodeReportedRoom = FirebaseDatabase.getInstance().getReference().child("ReportedRoom");
        String reportId = nodeReportedRoom.child(roomId).push().getKey();

        nodeReportedRoom.child(roomId).child(reportId).setValue(reportedRoomModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Thanks for your report", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
