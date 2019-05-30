package com.example.designapptest.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.designapptest.Controller.UserController;
import com.example.designapptest.Model.UserModel;
import com.example.designapptest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpView extends AppCompatActivity implements View.OnClickListener {
    Button btn_signup;
    FirebaseAuth firebaseAuth;
    EditText edt_email_signUp, edt_password_signUp, edt_retype_password_signUp, edt_name_signUp, edt_phone_signUp;
    RadioButton rad_gender_female_signUp, rad_gender_male_signUp;
    ProgressDialog progressDialog;

    UserController userController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_view);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup = (Button) findViewById(R.id.btn_signUp);
        edt_email_signUp = (EditText) findViewById(R.id.edt_email_signUp);
        edt_password_signUp = (EditText) findViewById(R.id.edt_password_signUp);
        edt_retype_password_signUp = (EditText) findViewById(R.id.edt_retype_password_signUp);
        edt_name_signUp = (EditText) findViewById(R.id.edt_name_signUp);
        edt_phone_signUp = (EditText) findViewById(R.id.edt_phone_signUp);
        rad_gender_female_signUp = (RadioButton) findViewById(R.id.rad_gender_female_signUp);
        rad_gender_male_signUp = (RadioButton) findViewById(R.id.rad_gender_male_signUp);

        progressDialog = new ProgressDialog(SignUpView.this, R.style.MyProgessDialogStyle);
        userController = new UserController(this);

        btn_signup.setOnClickListener(this);
        rad_gender_female_signUp.setOnClickListener(this);
        rad_gender_male_signUp.setOnClickListener(this);
    }

    private void signUp() {
        String email = edt_email_signUp.getText().toString();
        String password = edt_password_signUp.getText().toString();
        String passwordRetype = edt_retype_password_signUp.getText().toString();
        final String name = edt_name_signUp.getText().toString();
        final String avatar = "user.png";
        final Boolean owner = false;
        final String phone = edt_phone_signUp.getText().toString();
        Boolean gender = true;
        if(rad_gender_female_signUp.isChecked()) {
            gender = false;
        }

        final Boolean genderUser = gender;
        String error = "Vui lòng nhập";
        if(email.trim().length() == 0) {
            error += " email";
            Toast.makeText(SignUpView.this, error, Toast.LENGTH_SHORT).show();
        } else if (password.trim().length() == 0) {
            error += " mật khẩu";
            Toast.makeText(SignUpView.this, error, Toast.LENGTH_SHORT).show();
        } else if (!passwordRetype.equals(password)) {
            Toast.makeText(SignUpView.this, "Mật khẩu không khớp. Vui lòng nhập lại!", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setMessage("Đang tạo tài khoản...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        UserModel userModel = new UserModel();
                        userModel.setName(name);
                        userModel.setEmail(email);
                        userModel.setAvatar(avatar);
                        userModel.setGender(genderUser);
                        userModel.setOwner(owner);
                        userModel.setPhoneNumber(phone);

                        String uid = task.getResult().getUser().getUid();

                        userController.addUser(userModel, uid);

                        progressDialog.dismiss();
                        Toast.makeText(SignUpView.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent iSignin = new Intent(SignUpView.this, LoginView.class);
                        startActivity(iSignin);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpView.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_signUp:
                signUp();
                break;
            case R.id.rad_gender_female_signUp:
                rad_gender_male_signUp.setChecked(false);
                break;
            case R.id.rad_gender_male_signUp:
                rad_gender_female_signUp.setChecked(false);
                break;
        }
    }
}
