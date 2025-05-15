package com.app.jmd.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterFabricator;
import com.app.jmd.adapterS.FilterFabricatorAdapter;
import com.app.jmd.adapterS.GoDownFilterAdapter;
import com.app.jmd.adapterS.MasterFilterAdapter;
import com.app.jmd.interfaces.FilterSpinner;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.interfaces.UpdateQtyInterface;
//import com.app.jmd.mode.CustomModelFabric;
import com.app.jmd.mode.FabicatorModel;
import com.app.jmd.mode.GoDownModel;
import com.app.jmd.mode.LstEmbDetail;
import com.app.jmd.mode.LstEmbDetailUpdated;
import com.app.jmd.mode.LstGodown;
import com.app.jmd.mode.LstParty;
import com.app.jmd.mode.Lstlot;
import com.app.jmd.mode.MasterMainModel;
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

public class FabSelectActivity extends AppCompatActivity implements View.OnClickListener, FilterSpinner, MyItemClickListener, UpdateQtyInterface {
    Button getLotNo;
    EditText etLotNo;
    RecyclerView rv_fabricator;
    API_Interface api_interface;
    Call<FabicatorModel> fabicatorModelCall;
    FabicatorModel fabicatorModel;
    AdapterFabricator adapterFabricator;
    Context context;
    TextView tv_header;
    ImageView iv_back;
    LinearLayout ll_ledger_report;
    FilterFabricatorAdapter filterFabricatorAdapter;
    TextView tv_fabric_name,tv_master_name,tv_godown_name;
    RelativeLayout relaLayoutFabricr,relaLayoutMaster,relaLayoutGodown;
    String customerCode="";
    String customerName="";
    String masterCode="";
    String masterName="";
    String goDownCode="";
    String goDownName="", currentDate,myjson,strRemark;
    List<LstParty> customerPartyList = new ArrayList<>();
    List<LstParty> masterPartyList = new ArrayList<>();
    List<LstGodown> goDownPartyList = new ArrayList<>();
    List<FabicatorModel> fabricatorList = new ArrayList<>();
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    MasterMainModel masterMainModel;
    GoDownModel goDownModel;
    MasterFilterAdapter masterFilterAdapter;
    GoDownFilterAdapter goDownFilterAdapter;
    Call<SubmitDataProductionModel> commonUsedModelCall;
    EditText etRemakr;
    Button btn_upload;
    List<Lstlot> lstEmbDetailList=new ArrayList<>();
    SaveFabModel fabModel=new SaveFabModel();
    SaveFabCustomModel saveFabCustomModel = new SaveFabCustomModel();
    List<LstEmbDetail> addList = new ArrayList<>();
    List<LstEmbDetailUpdated> addListUpdated = new ArrayList<>();
    private boolean isSelectionControlled = false;
    String qty="";
    int pos;
    String fabricatorCoder,masterCoder,godownCoder,remarkDatar;

    // Retrieve the ArrayList using the same key

//    List<CustomModelFabric> tempSizeList= new ArrayList<>();



