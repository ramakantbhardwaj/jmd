package com.app.jmd.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterDashBoard;

public class MainActivity2 extends AppCompatActivity{
    Toolbar toolbar;
    TextView tv_header;
    AdapterDashBoard adapterDashBoard;
    RecyclerView rv_adminDashbord;
    LinearLayout linLayout_cball;
    Context context;
   public static TextView tv_laser_current_bal;
    ImageButton tv_logout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context=MainActivity2.this;
        initView();
        setRecycle();
    }
    public void initView(){
        toolbar=findViewById(R.id.toolbar);
        linLayout_cball=findViewById(R.id.linLayout_cball);
        rv_adminDashbord=findViewById(R.id.rv_adminDashbord);
        tv_logout=findViewById(R.id.tv_logout);
        tv_header=findViewById(R.id.tv_header);
        tv_header.setText("MIS");
        tv_header.setVisibility(View.VISIBLE);
        tv_logout.setVisibility(View.VISIBLE);
//        ib_logout=findViewById(R.id.ib_logout);
        tv_laser_current_bal=findViewById(R.id.tv_laser_current_bal);
        String appType= AppPrefrences.getInstance(MainActivity2.this).getDataFromPrefs("app_type");
        toolbar.setTitle(R.string.app_name);
        if (appType.equalsIgnoreCase("S")){
            linLayout_cball.setVisibility(View.GONE);
        }
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Alert!!");
                builder.setMessage("Are you sure to Logout");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent logout = new Intent(context, LoginActivity.class);
                        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        AppPrefrences.getInstance(getApplicationContext()).removePrefs();
                        startActivity(logout);
                        finish();
                    }
                });
                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
//                alertDialog.setIcon(R.drawable.logo);
                alertDialog.show();

            }
        });
    }

    @Override
    public boolean isFinishing() {
        return super.isFinishing();
    }

    public void setRecycle(){
        adapterDashBoard = new AdapterDashBoard(MainActivity2.this);
        rv_adminDashbord.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rv_adminDashbord.setAdapter(adapterDashBoard);
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        super.onBackPressed();
        new AlertDialog.Builder(MainActivity2.this)
                .setTitle("Title")
                .setMessage("Do you really want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(MainActivity2.this);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

//





}