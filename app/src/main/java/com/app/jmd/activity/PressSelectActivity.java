package com.app.jmd.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterFabricator;
import com.app.jmd.adapterS.AdapterPressman;
import com.app.jmd.mode.FabicatorModel;
import com.app.jmd.mode.LstEmbDetail;
import com.app.jmd.mode.LstEmbDetailUpdated;
import com.app.jmd.mode.SaveFabCustomModel;
import com.app.jmd.mode.SaveFabModel;
import com.app.jmd.mode.SubmitDataProductionModel;
import com.app.jmd.retrofit.APIClient;
import com.app.jmd.retrofit.API_Interface;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PressSelectActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    TextView tv_header;
    ImageView iv_back;
    EditText etRemakr;
    Button btn_upload;
    RecyclerView rv_fabricator;
    API_Interface api_interface;
    Call<FabicatorModel> fabicatorModelCall;
    FabicatorModel fabicatorModel;
    AdapterPressman adapterPressman;
    LinearLayout ll_ledger_report;
    List<LstEmbDetail> addList = new ArrayList<>();
    List<LstEmbDetailUpdated> addListUpdated = new ArrayList<>();
    SaveFabModel fabModel=new SaveFabModel();
    SaveFabCustomModel saveFabCustomModel = new SaveFabCustomModel();
    String myjson,currentDate,pressmanCoder,godownCoder,remarkDatar;
    Call<SubmitDataProductionModel> commonUsedModelCall;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_select);
        context = PressSelectActivity.this;
        tv_header= findViewById(R.id.tv_header);
        iv_back= findViewById(R.id.iv_back);
        tv_header.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        etRemakr = findViewById(R.id.etRemakr);

        btn_upload = findViewById(R.id.btn_upload);
        rv_fabricator = findViewById(R.id.rv_fabricator);
        ll_ledger_report = findViewById(R.id.ll_ledger_report);
        progressDialog = new ProgressDialog(this);
        btn_upload.setOnClickListener(this);

        tv_header.setText("Press Issue");
        currentDate=getCurrentDate();
        Intent intent = getIntent();


        ArrayList<String> receivedList = intent.getStringArrayListExtra("list_lots_no");

        if (receivedList != null) {
            StringBuilder concatenatedItems = new StringBuilder();

            for (String item : receivedList) {
                concatenatedItems.append(item).append(", "); // Add a comma and space after each item
            }

            // Remove the trailing comma and space if there are items
            if (concatenatedItems.length() > 0) {
                concatenatedItems.setLength(concatenatedItems.length() - 2); // Remove last comma and space
            }

            // Convert to String
            String allItems = concatenatedItems.toString();

            showAllLedgerPartyData(allItems);
            Log.d("FabSelectActivity", "Concatenated items: " + allItems);
        } else {
            Log.d("FabSelectActivity", "Received list is null");
        }

        pressmanCoder = intent.getStringExtra("pressman_code");
        godownCoder = intent.getStringExtra("godown_code");
        remarkDatar = intent.getStringExtra("remark_data");


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PressIssueActivity.class));
                finish();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//                lotNo= etLotNo.getText().toString().trim();
//                if (lotNo.isEmpty()){
//                    Toast.makeText(context, "Please enter a lot number", Toast.LENGTH_SHORT).show();
//                }else {