    @SuppressLint({"MissingInflatedId", "SuspiciousIndentation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_select);
        context= FabSelectActivity.this;
        tv_fabric_name = findViewById(R.id.tv_fabric_name);
        tv_master_name = findViewById(R.id.tv_master_name);
        tv_godown_name = findViewById(R.id.tv_godown_name);
        btn_upload = findViewById(R.id.btn_upload);
        relaLayoutGodown = findViewById(R.id.relaLayoutGodown);
        relaLayoutFabricr = findViewById(R.id.relaLayoutFabricr);
        relaLayoutMaster = findViewById(R.id.relaLayoutMaster);
        etRemakr = findViewById(R.id.etRemakr);
        tv_header = findViewById(R.id.tv_header);
        ll_ledger_report = findViewById(R.id.ll_ledger_report);
        iv_back = findViewById(R.id.iv_back);
        rv_fabricator = findViewById(R.id.rv_fabricator);
        tv_header.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        tv_header.setText("Fabricator Issue");
        btn_upload.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
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

         fabricatorCoder = intent.getStringExtra("fabricator_code");
         masterCoder = intent.getStringExtra("master_code");
         godownCoder = intent.getStringExtra("godown_code");
         remarkDatar = intent.getStringExtra("remark_data");

//        // Check if any of the values are null
//        if (fabricatorCoder == null || masterCoder == null || godownCoder == null || remarkDatar == null) {
//            Log.d("ReceivedData", "One or more values are null");
//        } else {
            // All values are non-null
            Log.d("ReceivedData", "Fabricator Code: " + fabricatorCoder);
            Log.d("ReceivedData", "Master Code: " + masterCoder);
            Log.d("ReceivedData", "Godown Code: " + godownCoder);
            Log.d("ReceivedData", "Remark Data: " + remarkDatar);
//        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, FabricatorIssueActivity.class));
                finish();
            }
        });
        showAllFabList();
        showAllMasterList();
        showAllGoDownList();
        relaLayoutFabricr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FabSelectActivity.this);
                builder.setTitle("Select Fabricator");
                LayoutInflater inflater = getLayoutInflater();
                View convertView = inflater.inflate(R.layout.alert_dialog_spinner_filter_layout, null);
                builder.setView(convertView);
                SearchView searchView = convertView.findViewById(R.id.searchView);
                searchView.setIconifiedByDefault(false);
                RecyclerView list = convertView.findViewById(R.id.recyclerViewCity);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filterFabricatorAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                filterFabricatorAdapter = new FilterFabricatorAdapter(customerPartyList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(filterFabricatorAdapter);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filterFabricatorAdapter.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filterFabricatorAdapter.getFilter().filter("");
                        customerCode = "";
                        customerName = "Select Fabricator";
                        tv_fabric_name.setText(customerName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        relaLayoutMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FabSelectActivity.this);
                builder.setTitle("Select Master");
                LayoutInflater inflater = getLayoutInflater();
                View convertView = inflater.inflate(R.layout.alert_dialog_spinner_filter_layout, null);
                builder.setView(convertView);
                SearchView searchView = convertView.findViewById(R.id.searchView);
                searchView.setIconifiedByDefault(false);
                RecyclerView list = convertView.findViewById(R.id.recyclerViewCity);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        masterFilterAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                masterFilterAdapter = new MasterFilterAdapter(masterPartyList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(masterFilterAdapter);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        masterFilterAdapter.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        masterFilterAdapter.getFilter().filter("");
                        masterCode = "";
                        masterName = "Select Master";
                        tv_master_name.setText(masterName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        relaLayoutGodown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FabSelectActivity.this);
                builder.setTitle("Select Go Down");
                LayoutInflater inflater = getLayoutInflater();
                View convertView = inflater.inflate(R.layout.alert_dialog_spinner_filter_layout, null);
                builder.setView(convertView);
                SearchView searchView = convertView.findViewById(R.id.searchView);
                searchView.setIconifiedByDefault(false);
                RecyclerView list = convertView.findViewById(R.id.recyclerViewCity);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        goDownFilterAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                goDownFilterAdapter = new GoDownFilterAdapter(goDownPartyList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(goDownFilterAdapter);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goDownFilterAdapter.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goDownFilterAdapter.getFilter().filter("");
                        goDownCode= "";
                        goDownName = "Select GoDown";
                        tv_godown_name.setText(goDownName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
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
        ProgressDialog pd = new ProgressDialog(FabSelectActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        fabicatorModelCall = api_interface.getFabricatorIssue(lotNo,"");
        fabicatorModelCall.enqueue(new Callback<FabicatorModel>() {
            @Override
            public void onResponse(@NonNull Call<FabicatorModel> call, @NonNull Response<FabicatorModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                fabicatorModel = response.body();
                if (status) {
                    if (!fabicatorModel.getLstlot().isEmpty()) {
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FabSelectActivity.this);
                        rv_fabricator.setLayoutManager(linearLayoutManager);
                        adapterFabricator = new AdapterFabricator(addList, FabSelectActivity.this,true);
                        rv_fabricator.setAdapter(adapterFabricator);
//                        tv_header.setText("Fabricator Issue"+"("+fabicatorModel.getLstlot().size()+")");

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
                        Toast.makeText(FabSelectActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        rv_fabricator.setAdapter(null);
                        ll_ledger_report.setVisibility(View.GONE);
                    }
                } else {
                    pd.dismiss();
                    rv_fabricator.setAdapter(null);
                    ll_ledger_report.setVisibility(View.GONE);
                    Toast.makeText(FabSelectActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

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

    private void showAllFabList() {

        try{
            api_interface = APIClient.getService();
            Call<MasterMainModel> call = api_interface.getMasterData("03","");
            progressDialog.show();
            call.enqueue(new Callback<MasterMainModel>() {
                @Override
                public void onResponse(@NonNull Call<MasterMainModel> call, @NonNull Response<MasterMainModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    masterMainModel = response.body();

                    if (status && masterMainModel.getLstParty()!=null) {
                        customerPartyList.clear();
                        customerPartyList.addAll(masterMainModel.getLstParty());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabSelectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(FabSelectActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showAllMasterList() {

        try{
            api_interface = APIClient.getService();
            Call<MasterMainModel> call = api_interface.getMasterData("03","");
            progressDialog.show();
            call.enqueue(new Callback<MasterMainModel>() {
                @Override
                public void onResponse(@NonNull Call<MasterMainModel> call, @NonNull Response<MasterMainModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    masterMainModel = response.body();

                    if (status && masterMainModel.getLstParty()!=null) {
                        masterPartyList.clear();
                        masterPartyList.addAll(masterMainModel.getLstParty());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabSelectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(FabSelectActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showAllGoDownList() {

        try{
            api_interface = APIClient.getService();
            Call<GoDownModel> call = api_interface.getGoDownData("","");
            progressDialog.show();
            call.enqueue(new Callback<GoDownModel>() {
                @Override
                public void onResponse(@NonNull Call<GoDownModel> call, @NonNull Response<GoDownModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    goDownModel = response.body();

                    if (status && goDownModel.getLstGodown()!=null) {
                        goDownPartyList.clear();
                        goDownPartyList.addAll(goDownModel.getLstGodown());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<GoDownModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabSelectActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(FabSelectActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                saveFabCustomModel.setMastercode(masterCoder);
                saveFabCustomModel.setVchdate(currentDate);
                saveFabCustomModel.setPartycode(fabricatorCoder);
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

//        SaveFabCustomModel submitpackPojo = new SaveFabCustomModel();
        SaveFabCustomModel submitpackPojo = new SaveFabCustomModel();

        try {
            JSONObject jsonObject = new JSONObject(json);
            submitpackPojo.setPartycode(jsonObject.getString("partycode"));
            submitpackPojo.setMastercode(jsonObject.getString("mastercode"));
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
        commonUsedModelCall=api_interface.saveDetailsFabIssueDetails(submitpackPojo);
        commonUsedModelCall.enqueue(new Callback<SubmitDataProductionModel>() {
            @Override
            public void onResponse(@NonNull Call<SubmitDataProductionModel> call, @NonNull Response<SubmitDataProductionModel> response) {
                assert response.body() != null;
                String msg = response.body().getMessage();
                boolean status = response.body().getStatus();
                progressDialog.dismiss();
                if (status){
                    Toast.makeText(FabSelectActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, FabricatorIssueActivity.class);
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

    @Override
    public void onItemClick(int position) {
//        posit=position;

    }

    @Override
    public void customerFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            goDownCode = partyCode;
            goDownName = partyName;
            goDownFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_godown_name.setText(partyName);

        }

    }

    @Override
    public void supplierFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            customerCode = partyCode;
            customerName = partyName;
            filterFabricatorAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_fabric_name.setText(partyName);

        }


    }

    @Override
    public void marketerFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            masterCode = partyCode;
            masterName = partyName;
            masterFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_master_name.setText(partyName);

        }



    }

    @Override
    public void sizeFilter(String partyCode, String partyName) {

    }
//    private void updateCartList(int index, String qty) {
//        if (index >= 0 && index < tempSizeList.size()) {
//            CustomModelFabric item = tempSizeList.get(index);
//            item.setQty(qty);
//        }
//    }

    @Override
    public void updateQty(int position, String newText) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(newText);
        qty = stringBuilder.toString();
        pos = position;
//        updateCartList(position,qty);
//        String amount = String.valueOf(Math.round(Float.parseFloat((rate))) * Integer.parseInt(qty));
//        updateCartList(position,qty,amount);
//        addList.add(new LstEmbDetail("", fabicatorModel.getLstlot().get(position).getDesigncode()
//                , fabicatorModel.getLstlot().get(position).getSizecode(),
//                fabicatorModel.getLstlot().get(position).getColourcode(),
//                fabicatorModel.getLstlot().get(position).getLotno(),
//                newText));
        Toast.makeText(context, ""+String.valueOf(pos)+"  "+qty, Toast.LENGTH_SHORT).show();


    }
}