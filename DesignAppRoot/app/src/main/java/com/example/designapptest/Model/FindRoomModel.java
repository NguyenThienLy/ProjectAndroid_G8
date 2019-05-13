package com.example.designapptest.Model;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.designapptest.Controller.Interfaces.IFindRoomModel;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.Views.FindRoom;
import com.example.designapptest.Views.FindRoomAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.ContentHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FindRoomModel implements Parcelable {
    String user;
    String time;
    double maxPrice, minPrice;
    boolean gender;

    //Mã tìm ở ghép
    String findRoomID;

    //Chủ tìm ở ghép
    UserModel findRoomOwner;

    // Lưu danh sách tiện nghi phòng trọ
    List<ConvenientModel> listConvenientRoom;

    // Lưu danh sách các tiện nghi của phòng trọ.
    private List<String> convenients;

    // Lưu danh sách các id của vị trí phòng trọ.
    private List<String> location;

    protected FindRoomModel(Parcel in) {
        user = in.readString();
        time = in.readString();
        maxPrice = in.readDouble();
        minPrice = in.readDouble();
        gender = in.readByte() != 0;
        findRoomID = in.readString();
        findRoomOwner = in.readParcelable(UserModel.class.getClassLoader());
        listConvenientRoom = in.createTypedArrayList(ConvenientModel.CREATOR);
        convenients = in.createStringArrayList();
        location = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(time);
        dest.writeDouble(maxPrice);
        dest.writeDouble(minPrice);
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(findRoomID);
        dest.writeParcelable(findRoomOwner, flags);
        dest.writeTypedList(listConvenientRoom);
        dest.writeStringList(convenients);
        dest.writeStringList(location);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FindRoomModel> CREATOR = new Creator<FindRoomModel>() {
        @Override
        public FindRoomModel createFromParcel(Parcel in) {
            return new FindRoomModel(in);
        }

        @Override
        public FindRoomModel[] newArray(int size) {
            return new FindRoomModel[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTimee(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public List<String> getConvenients() {
        return convenients;
    }

    public void setConvenients(List<String> convenients) {
        this.convenients = convenients;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public String getFindRoomID() {
        return findRoomID;
    }

    public void setFindRoomID(String findRoomID) {
        this.findRoomID = findRoomID;
    }

    public UserModel getFindRoomOwner() {
        return findRoomOwner;
    }

    public void setFindRoomOwner(UserModel findRoomOwner) {
        this.findRoomOwner = findRoomOwner;
    }

    public List<ConvenientModel> getListConvenientRoom() {
        return listConvenientRoom;
    }

    public void setListConvenientRoom(List<ConvenientModel> listConvenientRoom) {
        this.listConvenientRoom = listConvenientRoom;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {

        this.minPrice = minPrice;
    }

    public double getMaxPrice() {

        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {

        this.maxPrice = maxPrice;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    //Lưu ý phải có hàm khởi tạo rỗng
    public FindRoomModel() {
        //Trả về node root của database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    // Hàm khởi tạo có tham số đầy đủ
    public FindRoomModel(String user, String time, double minPrice, double maxPrice, boolean gender,
                         String findRoomID, List<String> convenients, List<String> location) {
        this.user = user;
        this.time = time;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.gender = gender;
        this.findRoomID = findRoomID;
        this.convenients = convenients;
        this.location = location;
    }

    public void ListFindRoom(final IFindRoomModel findRoomModelInterface) {

        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Duyệt vào node Room trên firebase
                DataSnapshot dataSnapshotRoom = dataSnapshot.child("FindRoom");

                //Duyệt hết trong danh sách tìm ở ghép.
                for (DataSnapshot valueFindRoom : dataSnapshotRoom.getChildren()) {
                    //Lấy ra giá trị ép kiểu qua kiểu RoomModel
                    FindRoomModel findRoomModel = valueFindRoom.getValue(FindRoomModel.class);
                    //Set mã phòng trọ
                    findRoomModel.setFindRoomID(valueFindRoom.getKey());

                    // Chỉ xử lí nếu khác null
                    if (findRoomModel.getConvenients() != null) {
                        //Thêm danh sách tiện nghi của phòng trọ dựa vào danh sách id đã có ở findRoomModel
                        List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                        //Duyệt tất cả các giá trị trong node tương ứng
                        int index;
                        int size = findRoomModel.getConvenients().size();

                        for (index = 0; index < size; index++) {
                            String convenientId = findRoomModel.getConvenients().get(index);
                            if (convenientId != null) {
                                ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                                convenientModel.setConvenientID(convenientId);

                                tempConvenientList.add(convenientModel);
                            }
                        }

                        findRoomModel.setListConvenientRoom(tempConvenientList);
                    }
                    //End Thêm danh sách tiện nghi của phòng trọ

                    //Thêm thông tin chủ sở hữu cho phòng trọ
                    UserModel tempUser = dataSnapshot.child("Users").child(findRoomModel.getUser()).getValue(UserModel.class);
                    findRoomModel.setFindRoomOwner(tempUser);

                    //End thêm thông tin chủ sở hữu cho phòng trọ

                    //Kích hoạt interface
                    findRoomModelInterface.getListFindRoom(findRoomModel);
                }

                //Kích hoạt interface
                findRoomModelInterface.getSuccessNotify();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }


    public void addFindRoom(final FindRoomModel findRoomModel, final IFindRoomModel findRoomModelInterface) {
        // Tại sao ở đây nếu dùng notRoot thì nó bằng null
        DatabaseReference nodeFindRoom = FirebaseDatabase.getInstance().getReference().child("FindRoom");

        //Lấy Key push động vào firebase
        String findRoomID = nodeFindRoom.push().getKey();

        //push vào node room
        nodeFindRoom.child(findRoomID).setValue(findRoomModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                findRoomModelInterface.addSuccessNotify();
            }
        });
    }
}
