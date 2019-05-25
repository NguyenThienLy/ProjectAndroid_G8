package com.example.designapptest.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.Model.UserModel;
import com.example.designapptest.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class personalPage extends AppCompatActivity {


    private EditText edtNamePersonalPage ;
    private Spinner spnGenderPersonalPage ;
    private EditText edtPhonePersonalPage;
    private ImageView imgAvtPersonalPage;
    private ImageButton btnImgCancelPersonalPage;
    private CheckBox chBoxEditPersonalPage;
    private TextView txtChangeAvtPersonalPage;
    private TextView txtPasswordPersonalPage;
    private EditText edtOldPasswordPersonalPage,edtNewPasswordPersonalPage,edtRetypeNewPasswordPersonalPage;
    private Button btnChangePasswordPersonalPage;
    private String mImageUrl;
    Uri uri;
    boolean isChangeAvatar=false;
    String name,gender,phoneNumber;
    boolean isMale;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_page_view);
        edtNamePersonalPage = findViewById(R.id.edt_name_personal_page);
        spnGenderPersonalPage = findViewById(R.id.spn_gender_personal_page);
        edtPhonePersonalPage = findViewById(R.id.edt_phone_personal_page);
        imgAvtPersonalPage = (ImageView) findViewById(R.id.img_avt_personal_page);
        chBoxEditPersonalPage = findViewById(R.id.chBox_edit_personal_page);
        btnImgCancelPersonalPage = findViewById(R.id.btnImg_cancel_personal_page);
        txtChangeAvtPersonalPage = findViewById(R.id.txt_change_avt_personal_page);
        txtPasswordPersonalPage = findViewById(R.id.tv_password_personal_page);
        edtOldPasswordPersonalPage = findViewById(R.id.edt_old_password_personal_page);
        edtNewPasswordPersonalPage = findViewById(R.id.edt_new_password_personal_page);
        edtRetypeNewPasswordPersonalPage = findViewById(R.id.edt_retype_new_password_personal_page);
        btnChangePasswordPersonalPage = findViewById(R.id.btn_change_password_personal_page);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        initControl();

        EditPersonal();


        cancel();

    }

    private  void getDataFromControl(){
        name=edtNamePersonalPage.getText().toString();
        phoneNumber=edtPhonePersonalPage.getText().toString();

    }



    private void initControl(){

        List<String> list = new ArrayList<>();
        list.add("Nam");
        list.add("Nữ");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGenderPersonalPage.setAdapter(adapter);
        spnGenderPersonalPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               gender = adapterView.getItemAtPosition(i).toString();
               if(i==0){
                   isMale=true;
               }
               else {
                   isMale=false;
               }
               spnGenderPersonalPage.setEnabled(false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences2 = this.getSharedPreferences(LoginView.PREFS_DATA_NAME, Context.MODE_PRIVATE);
        String UID = sharedPreferences2.getString(LoginView.SHARE_UID,"");

        DatabaseReference nodeUser = databaseReference.child("Users").child(UID);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);

                edtNamePersonalPage.setText(userModel.getName());
                edtPhonePersonalPage.setText(userModel.getPhoneNumber());
                if(userModel.isGender()==true){
                    spnGenderPersonalPage.setSelection(0);
                }
                else{
                    spnGenderPersonalPage.setSelection(1);
                }
                //mImageUrl=userModel.getAvatar();
                Picasso.get().load(userModel.getAvatar()).into(imgAvtPersonalPage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };

        nodeUser.addListenerForSingleValueEvent(valueEventListener);
    }

    private void EditPersonal(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences2 = this.getSharedPreferences(LoginView.PREFS_DATA_NAME, Context.MODE_PRIVATE);
        String UID = sharedPreferences2.getString(LoginView.SHARE_UID,"");

        DatabaseReference nodeUser = databaseReference.child("Users").child(UID);

        chBoxEditPersonalPage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   chBoxEditPersonalPage.setBackground(getDrawable(R.drawable.ic_svg_check_24));
                   btnImgCancelPersonalPage.setImageDrawable(getDrawable(R.drawable.ic_svg_cancel_24));
                   edtNamePersonalPage.setEnabled(true);
                   spnGenderPersonalPage.setEnabled(true);
                   edtPhonePersonalPage.setEnabled(true);
                   txtChangeAvtPersonalPage.setText("Đổi ảnh đại diện");
                   txtPasswordPersonalPage.setText("Đổi mật khẩu");

                   changeImage();



               }else{
                   progressDialog.setMessage("Đang chỉnh sửa ...");
                   progressDialog.setIndeterminate(true);
                   progressDialog.show();
                   chBoxEditPersonalPage.setBackground(getDrawable(R.drawable.ic_svg_edit_24));
                   btnImgCancelPersonalPage.setImageDrawable(null);
                   edtNamePersonalPage.setEnabled(false);
                   spnGenderPersonalPage.setEnabled(false);
                   edtPhonePersonalPage.setEnabled(false);
                   txtChangeAvtPersonalPage.setText("");
                   txtPasswordPersonalPage.setText("");
                   edtOldPasswordPersonalPage.setWidth(0);
                   edtNewPasswordPersonalPage.setWidth(0);
                   edtRetypeNewPasswordPersonalPage.setWidth(0);


                   if(isChangeAvatar) {
                       StorageReference storageReference = FirebaseStorage.getInstance().getReference("Users");
                       StorageReference ref = storageReference.child(uri.getLastPathSegment());

                       Task<Uri> urlTask = ref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                           @Override
                           public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                               if (!task.isSuccessful()) {
                                   throw task.getException();
                               }
                               return ref.getDownloadUrl();
                           }
                       }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                           @Override
                           public void onComplete(@NonNull Task<Uri> task) {
                               if (task.isSuccessful()) {
                                   getDataFromControl();
                                   Uri downloadUri = task.getResult();
                                   mImageUrl = downloadUri.toString();
                                   UserModel userModel = new UserModel();
                                   userModel.setName(name);
                                   userModel.setPhoneNumber(phoneNumber);
                                   userModel.setAvatar(mImageUrl);
                                   userModel.setGender(isMale);
                                   nodeUser.setValue(userModel);
                                   progressDialog.dismiss();

                               }
                           }
                       });
                   }
                   else {
                       getDataFromControl();

                       nodeUser.child("gender").setValue(isMale);
                       nodeUser.child("name").setValue(name);
                       nodeUser.child("phoneNumber").setValue(phoneNumber);
                       progressDialog.dismiss();


                   }
               }

            }
        });
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences2 = this.getSharedPreferences(LoginView.PREFS_DATA_NAME, Context.MODE_PRIVATE);
        String UID = sharedPreferences2.getString(LoginView.SHARE_UID,"");

        DatabaseReference nodeUser = databaseReference.child("Users").child(UID);

        if(requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Log.d("check", uri.getLastPathSegment());

            Picasso.get().load(uri).into(imgAvtPersonalPage);
            isChangeAvatar=true;

        }

    }

    private void changeImage(){
        imgAvtPersonalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void cancel(){
        btnImgCancelPersonalPage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                chBoxEditPersonalPage.setBackground(getDrawable(R.drawable.ic_svg_edit_24));
                btnImgCancelPersonalPage.setImageDrawable(null);
                edtNamePersonalPage.setEnabled(false);
                spnGenderPersonalPage.setEnabled(false);
                edtPhonePersonalPage.setEnabled(false);
                txtChangeAvtPersonalPage.setText("");

            }
        });
    }

    public void ChangePass(View v){
        edtOldPasswordPersonalPage.setVisibility(View.VISIBLE);
        edtNewPasswordPersonalPage.setVisibility(View.VISIBLE);
        edtRetypeNewPasswordPersonalPage.setVisibility(View.VISIBLE);



        btnChangePasswordPersonalPage.setVisibility(View.VISIBLE);

        btnChangePasswordPersonalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = firebaseUser.getEmail();
                final String newPass = edtNewPasswordPersonalPage.getText().toString();
                final String retypeNewPass = edtRetypeNewPasswordPersonalPage.getText().toString();
                final String oldPass = edtOldPasswordPersonalPage.getText().toString();

                if(oldPass.equals("")==true||newPass.equals("")==true||retypeNewPass.equals("")==true){
                    Toast.makeText(personalPage.this, "Hãy điền đủ thông tin yêu cầu", Toast.LENGTH_SHORT).show();
                }
                else {

                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPass);

                    //kiểm tra mật khẩu cũ có giống vs mk đăng ký không, nếu giống thì updatepassword
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (newPass.equals(retypeNewPass) == true) {
                                    firebaseUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(personalPage.this, "Lỗi!!!Xin thực hiện lại", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(personalPage.this, "Thay đổi mật khẩu thành  công", Toast.LENGTH_SHORT).show();
                                                edtOldPasswordPersonalPage.setVisibility(View.INVISIBLE);
                                                edtNewPasswordPersonalPage.setVisibility(View.INVISIBLE);
                                                edtRetypeNewPasswordPersonalPage.setVisibility(View.INVISIBLE);
                                                btnChangePasswordPersonalPage.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(personalPage.this, "Xác nhận mật khẩu mới không đúng!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(personalPage.this, "Xác thực người dùng thất bại do nhập mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

}
