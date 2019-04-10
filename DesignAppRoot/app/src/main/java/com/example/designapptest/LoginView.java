package com.example.designapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginView extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
,FirebaseAuth.AuthStateListener{

    public static int REQUEST_CODE_LOGIN_WITH_GOOGLE=99;
    //Biến kiểm tra xem đang login kiểu nào: google, facebook, tài khoản app
    public static int CHECK_TYPE_PROVIDER_LOGIN=0;
    public static int CODE_PROVIDER_LOGIN_WITH_GOOGLE=1;
    public static int CODE_PROVIDER_LOGIN_WITH_FACEBOOK=2;

    ImageButton btnLoginWithGoogle;
    GoogleApiClient apiClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        //Khởi tạo firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        //Text Đăng xuất
        firebaseAuth.signOut();

        btnLoginWithGoogle = (ImageButton)findViewById(R.id.btnImg_google_login);
        btnLoginWithGoogle.setOnClickListener(this);

        CreateClientLoginWithGoogle();
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
    private void CreateClientLoginWithGoogle(){
        //Chú ý chỗ lấy id web này.....:( 8 tiếng để tìm ra lỗi ở đây
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("115253171209-ii7mjp8rj6r0mqsnl5ei2arne611gmpe.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //Tạo ra sign client
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
    }
    //end Tạo client đăng nhập bằng google

    //Đăng nhập vào tài khoản google
    private void LoginGoogle(GoogleApiClient apiClient){
        //set code
        CHECK_TYPE_PROVIDER_LOGIN=CODE_PROVIDER_LOGIN_WITH_GOOGLE;
        Intent ILoginGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        //Hiển thị client google để đăng nhập
        startActivityForResult(ILoginGoogle,REQUEST_CODE_LOGIN_WITH_GOOGLE);
    }
    //end Đăng nhập vào tài khoản google

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Kiểm tra nếu resultcode trả về là của client Login with google
        Toast.makeText(this,"khong chay vao ham nay",Toast.LENGTH_LONG).show();
        if(requestCode==REQUEST_CODE_LOGIN_WITH_GOOGLE){
            Toast.makeText(this,"co chay vao ham nay",Toast.LENGTH_LONG).show();

            if(resultCode == RESULT_OK){
                Toast.makeText(this,"ham nay thi khong chay sao",Toast.LENGTH_LONG).show();
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                //Lấy ra account google được đăng nhập
                GoogleSignInAccount account = signInResult.getSignInAccount();
                //Lấy ra token của account google
                String tokenID = account.getIdToken();
                Toast.makeText(this,tokenID.toString(),Toast.LENGTH_LONG).show();
                CheckLoginFirebase(tokenID);
            }
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Toast.makeText(this,"dang nhap thanh cong o day",Toast.LENGTH_LONG).show();
//                //firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                Toast.makeText(this,"dang nhap that bai o day",Toast.LENGTH_LONG).show();
//                // Google Sign In failed, update UI appropriately
//                Log.w("loginfalse", "Google sign in failed", e);
//                // ...
//            }

        }
    }

    //Lấy token id và đăng nhập vào firebase
    private void CheckLoginFirebase(String tokenID){
        if(CHECK_TYPE_PROVIDER_LOGIN == CODE_PROVIDER_LOGIN_WITH_GOOGLE){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            //SignIn to firebase
            firebaseAuth.signInWithCredential(authCredential);
        }
    }
    //end Lấy token id và đăng nhập vào firebase

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnImg_google_login:
                    LoginGoogle(apiClient);
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){
            //Load trang chủ
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{

        }
    }
}
