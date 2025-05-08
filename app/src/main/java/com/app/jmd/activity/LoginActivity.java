package com.app.jmd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.app.jmd.ConnectionUtility;
import com.app.jmd.R;
import com.app.jmd.mode.LoginMainModel;
import com.app.jmd.network.NetworkUtil;
import com.app.jmd.retrofit.APIClient;
import com.app.jmd.retrofit.API_Interface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button btn_sign_in;
    ProgressDialog progressDialog;
    TextInputEditText etMobile,etPassword;
    LoginMainModel loginMainModel;
    ConnectionUtility connectionUtility;
     API_Interface apiInterface;
    Call<LoginMainModel> call1;
    TextInputLayout tblPhone,tblPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        tblPhone = findViewById(R.id.tblPhone);
        tblPass = findViewById(R.id.tblPass);
        progressDialog=new ProgressDialog(this);
        connectionUtility=new ConnectionUtility(this);
        NetworkUtil.getConnectivityStatus(LoginActivity.this);
        tblPhone.setBoxStrokeColor(getResources().getColor(R.color.black));
        tblPass.setBoxStrokeColor(getResources().getColor(R.color.black));

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etMobile.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "mobile no can' t be empty", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "password can't be empty", Toast.LENGTH_SHORT).show();
                }
               else {
                    if (connectionUtility.isConnectingToInternet()&&validationMoNo(etMobile.getText().toString())) {
//                        if(etMobile.getText().toString().equalsIgnoreCase("9718360460")){
//                            AppPrefrences.getInstance(getApplicationContext()).saveDataToPrefs("app_type","A");
//                        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                        getOtpApiRetrofit();
//                        startActivity(intent);
//                        }else {
//
//                            getOtpApiRetrofit();
//                        }
//                        AppPrefrences.getInstance(getApplicationContext()).saveDataToPrefs("app_type","A");
//                        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Please connect to working internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validationMoNo(etMobile.getText().toString())){
                    btn_sign_in.setEnabled(true);
                }else {
                    btn_sign_in.setEnabled(false);
                    etMobile.setError("Invalid Mobile No");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
        private void getOtpApiRetrofit () {
            progressDialog.show();
            apiInterface = APIClient.getService();
            call1 = apiInterface.getLogin(etMobile.getText().toString(),etPassword.getText().toString());

            call1.enqueue(new Callback<LoginMainModel>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(@NonNull Call<LoginMainModel> call, @NonNull Response<LoginMainModel> response) {
                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    loginMainModel = response.body();
                    progressDialog.dismiss();
                    if (status) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                        startActivity(intent);
                            finish();
                        Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        boolean validationMoNo(String mobile){
            Pattern p=Pattern.compile("[6-9][0-9]{9}");
            Matcher m=p.matcher(mobile);
            return m.matches();
        }

}