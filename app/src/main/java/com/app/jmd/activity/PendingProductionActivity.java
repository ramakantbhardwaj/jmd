package com.app.jmd.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterPendingProduction;
import com.app.jmd.mode.PendingProductionModel;
import com.app.jmd.retrofit.APIClient;
import com.app.jmd.retrofit.API_Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingProductionActivity extends AppCompatActivity {
    RecyclerView rv_pending_order;
    API_Interface api_interface;
    Call<PendingProductionModel> pendingProductionModelCall;
    PendingProductionModel pendingProductionModel;
    AdapterPendingProduction adapterPendingProduction;
    Context context;
    TextView tv_header;
    ImageView iv_back;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_production);
        context = PendingProductionActivity.this;
        rv_pending_order = findViewById(R.id.rv_pending_order);
        tv_header = findViewById(R.id.tv_header);
        iv_back = findViewById(R.id.iv_back);
        tv_header.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        showAllLedgerPartyData();
        tv_header.setText("Pending Production");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity2.class));
                finish();
            }
        });

    }

    private void showAllLedgerPartyData() {
        ProgressDialog pd = new ProgressDialog(PendingProductionActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        pendingProductionModelCall = api_interface.allPendingProduction("", "", "");
        pendingProductionModelCall.enqueue(new Callback<PendingProductionModel>() {
            @Override
            public void onResponse(@NonNull Call<PendingProductionModel> call, @NonNull Response<PendingProductionModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                pendingProductionModel = response.body();
                if (status) {
                    if (!pendingProductionModel.getLstprod().isEmpty()) {
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PendingProductionActivity.this);
                        rv_pending_order.setLayoutManager(linearLayoutManager);
                        adapterPendingProduction = new AdapterPendingProduction(pendingProductionModel, PendingProductionActivity.this);
                        rv_pending_order.setAdapter(adapterPendingProduction);
                        pd.dismiss();
//                        ll_ledger_report.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(PendingProductionActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        rv_pending_order.setAdapter(null);
//                        ll_ledger_report.setVisibility(View.GONE);
                    }
                } else {
                    pd.dismiss();
                    rv_pending_order.setAdapter(null);
//                    ll_ledger_report.setVisibility(View.GONE);
                    Toast.makeText(PendingProductionActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<PendingProductionModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
                rv_pending_order.setAdapter(null);
//                ll_ledger_report.setVisibility(View.GONE);

            }
        });

    }
}

