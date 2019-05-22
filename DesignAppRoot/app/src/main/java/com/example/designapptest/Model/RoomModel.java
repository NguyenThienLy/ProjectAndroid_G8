package com.example.designapptest.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.designapptest.ClassOther.FilePath;
import com.example.designapptest.Controller.Interfaces.ICallBackFromAddRoom;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;

public class RoomModel implements Parcelable { // Linh thêm
    String describe, name, owner, timeCreated;
    long currentNumber, maxNumber;
    double latitude, longtitude, length, width, rentalCosts;
    boolean authentication;
    boolean gender;

    //Update 21/4/2019 by qui: chia address ra nhỏ để fillter

    String apartmentNumber, county, street, ward, city;

    //End Update 21/4/2019

    //id để generate từ firebase
    String typeID;

    //String để lấy giá trị từ ID
    String roomType;

    //String để lưu link hình ảnh dung lượng thấp của phòng
    String compressionImage;

    //Đánh giá của người xem trọ
    long medium;
    long great;
    long prettyGood;
    long bad;

    //Mã Phòng trọ
    String roomID;

    //Chủ phòng trọ
    UserModel roomOwner;

    // Lưu dnh sách tiện nghi phòng trọ
    List<ConvenientModel> listConvenientRoom;

    // Lưu dnh sách giá phòng trọ
    List<RoomPriceModel> listRoomPrice;

    //Lưu mảng tên hình trên firebase
    private List<String> listImageRoom;

    //Lưu mảng comment của phòng trọ
    List<CommentModel> listCommentRoom;

    public static List<String> myFavoriteRooms = new ArrayList<String>();

    protected RoomModel(Parcel in) {

        describe = in.readString();
        name = in.readString();
        owner = in.readString();
        timeCreated = in.readString();
        currentNumber = in.readLong();
        maxNumber = in.readLong();
        latitude = in.readDouble();
        longtitude = in.readDouble();
        length = in.readDouble();
        width = in.readDouble();
        rentalCosts = in.readDouble();
        authentication = in.readByte() != 0;
        gender = in.readByte() != 0;
        typeID = in.readString();
        roomType = in.readString();
        medium = in.readLong();
        great = in.readLong();
        prettyGood = in.readLong();
        bad = in.readLong();
        roomID = in.readString();

        //update 14/4/2019
        apartmentNumber = in.readString();
        county = in.readString();
        street = in.readString();
        ward = in.readString();
        city = in.readString();
        roomOwner = in.readParcelable(UserModel.class.getClassLoader());
        //end update 14/4/2019

        listImageRoom = in.createStringArrayList();

        listConvenientRoom = new ArrayList<ConvenientModel>();
        in.readTypedList(listConvenientRoom, ConvenientModel.CREATOR);

        // Linh thêm
        listRoomPrice = new ArrayList<>();
        in.readTypedList(listRoomPrice, RoomPriceModel.CREATOR);

        listCommentRoom = new ArrayList<CommentModel>();
        in.readTypedList(listCommentRoom, CommentModel.CREATOR);
    }