//                }
//                break;
            case R.id.btn_upload:
                validateField();

        }


    }
    private void showAllLedgerPartyData(String lotNo) {
        ProgressDialog pd = new ProgressDialog(PressSelectActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        fabicatorModelCall = api_interface.getPressIssue(lotNo,"");
        fabicatorModelCall.enqueue(new Callback<FabicatorModel>() {
            @Override
            public void onResponse(@NonNull Call<FabicatorModel> call, @NonNull Response<FabicatorModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                fabicatorModel = response.body();
                if (status) {
                    if (!fabicatorModel.getLstlot().isEmpty()) {
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PressSelectActivity.this);
                        rv_fabricator.setLayoutManager(linearLayoutManager);
                        adapterPressman = new AdapterPressman(addList, PressSelectActivity.this,true);
                        rv_fabricator.setAdapter(adapterPressman);
                        for (int i=0;i<fabicatorModel.getLstlot().size();i++){
                            addList.add(new LstEmbDetail(
                                    "",
                                    fabicatorModel.getLstlot().get(i).getDesigncode(),
                                    fabicatorModel.getLstlot().get(i).getSizecode(),
                                    fabicatorModel.getLstlot().get(i).getColourcode(),
                                    fabicatorModel.getLstlot().get(i).getLotno(),
                                    fabicatorModel.getLstlot().get(i).getQty(),
                                    fabicatorModel.getLstlot().get(i).getDesignname(),
                                    fabicatorModel.getLstlot().get(i).getSizename(),
                                    fabicatorModel.getLstlot().get(i).getDesignpath()
                            ));
                            addListUpdated.add(new LstEmbDetailUpdated(
                                    "",
                                    fabicatorModel.getLstlot().get(i).getDesigncode(),
                                    fabicatorModel.getLstlot().get(i).getSizecode(),
                                    fabicatorModel.getLstlot().get(i).getColourcode(),
                                    fabicatorModel.getLstlot().get(i).getLotno(),
                                    fabicatorModel.getLstlot().get(i).getQty()
                            ));
                        }

                        pd.dismiss();
                        ll_ledger_report.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(PressSelectActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        rv_fabricator.setAdapter(null);
                        ll_ledger_report.setVisibility(View.GONE);
                    }
                } else {
                    pd.dismiss();
                    rv_fabricator.setAdapter(null);
                    ll_ledger_report.setVisibility(View.GONE);
                    Toast.makeText(PressSelectActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<FabicatorModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
                rv_fabricator.setAdapter(null);
//                ll_ledger_report.setVisibility(View.GONE);

            }
        });

    }


    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

    private void validateField()  {
        boolean cancel= false;
//        strRemark= etRemakr.getText().toString().trim();



//        if(spn_master.getSelectedItem()==("Select Master"))
//        {
//            AppPrefrences.alertBox("Please choose Master", context);
//            cancel = true;
//            return;
//        }
//        if(spn_manager.getSelectedItem()==("Select Manager"))
//        {
//            AppPrefrences.alertBox("Please choose Manager", context);
//            cancel = true;
//            return;
//        }


        if (!cancel) {
            hideInputSoft();

//            for(int i=0;i<fabicatorModel.getLstlot().size();i++) {
//                tempSizeList.add(new CustomModelFabric());
//
//            }
            try {
                saveFabCustomModel.setVchdate(currentDate);
                saveFabCustomModel.setPartycode(pressmanCoder);
                saveFabCustomModel.setGodowncode(godownCoder);
                saveFabCustomModel.setRemark(remarkDatar);
                saveFabCustomModel.setSlno("");
                saveFabCustomModel.setLstEmbDetail(addListUpdated);
//                tempSizeList

                Gson gson = new Gson();
                myjson = gson.toJson(saveFabCustomModel);
                sendAnswer(myjson);

            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }
    private void sendAnswer(String json) {

//        SaveFabModel submitpackPojo = new SaveFabModel();
        SaveFabCustomModel submitpackPojo= new SaveFabCustomModel();

        try {
            JSONObject jsonObject = new JSONObject(json);
            submitpackPojo.setPartycode(jsonObject.getString("partycode"));
//            submitpackPojo.setMastercode(jsonObject.getString("mastercode"));
            submitpackPojo.setGodowncode(jsonObject.getString("godowncode"));
            submitpackPojo.setRemark(jsonObject.getString("remark"));
            submitpackPojo.setVchdate(jsonObject.getString("vchdate"));
            submitpackPojo.setSlno(jsonObject.getString("slno"));
            submitpackPojo.setLstEmbDetailNew(jsonObject.getString("lstEmbDetail"));
            JSONArray j1 = jsonObject.getJSONArray("lstEmbDetail");
            if (j1 != null && j1.length() > 0) {
                List<LstEmbDetailUpdated> aist = new ArrayList<>();
                for (int i = 0; i < j1.length(); i++) {
                    JSONObject ja = j1.getJSONObject(i);
                    LstEmbDetailUpdated ans = new LstEmbDetailUpdated();
                    ans.setDesigncode(ja.getString("designcode"));
                    ans.setColourcode(ja.getString("colourcode"));
                    ans.setSizecode(ja.getString("sizecode"));
                    ans.setLotno(ja.getString("lotno"));
                    ans.setQty(ja.getString("qty"));
//                    ans.setQty(ja.getString("sizename"));
//                    ans.setQty(ja.getString("designname"));
                    ans.setDetslno(ja.getString("detslno"));
                    aist.add(ans);
                }
                submitpackPojo.setLstEmbDetail(aist);
            }

        } catch (JSONException e) {
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        API_Interface api_interface = APIClient.getService();
        commonUsedModelCall=api_interface.saveDetailsPressIssue(submitpackPojo);
        commonUsedModelCall.enqueue(new Callback<SubmitDataProductionModel>() {
            @Override
            public void onResponse(@NonNull Call<SubmitDataProductionModel> call, @NonNull Response<SubmitDataProductionModel> response) {
                assert response.body() != null;
                String msg = response.body().getMessage();
                boolean status = response.body().getStatus();
                progressDialog.dismiss();
                if (status){
                    Toast.makeText(PressSelectActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PressIssueActivity
                            .class);
                    finish(); // Finish the current activity
                    startActivity(intent);
                }else {
                    AppPrefrences.alertBox(msg,context);
                }
            }

            @Override
            public void onFailure(Call<SubmitDataProductionModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void hideInputSoft() {

        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }
}