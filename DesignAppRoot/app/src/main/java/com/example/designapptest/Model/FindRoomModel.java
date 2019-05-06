package com.example.designapptest.Model;

import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.designapptest.Controller.Interfaces.IFindRoomModel;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FindRoomModel implements Parcelable {
    String user;
    long maxPrice, minPrice;
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
        maxPrice = in.readLong();
        minPrice = in.readLong();
        gender = in.readByte() != 0;
        findRoomID = in.readString();
        findRoomOwner = in.readParcelable(UserModel.class.getClassLoader());
        listConvenientRoom = in.createTypedArrayList(ConvenientModel.CREATOR);
        convenients = in.createStringArrayList();
        location = in.createStringArrayList();
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

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {

        this.minPrice = minPrice;
    }

    public long getMaxPrice() {

        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {

        this.maxPrice = maxPrice;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    //Lưu ý phải có hàm khởi tạo rỗng
    public FindRoomModel() {
        //Trả về node root của database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
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

                    //Thêm danh sách tiện nghi của phòng trọ dựa vào danh sách id đã có ở findRoomModel
                    List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    int index;
                    for (index = 0; index < findRoomModel.getConvenients().size(); index++) {
                        String convenientId = findRoomModel.getConvenients().get(index);
                        if (convenientId != null) {
                            ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                            convenientModel.setConvenientID(convenientId);

                            tempConvenientList.add(convenientModel);
                        }
                    }

                    findRoomModel.setListConvenientRoom(tempConvenientList);
                    //End Thêm danh sách tiện nghi của phòng trọ

                    //Thêm thông tin chủ sở hữu cho phòng trọ
                    UserModel tempUser = dataSnapshot.child("Users").child(findRoomModel.getUser()).getValue(UserModel.class);
                    findRoomModel.setFindRoomOwner(tempUser);

                    //End thêm thông tin chủ sở hữu cho phòng trọ

                    //Kích hoạt interface
                    findRoomModelInterface.getListFindRoom(findRoomModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeLong(maxPrice);
        dest.writeLong(minPrice);
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(findRoomID);
        dest.writeParcelable(findRoomOwner, flags);
        dest.writeTypedList(listConvenientRoom);
        dest.writeStringList(convenients);
        dest.writeStringList(location);
    }
}
