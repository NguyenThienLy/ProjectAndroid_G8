package com.example.designapptest.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginView extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
        , FirebaseAuth.AuthStateListener {

    public static int REQUEST_CODE_LOGIN_WITH_GOOGLE = 99;
    //Biến kiểm tra xem đang login kiểu nào: google, facebook, tài khoản app
    public static int CHECK_TYPE_PROVIDER_LOGIN = 0;
    public static int CODE_PROVIDER_LOGIN_WITH_GOOGLE = 1;
    public static int CODE_PROVIDER_LOGIN_WITH_FACEBOOK = 2;

    public static final String SHARE_UID = "currentUserId";
    public static final String IS_ADMIN = "isAdmin";
    public static final String PREFS_DATA_NAME = "currentUserId";

    ImageButton btnLoginWithGoogle;
    GoogleApiClient apiClient;
    FirebaseAuth firebaseAuth;

    Button btn_signUp;
    Button btn_login;

    EditText edt_username_login;
    EditText edt_password_login;
    TextView tvForgotPassword;

    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;

    DatabaseReference nodeRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        //Khởi tạo firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        //Text Đăng xuất
        firebaseAuth.signOut();
        // Lưu mã user đăng nhập vào app
        sharedPreferences = getSharedPreferences(PREFS_DATA_NAME, MODE_PRIVATE);

        btnLoginWithGoogle = (ImageButton) findViewById(R.id.btnImg_google_login);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_username_login = (EditText) findViewById(R.id.edt_username_login);
        edt_password_login = (EditText) findViewById(R.id.edt_password_login);
        tvForgotPassword = (TextView) findViewById(R.id.tv_forgot_password);

        progressDialog = new ProgressDialog(LoginView.this, R.style.MyProgessDialogStyle);

        btnLoginWithGoogle.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        CreateClientLoginWithGoogle();
        /*ClickForgotPassword();*/
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Thêm sự kiện listenerStateChange
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Xóa sự kiện ListenerStateChange
        firebaseAuth.removeAuthStateListener(this);
    }

    //Tạo client đăng nhập bằng google
    private void CreateClientLoginWithGoogle() {
        //Chú ý chỗ lấy id web này.....:( 8 tiếng để tìm ra lỗi ở đây
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("115253171209-ii7mjp8rj6r0mqsnl5ei2arne611gmpe.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //Tạo ra sign client
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }
    //end Tạo client đăng nhập bằng google

    //Đăng nhập vào tài khoản google
    private void LoginGoogle(GoogleApiClient apiClient) {
        //set code
        CHECK_TYPE_PROVIDER_LOGIN = CODE_PROVIDER_LOGIN_WITH_GOOGLE;
        Intent ILoginGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        //Hiển thị client google để đăng nhập
        startActivityForResult(ILoginGoogle, REQUEST_CODE_LOGIN_WITH_GOOGLE);
    }
    //end Đăng nhập vào tài khoản google

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Kiểm tra nếu resultcode trả về là của client Login with google

        if (requestCode == REQUEST_CODE_LOGIN_WITH_GOOGLE) {


            if (resultCode == RESULT_OK) {

                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                //Lấy ra account google được đăng nhập
                GoogleSignInAccount account = signInResult.getSignInAccount();
                //Lấy ra token của account google
                String tokenID = account.getIdToken();

                CheckLoginFirebase(tokenID);
            }

        }
    }

    //Lấy token id và đăng nhập vào firebase
    private void CheckLoginFirebase(String tokenID) {
        if (CHECK_TYPE_PROVIDER_LOGIN == CODE_PROVIDER_LOGIN_WITH_GOOGLE) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);
            //SignIn to firebase
            firebaseAuth.signInWithCredential(authCredential);
        }
    }
    //end Lấy token id và đăng nhập vào firebase

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void login() {
        String username = edt_username_login.getText().toString();
        String password = edt_password_login.getText().toString();

        if (username.trim().length() == 0 || password.trim().length() == 0) {
            Toast.makeText(LoginView.this, "Tên đăng nhập hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Đang đăng nhập...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginView.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnImg_google_login:
                LoginGoogle(apiClient);
                break;
            case R.id.btn_signUp:
                Intent iSignup = new Intent(LoginView.this, SignUpView.class);
                startActivity(iSignup);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            checkAdminLogin(user.getUid());

            progressDialog.dismiss();
            Toast.makeText(LoginView.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    private void checkAdminLogin(String UID) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isAdmin = false;

                // Lưu lại mã user đăng nhập vào app
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SHARE_UID, UID);

                for(DataSnapshot adminValue : dataSnapshot.getChildren()) {
                    if (adminValue.getKey().equals(UID)) {
                        isAdmin = true;

                        editor.putBoolean(IS_ADMIN, isAdmin);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), adminView.class);
                        startActivity(intent);
                        break;
                    }
                }

                //Load trang chủ
                //Intent intent = new Intent(this, MainActivity.class);
                if(isAdmin == false) {
                    editor.putBoolean(IS_ADMIN, isAdmin);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), Main_Menu.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoot.child("Admins").addListenerForSingleValueEvent(valueEventListener);
    }

    public void ClickForgotPassword(View v) {

        Intent intent = new Intent(LoginView.this, resetPasswordByEmail.class);
        startActivity(intent);

    }
}