    public static final Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) {
            return new RoomModel(in);
        }

        @Override
        public RoomModel[] newArray(int size) {
            return new RoomModel[size];
        }
    };

    public String getCompressionImage() {
        return compressionImage;
    }

    public void setCompressionImage(String compressionImage) {
        this.compressionImage = compressionImage;
    }

    public UserModel getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(UserModel roomOwner) {
        this.roomOwner = roomOwner;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public List<ConvenientModel> getListConvenientRoom() {
        return listConvenientRoom;
    }

    public void setListConvenientRoom(List<ConvenientModel> listConvenientRoom) {
        this.listConvenientRoom = listConvenientRoom;
    }

    public List<RoomPriceModel> getListRoomPrice() {
        return listRoomPrice;
    }

    public void setListRoomPrice(List<RoomPriceModel> listRoomPrice) {
        this.listRoomPrice = listRoomPrice;
    }

    public List<CommentModel> getListCommentRoom() {
        return listCommentRoom;
    }

    public void setListCommentRoom(List<CommentModel> listCommentRoom) {
        this.listCommentRoom = listCommentRoom;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public long getMedium() {
        return medium;
    }

    public void setMedium(long medium) {
        this.medium = medium;
    }

    public long getGreat() {
        return great;
    }

    public void setGreat(long great) {
        this.great = great;
    }

    public long getPrettyGood() {
        return prettyGood;
    }

    public void setPrettyGood(long prettyGood) {
        this.prettyGood = prettyGood;
    }

    public long getBad() {
        return bad;
    }

    public void setBad(long bad) {
        this.bad = bad;
    }


    public List<String> getListImageRoom() {
        return listImageRoom;
    }

    public void setListImageRoom(List<String> listImageRoom) {
        this.listImageRoom = listImageRoom;
    }


    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    //Lưu ý phải có hàm khởi tạo rỗng
    public RoomModel() {
        //Trả về node root của database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(long currentNumber) {
        this.currentNumber = currentNumber;
    }

    public long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getRentalCosts() {
        return rentalCosts;
    }

    public void setRentalCosts(double rentalCosts) {
        this.rentalCosts = rentalCosts;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public void ListRoom(final IMainRoomModel mainRoomModelInterface) {

        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xoá list favorite room để add lại khi user xóa hoặc thêm lại favorite room ở favoriteRoomView
                mainRoomModelInterface.refreshListFavoriteRoom();

                //Duyệt vào node Room trên firebase
                DataSnapshot dataSnapshotRoom = dataSnapshot.child("Room");

                //Duyệt hết trong danh sách phòng trọ
                for (DataSnapshot valueRoom : dataSnapshotRoom.getChildren()) {
                    //Lấy ra giá trị ép kiểu qua kiểu RoomModel
                    RoomModel roomModel = valueRoom.getValue(RoomModel.class);
                    //Set mã phòng trọ
                    roomModel.setRoomID(valueRoom.getKey());

                    //Set loại phòng trọ
                    String tempType = dataSnapshot.child("RoomTypes")
                            .child(roomModel.getTypeID())
                            .getValue(String.class);

                    Log.d("kiem tra", tempType);
                    roomModel.setRoomType(tempType);

                    //Thêm tên danh sách tên hình vào phòng trọ

                    //Duyệt vào node RoomImages trên firebase và duyệt vào node có mã room tương ứng
                    DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(valueRoom.getKey());
                    List<String> tempImageList = new ArrayList<String>();
                    //Duyêt tất cả các giá trị của node tương ứng
                    for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                        tempImageList.add(valueImage.getValue(String.class));
                    }

                    //set mảng hình vào list
                    roomModel.setListImageRoom(tempImageList);

                    //Thêm vào hình dung lượng thấp của phòng trọ
                    DataSnapshot dataSnapshotComPress = dataSnapshot.child("RoomCompressionImages").child(valueRoom.getKey());
                    //Kiểm tra nếu có dữ liệu
                    if(dataSnapshotComPress.getChildrenCount()>0){
                        for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                            roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                        }
                    }else {
                        roomModel.setCompressionImage(tempImageList.get(0));
                    }

                    //End Thêm tên danh sách tên hình vào phòng trọ

                    //Thêm danh sách bình luận của phòng trọ

                    DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(valueRoom.getKey());
                    List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                        CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                        commentModel.setCommentID(CommentValue.getKey());
                        //Duyệt user tương ứng để lấy ra thông tin user bình luận
                        UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                        commentModel.setUserComment(tempUser);
                        //End duyệt user tương ứng để lấy ra thông tin user bình luận

                        tempCommentList.add(commentModel);
                    }

                    roomModel.setListCommentRoom(tempCommentList);

                    //End Thêm danh sách bình luận của phòng trọ

                    //Thêm danh sách tiện nghi của phòng trọ

                    DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(valueRoom.getKey());
                    List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                        String convenientId = valueConvenient.getValue(String.class);
                        ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                        convenientModel.setConvenientID(convenientId);

                        tempConvenientList.add(convenientModel);
                    }

                    roomModel.setListConvenientRoom(tempConvenientList);

                    //End Thêm danh sách tiện nghi của phòng trọ

                    //Thêm danh sách giá của phòng trọ

                    DataSnapshot dataSnapshotRoomPrice = dataSnapshot.child("RoomPrice").child(valueRoom.getKey());
                    List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                        String roomPriceId = valueRoomPrice.getKey();
                        double price = valueRoomPrice.getValue(double.class);

                        if (roomPriceId.equals("IDRPT4")) {
                            continue;
                        }
                        RoomPriceModel roomPriceModel = dataSnapshot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                        roomPriceModel.setRoomPriceID(roomPriceId);
                        roomPriceModel.setPrice(price);

                        tempRoomPriceList.add(roomPriceModel);
                    }

                    roomModel.setListRoomPrice(tempRoomPriceList);

                    //End Thêm danh sách giá của phòng trọ

                    //Thêm thông tin chủ sở hữu cho phòng trọ
                    UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                    roomModel.setRoomOwner(tempUser);

                    //End thêm thông tin chủ sở hữu cho phòng trọ

                    //Kích hoạt interface
                    mainRoomModelInterface.getListMainRoom(roomModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addValueEventListener(valueEventListener);
    }

    private void SenData(List<String> ListRoomID, IMainRoomModel mainRoomModelInterface){
        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xoá list favorite room để add lại khi user xóa hoặc thêm lại favorite room ở favoriteRoomView
                mainRoomModelInterface.refreshListFavoriteRoom();

                for(String RoomID:ListRoomID){
                    //Duyệt vào room cần lấy dữ liệu
                    DataSnapshot dataSnapshotValueRoom = dataSnapshot.child("Room").child(RoomID);

                    //Lấy ra giá trị ép kiểu qua kiểu RoomModel
                    RoomModel roomModel = dataSnapshotValueRoom.getValue(RoomModel.class);
                    Log.d("check", RoomID);

                    roomModel.setRoomID(RoomID);

                    //Set loại phòng trọ
                    String tempType = dataSnapshot.child("RoomTypes")
                            .child(roomModel.getTypeID())
                            .getValue(String.class);

                    roomModel.setRoomType(tempType);

                    //Thêm tên danh sách tên hình vào phòng trọ

                    //Duyệt vào node RoomImages trên firebase và duyệt vào node có mã room tương ứng
                    DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(RoomID);
                    List<String> tempImageList = new ArrayList<String>();
                    //Duyêt tất cả các giá trị của node tương ứng
                    for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                        tempImageList.add(valueImage.getValue(String.class));
                    }

                    //set mảng hình vào list
                    roomModel.setListImageRoom(tempImageList);

                    //End Thêm tên danh sách tên hình vào phòng trọ

                    //Thêm vào hình dung lượng thấp của phòng trọ
                    DataSnapshot dataSnapshotComPress = dataSnapshot.child("RoomCompressionImages").child(RoomID);
                    //Kiểm tra nếu có dữ liệu
                    if(dataSnapshotComPress.getChildrenCount()>0){
                        for (DataSnapshot valueCompressionImage : dataSnapshotComPress.getChildren()) {
                            roomModel.setCompressionImage(valueCompressionImage.getValue(String.class));
                        }
                    }else {
                        roomModel.setCompressionImage(tempImageList.get(0));
                    }

                    //Thêm danh sách bình luận của phòng trọ

                    DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(RoomID);
                    List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                        CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                        commentModel.setCommentID(CommentValue.getKey());
                        //Duyệt user tương ứng để lấy ra thông tin user bình luận
                        UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                        commentModel.setUserComment(tempUser);
                        //End duyệt user tương ứng để lấy ra thông tin user bình luận

                        tempCommentList.add(commentModel);
                    }

                    roomModel.setListCommentRoom(tempCommentList);

                    //End Thêm danh sách bình luận của phòng trọ

                    //Thêm danh sách tiện nghi của phòng trọ

                    DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(RoomID);
                    List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                        String convenientId = valueConvenient.getValue(String.class);
                        ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                        convenientModel.setConvenientID(convenientId);

                        tempConvenientList.add(convenientModel);
                    }

                    roomModel.setListConvenientRoom(tempConvenientList);

                    //End Thêm danh sách tiện nghi của phòng trọ

                    //Thêm danh sách giá của phòng trọ

                    DataSnapshot dataSnapshotRoomPrice = dataSnapshot.child("RoomPrice").child(RoomID);
                    List<RoomPriceModel> tempRoomPriceList = new ArrayList<RoomPriceModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot valueRoomPrice : dataSnapshotRoomPrice.getChildren()) {
                        String roomPriceId = valueRoomPrice.getKey();
                        double price = valueRoomPrice.getValue(double.class);

                        if (roomPriceId.equals("IDRPT4")) {
                            continue;
                        }
                        RoomPriceModel roomPriceModel = dataSnapshot.child("RoomPriceType").child(roomPriceId).getValue(RoomPriceModel.class);
                        roomPriceModel.setRoomPriceID(roomPriceId);
                        roomPriceModel.setPrice(price);

                        tempRoomPriceList.add(roomPriceModel);
                    }

                    roomModel.setListRoomPrice(tempRoomPriceList);

                    //End Thêm danh sách giá của phòng trọ

                    //Thêm thông tin chủ sở hữu cho phòng trọ
                    UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                    roomModel.setRoomOwner(tempUser);

                    //End thêm thông tin chủ sở hữu cho phòng trọ

                    //Kích hoạt interface
                    mainRoomModelInterface.getListMainRoom(roomModel);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void ListRoomUser(final IMainRoomModel mainRoomModelInterface, String userID) {

        Query nodeRoomOderbyUserID = nodeRoot.child("Room")
                .orderByChild("owner")
                .equalTo(userID);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> ListIDRoom = new ArrayList<String>();
                int i = 0;
                for(DataSnapshot RoomSnapShot:dataSnapshot.getChildren()){
                    //Lọc ra danh sách Room đã
                    ListIDRoom.add(RoomSnapShot.getKey());
                    i++;
                    if (i == dataSnapshot.getChildrenCount()) {
                        //Thêm dữ liệu và gửi về lại UI
                        SenData(ListIDRoom,mainRoomModelInterface);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoomOderbyUserID.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(describe);
        dest.writeString(name);
        dest.writeString(owner);
        dest.writeString(timeCreated);
        dest.writeLong(currentNumber);
        dest.writeLong(maxNumber);
        dest.writeDouble(latitude);
        dest.writeDouble(longtitude);
        dest.writeDouble(length);
        dest.writeDouble(width);
        dest.writeDouble(rentalCosts);
        dest.writeByte((byte) (authentication ? 1 : 0));
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(typeID);
        dest.writeString(roomType);
        dest.writeLong(medium);
        dest.writeLong(great);
        dest.writeLong(prettyGood);
        dest.writeLong(bad);
        dest.writeString(roomID);

        //update 21/4/2019
        dest.writeString(apartmentNumber);
        dest.writeString(county);
        dest.writeString(street);
        dest.writeString(ward);
        dest.writeString(city);
        dest.writeParcelable(roomOwner, flags);
        //end update 21/4/2019

        dest.writeStringList(listImageRoom);
        dest.writeTypedList(listConvenientRoom);
        // Linh thêm
        dest.writeTypedList(listRoomPrice);
        dest.writeTypedList(listCommentRoom);
    }


    //Hàm thêm phòng mới |Thêm theo thứ tự thêm hình trước và thêm vào node room sau cùng để tránh lỗi xảy ra
    public void addRoom(RoomModel roomModel, List<String> listConvenient, List<String> listPathImage
            , float electricBill, float warterBill, float InternetBill, float parkingBill, ICallBackFromAddRoom iCallBackFromAddRoom, Context context) {

        //Lấy ra node room
        DatabaseReference nodeRoom = nodeRoot.child("Room");
        //Lấy Key push động vào firebase
        String RoomID = nodeRoom.push().getKey();

        //Tải hình lên trước sau khi hoàn tất tải hình mới thêm vào các thông tin cần thiết
        //Tải hình lên
        final int[] count = {0};
        for (String pathImage : listPathImage) {

            //Lấy ra ngày giờ hiện tại để phân biệt giữa các ảnh
            DateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
            String date = df.format(Calendar.getInstance().getTime());
            //End lấy ra ngày giờ hiện tại để phân biệt giữa các ảnh

            //Lấy đường dẫn Uri của hình
            Uri file = Uri.parse(pathImage);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("Images/" + date + file.getLastPathSegment());

            //Tải hình lên bằng hàm putFile
            storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Lấy URL hình mới upload
                            String dowloadURL = uri.toString();
                            //Push hình vào danh sách hình tương ứng với room
                            nodeRoot.child("RoomImages").child(RoomID).push().setValue(dowloadURL);
                            count[0]++;
                            if (count[0] == listPathImage.size()) {
                                //Thêm vào thông tin khác
                                //Thêm danh sách tiện ích
                                for (String dataConvenient : listConvenient) {
                                    nodeRoot.child("RoomConvenients").child(RoomID).push().setValue(dataConvenient);
                                }
                                //End thêm danh sách tiện ích

                                //Thêm chi tiết các giá phòng
                                addDetailRoomPrice(RoomID, electricBill, warterBill, InternetBill, parkingBill, (float) roomModel.getRentalCosts());
                                //End thêm chi tiết các giá phòng

                                //Thêm vào thông tin phòng
                                nodeRoom.child(RoomID).setValue(roomModel);

                                //Thêm vào node RoomLocation để filter

                                //Cắt bỏ P. trước phường
                                String SplitWarn = roomModel.getWard().substring(2);
                                //Push ID room vào
                                nodeRoot.child("LocationRoom").child(roomModel.getCounty())
                                        .child(SplitWarn)
                                        .child(roomModel.getStreet())
                                        .push().setValue(RoomID);

                                //End thêm vào node RoomLocation để filter

                                //Đăng hình ảnh đã nén ở trang đầu để load nhanh hơn
                                //Tạo đường dẫn file

                                //Lấy ra đường dẫn file
                                String path = FilePath.getPath(context, Uri.parse(listPathImage.get(0)));

                                //Tạo file tương ứng với đường dẫn đó
                                File image_filePath = new File(path);

                                //Tạo Bitmap để nén ảnh và tải lên
                                Bitmap image_bitmap = null;
                                try {

                                    image_bitmap = new Compressor(context)
                                            .setMaxHeight(320)
                                            .setMaxWidth(240)
                                            .setQuality(50)
                                            .compressToBitmap(image_filePath);
                                    //Tạo mảng byte để đổ dữ liệu từ bitmap sang để up lên storage
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    //Chuyển ảnh về JPG và đổ vào mảng byte
                                    image_bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);

                                    //Đổ dữ liệu ra mảng byte
                                    final byte[] image_byte = byteArrayOutputStream.toByteArray();

                                    //Lấy ra ngày giờ hiện tại để phân biệt giữa các ảnh
                                    DateFormat df2 = new SimpleDateFormat("ddMMyyyyHHmmss");
                                    String date2 = df2.format(Calendar.getInstance().getTime());
                                    //End lấy ra ngày giờ hiện tại để phân biệt giữa các ảnh

                                    //Tạo đường dẫn đến thu mục upload hình
                                    StorageReference CompressStorage = FirebaseStorage.getInstance().getReference().child("CompressionImages/"+roomModel.getOwner()+date2+".jpg");

                                    //Up load hình
                                    UploadTask uploadTask = CompressStorage.putBytes(image_byte);
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            CompressStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    //Lấy ra đường dẫn download
                                                    String ComPressDowloadURL = uri.toString();
                                                    //Thêm vào trong node Room
                                                    nodeRoot.child("RoomCompressionImages").child(RoomID).push().setValue(ComPressDowloadURL);

                                                    iCallBackFromAddRoom.stopProgess(true);
                                                }
                                            });
                                        }
                                    });
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });

        }
        //End tải hình lên

        //push vào node room

    }

    //Hàm thêm vào chi tiết giá cả của phòng
    private void addDetailRoomPrice(String roomID, float electricBill, float warterBill, float InternetBill, float parkingBill, float roomBill) {
        //Thêm tiền nước
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT0").setValue(warterBill);
        //Thêm tiền điện
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT3").setValue(electricBill);
        //Thêm tiền mạng
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT1").setValue(InternetBill);
        //Thêm tiền giữ xe
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT2").setValue(parkingBill);
        //Thêm tiền phòng
        nodeRoot.child("RoomPrice").child(roomID).child("IDRPT4").setValue(roomBill);
    }

    public static void getListFavoriteRoomsId(String UID) {

        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Thêm danh sách id trọ yêu thích
                RoomModel.myFavoriteRooms.clear();

                //Duyệt tất cả các giá trị trong node tương ứng
                for (DataSnapshot favoriteRoom : dataSnapshot.getChildren()) {
                    String roomId = favoriteRoom.getKey();
                    RoomModel.myFavoriteRooms.add(roomId);
                }
                //End thêm danh sách id trọ yêu thích
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        DatabaseReference node = FirebaseDatabase.getInstance().getReference();
        node.child("FavoriteRooms").child(UID).addValueEventListener(valueEventListener);
    }

    public void getAuthenticationRooms(final  IMainRoomModel iMainRoomModel){
        Query nodeRoomOderbyAuthentication = nodeRoot.child("Room")
                .orderByChild("authentication")
                .equalTo(true);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> ListIDRoom = new ArrayList<String>();
                int i = 0;
                for(DataSnapshot RoomSnapShot:dataSnapshot.getChildren()){
                    //Lọc ra danh sách Room đã
                    ListIDRoom.add(RoomSnapShot.getKey());
                    i++;
                    if (i == dataSnapshot.getChildrenCount()) {
                        //Thêm dữ liệu và gửi về lại UI
                        SenData(ListIDRoom,iMainRoomModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoomOderbyAuthentication.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getListFavoriteRooms(final IMainRoomModel iMainRoomModel, final SharedPreferences sharedPreferences) {
        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentUserId = sharedPreferences.getString("currentUserId", "");

                iMainRoomModel.refreshListFavoriteRoom();

//                //Thêm danh sách id trọ yêu thích
//                List<String> listFavoriteRoomsId = new ArrayList<String>();
//
//                DataSnapshot dataSnapshotFavoriteRooms = dataSnapshot.child("FavoriteRooms").child(currentUserId);
//
//                //Duyệt tất cả các giá trị trong node tương ứng
//                for (DataSnapshot favoriteRoom : dataSnapshotFavoriteRooms.getChildren()) {
//                    String roomId = favoriteRoom.getKey();
//                    listFavoriteRoomsId.add(roomId);
//                }
//                //End thêm danh sách id trọ yêu thích

                // Lấy danh sách id trọ yêu thích
//                List<String> listFavoriteRoomsId = new ArrayList<String>();
//                listFavoriteRoomsId = listFavoriteRoomsId;

                //Thêm danh sách trọ yêu thích

                DataSnapshot dataSnapshotRoom = dataSnapshot.child("Room");

                Log.d("check", "fav room");

                //Duyệt tất cả các giá trị trong node tương ứng
                for (String favoriteRoomId : myFavoriteRooms) {
                    for (DataSnapshot roomValue : dataSnapshotRoom.getChildren()) {
                        String roomId = roomValue.getKey();

                        if (roomId.equals(favoriteRoomId)) {
                            //Lấy ra giá trị ép kiểu qua kiểu RoomModel
                            RoomModel roomModel = roomValue.getValue(RoomModel.class);

                            //Set mã phòng trọ
                            roomModel.setRoomID(roomId);

                            //Set loại phòng trọ
                            String tempType = dataSnapshot.child("RoomTypes")
                                    .child(roomModel.getTypeID())
                                    .getValue(String.class);

                            roomModel.setRoomType(tempType);


                            //Thêm tên danh sách đường dẫn hình vào phòng trọ

                            //Duyệt vào node RoomImages trên firebase và duyệt vào node có mã room tương ứng
                            DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(roomId);

                            List<String> tempImageList = new ArrayList<String>();

                            //Duyêt tất cả các giá trị của node tương ứng
                            for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {
                                tempImageList.add(valueImage.getValue(String.class));
                            }

                            //set mảng đường dẫn hình vào list
                            roomModel.setListImageRoom(tempImageList);

                            //End Thêm tên danh sách đường dẫn hình vào phòng trọ


                            //Thêm danh sách bình luận của phòng trọ

                            DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(roomId);
                            List<CommentModel> tempCommentList = new ArrayList<CommentModel>();

                            //Duyệt tất cả các giá trị trong node tương ứng
                            for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                                CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                                commentModel.setCommentID(CommentValue.getKey());

                                //Duyệt user tương ứng để lấy ra thông tin user bình luận
                                UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                                commentModel.setUserComment(tempUser);
                                //End duyệt user tương ứng để lấy ra thông tin user bình luận

                                tempCommentList.add(commentModel);
                            }

                            roomModel.setListCommentRoom(tempCommentList);

                            //End Thêm danh sách bình luận của phòng trọ


                            //Thêm danh sách tiện nghi của phòng trọ

                            DataSnapshot dataSnapshotConvenientRoom = dataSnapshot.child("RoomConvenients").child(roomId);
                            List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                            //Duyệt tất cả các giá trị trong node tương ứng
                            for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                                String convenientId = valueConvenient.getValue(String.class);
                                ConvenientModel convenientModel = dataSnapshot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                                convenientModel.setConvenientID(convenientId);

                                tempConvenientList.add(convenientModel);
                            }

                            roomModel.setListConvenientRoom(tempConvenientList);

                            //End Thêm danh sách tiện nghi của phòng trọ


                            //Thêm thông tin chủ sở hữu cho phòng trọ
                            UserModel tempUser = dataSnapshot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                            roomModel.setRoomOwner(tempUser);

                            //End thêm thông tin chủ sở hữu cho phòng trọ

                            //Kích hoạt interface
                            iMainRoomModel.getListMainRoom(roomModel);

                            break;
                        }
                    }
                }
                //End Thêm danh sách trọ yêu thích
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void addToFavoriteRooms(String roomId, IMainRoomModel iMainRoomModel, String UID) {
        DatabaseReference nodeFavoriteRooms = FirebaseDatabase.getInstance().getReference().child("FavoriteRooms");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        nodeFavoriteRooms.child(UID).child(roomId).child("time").setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    iMainRoomModel.makeToast("Đã thêm vào danh sách yêu thích");
                    iMainRoomModel.setIconFavorite(R.drawable.ic_favorite_full_white);
                }
            }
        });
    }

    public void removeFromFavoriteRooms(String roomId, IMainRoomModel iMainRoomModel, String UID) {
        DatabaseReference nodeFavoriteRooms = FirebaseDatabase.getInstance().getReference().child("FavoriteRooms");

        nodeFavoriteRooms.child(UID).child(roomId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    iMainRoomModel.makeToast("Đã xóa khỏi danh sách yêu thích");
                    iMainRoomModel.setIconFavorite(R.drawable.ic_favorite_border_white);
                }
            }
        });
    }
}
