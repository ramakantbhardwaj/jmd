package com.app.jmd.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.DesignFilterAdapter;
import com.app.jmd.adapterS.DesignSizeAdapterPro;
import com.app.jmd.adapterS.ManagerFilterAdapter;
import com.app.jmd.adapterS.MasterFilterAdapter;
import com.app.jmd.adapterS.SizeFilterAdapter;
import com.app.jmd.interfaces.FilterSpinner;
import com.app.jmd.mode.DataMainModel;
import com.app.jmd.mode.DesignDataModel;
import com.app.jmd.mode.LstDesign;
import com.app.jmd.mode.LstParty;
import com.app.jmd.mode.LstProductionDetail;
import com.app.jmd.mode.LstSize;
import com.app.jmd.mode.MasterMainModel;
import com.app.jmd.mode.SizeDataModel;
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

public class ProductionActivity extends AppCompatActivity implements FilterSpinner {
    DataMainModel dataMainModel = new DataMainModel();
    API_Interface api_interface;
    ProgressDialog progressDialog;
    MasterMainModel masterMainModel;
    DesignDataModel designDataModel;
    SizeDataModel sizeDataModel;
    List<LstProductionDetail> addList = new ArrayList<>();
    Button btnAdd;
    EditText etQuantity,etRemakr;
    LinearLayoutCompat ll_view_record;
    Context context;
    String myjson, currentDate,strRemark;
    Call<SubmitDataProductionModel> commonUsedModelCall;
    Button btn_upload;
    ImageView iv_back;
    TextView tv_header;
    ////////////////////////////////spnr/////////////
    RelativeLayout relaLayoutCustomer,relaLayoutManager,relaLayoutDesign,relaLayoutSize;
    MasterFilterAdapter masterFilterAdapter;
    ManagerFilterAdapter managerFilterAdapter;
    DesignFilterAdapter designFilterAdapter;
    SizeFilterAdapter sizeFilterAdapter;
    List<LstParty> customerPartyList = new ArrayList<>();
    List<LstParty> customerCodeList = new ArrayList<>();
    List<LstParty> managerPartyList = new ArrayList<>();
    List<LstParty> managerCodeList = new ArrayList<>();
    List<LstDesign> designNameList = new ArrayList<>();
    List<LstDesign> designCodeList = new ArrayList<>();
    List<LstSize> sizeNameList = new ArrayList<>();
    List<LstSize> sizeCodeList = new ArrayList<>();
    TextView tv_master_party_name,tv_mangerPart_name,tv_design_name,tv_Size_name;
    AlertDialog alertDialog;

    String customerCode="";
    String customerName="";
    String managerSpoinnerCode="";
    String managerSpinerName="";
    String designCode="";
    String designName="";
    String sizeCode="";
    String sizeName="";


//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);
        context = ProductionActivity.this;
        tv_master_party_name = findViewById(R.id.tv_customer_name);
        tv_mangerPart_name = findViewById(R.id.tv_mangerPart_name);
        relaLayoutCustomer = findViewById(R.id.relaLayoutCustomer);
        relaLayoutManager = findViewById(R.id.relaLayoutManager);
        relaLayoutDesign = findViewById(R.id.relaLayoutDesign);
        tv_design_name = findViewById(R.id.tv_design_name);
        etQuantity = findViewById(R.id.etQuantity);
        relaLayoutSize = findViewById(R.id.relaLayoutSize);
        tv_Size_name = findViewById(R.id.tv_Size_name);
        btnAdd = findViewById(R.id.btnAdd);
        etRemakr = findViewById(R.id.etRemakr);
        btn_upload = findViewById(R.id.btn_upload);
        ll_view_record = findViewById(R.id.ll_view_record);
        iv_back = findViewById(R.id.iv_back);
        tv_header = findViewById(R.id.tv_header);
        iv_back.setVisibility(View.VISIBLE);
        tv_header.setVisibility(View.VISIBLE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        showAllMasterList();
        showAllMaanagerList();
        showAllDesignList();
        showAllSizeList();
        relaLayoutCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductionActivity.this);
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
                masterFilterAdapter = new MasterFilterAdapter(customerPartyList, context);
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
                        customerCode = "";
                        customerName = "Select Master";
                        tv_master_party_name.setText(customerName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        relaLayoutManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductionActivity.this);
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
                        managerFilterAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                managerFilterAdapter = new ManagerFilterAdapter(managerPartyList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(managerFilterAdapter);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        managerFilterAdapter.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        managerFilterAdapter.getFilter().filter("");
                        managerSpoinnerCode = "";
                        managerSpinerName = "Select Manager";
                        tv_mangerPart_name.setText(managerSpinerName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        relaLayoutDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductionActivity.this);
                builder.setTitle("Select Design");
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
                        designFilterAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                designFilterAdapter = new DesignFilterAdapter(designNameList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(designFilterAdapter);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        designFilterAdapter.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        designFilterAdapter.getFilter().filter("");
                        designCode = "";
                        designName = "Select Design";
                        tv_design_name.setText(designName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
        relaLayoutSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductionActivity.this);
                builder.setTitle("Select Design");
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
                        sizeFilterAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                list.setLayoutManager(new LinearLayoutManager(context));
                list.setHasFixedSize(true);
                sizeFilterAdapter = new SizeFilterAdapter(sizeNameList, context);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                list.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
                        layoutManager.getOrientation());
                list.addItemDecoration(dividerItemDecoration);
                list.setAdapter(sizeFilterAdapter);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sizeFilterAdapter.getFilter().filter("");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sizeFilterAdapter.getFilter().filter("");
                        sizeCode = "";
                        sizeName = "Select Design";
                        tv_Size_name.setText(sizeName);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String designCodest = designCode;
                String sizecodest = sizeCode;
                String sizeNamest = sizeName;
                String designNameSt = designName;
                String qty = etQuantity.getText().toString();
                if (!designCodest.isEmpty() && !sizecodest.isEmpty() && !qty.isEmpty() && !sizeNamest.isEmpty() && !designNameSt.isEmpty()) {
                    addList.add(new LstProductionDetail("", designCodest, designNameSt, sizecodest, sizeNamest, qty));
                    etQuantity.setText("");
                    if (addList.size() > 0) {
                        ll_view_record.setVisibility(View.VISIBLE);
                    } else {
                        ll_view_record.setVisibility(View.VISIBLE);
                    }
                    viewRecord();
                } else {
                    AppPrefrences.alertBox("Please Enter all field", ProductionActivity.this);
                }
            }
        });
        currentDate = getCurrentDate();
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateField();
            }
        });
        tv_header.setText("Production Planning");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity2.class));
                finish();
            }
        });
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
                        customerPartyList.clear();
                        customerCodeList.clear();
                        customerPartyList.addAll(masterMainModel.getLstParty());
                        customerCodeList.addAll(masterMainModel.getLstParty());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(ProductionActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(ProductionActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showAllMaanagerList() {

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
                        managerPartyList.clear();
                        managerCodeList.clear();
                        managerPartyList.addAll(masterMainModel.getLstParty());
                        managerCodeList.addAll(masterMainModel.getLstParty());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(ProductionActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(ProductionActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showAllDesignList() {

        try{
            api_interface = APIClient.getService();
            Call<DesignDataModel> call = api_interface.getDesignData("","");
            progressDialog.show();
            call.enqueue(new Callback<DesignDataModel>() {
                @Override
                public void onResponse(@NonNull Call<DesignDataModel> call, @NonNull Response<DesignDataModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    designDataModel = response.body();

                    if (status && designDataModel.getLstDesign()!=null) {
                        designNameList.clear();
                        designCodeList.clear();
                        designNameList.addAll(designDataModel.getLstDesign());
                        designCodeList.addAll(designDataModel.getLstDesign());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<DesignDataModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(ProductionActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(ProductionActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showAllSizeList() {

        try{
            api_interface = APIClient.getService();
            Call<SizeDataModel> call = api_interface.getSizeData("","");
            progressDialog.show();
            call.enqueue(new Callback<SizeDataModel>() {
                @Override
                public void onResponse(@NonNull Call<SizeDataModel> call, @NonNull Response<SizeDataModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    sizeDataModel = response.body();

                    if (status && sizeDataModel.getLstSize()!=null) {
                        sizeNameList.clear();
                        sizeCodeList.clear();
                        sizeNameList.addAll(sizeDataModel.getLstSize());
                        sizeCodeList.addAll(sizeDataModel.getLstSize());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<SizeDataModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(ProductionActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(ProductionActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private void viewRecord() {

        RecyclerView recycler_view_item = findViewById(R.id.recycler_view_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductionActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_item.setLayoutManager(layoutManager);
        DesignSizeAdapterPro designSizeAdapter = new DesignSizeAdapterPro(addList, ProductionActivity.this);
        recycler_view_item.setAdapter(designSizeAdapter);
    }




    private void hideInputSoft() {

        View view1 = this.getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

    private void validateField()  {
        boolean cancel= false;
        strRemark= etRemakr.getText().toString().trim();


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
            try {
                dataMainModel.setMastercode(customerCode);
                dataMainModel.setVchdate(currentDate);
                dataMainModel.setManagercode(managerSpoinnerCode);
                dataMainModel.setRemark(strRemark);
                dataMainModel.setSlno("1");
                dataMainModel.setLstProductionDetail(addList);


                Gson gson = new Gson();
                myjson = gson.toJson(dataMainModel);
                sendAnswer(myjson);

            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }
    private void sendAnswer(String json) {

        DataMainModel   submitpackPojo = new DataMainModel();

        try {
            JSONObject jsonObject = new JSONObject(json);
            submitpackPojo.setManagercode(jsonObject.getString("managercode"));
            submitpackPojo.setMastercode(jsonObject.getString("mastercode"));
            submitpackPojo.setRemark(jsonObject.getString("remark"));
            submitpackPojo.setVchdate(jsonObject.getString("vchdate"));

            submitpackPojo.setLstProductionDetail(jsonObject.getString("lstProductionDetail"));
            JSONArray j1 = jsonObject.getJSONArray("lstProductionDetail");
            if (j1 != null && j1.length() > 0) {
                List<LstProductionDetail> aist = new ArrayList<>();
                for (int i = 0; i < j1.length(); i++) {
                    JSONObject ja = j1.getJSONObject(i);
                    LstProductionDetail ans = new LstProductionDetail();
                    ans.setDesigncode(ja.getString("designcode"));
                    ans.setDesignname(ja.getString("designname"));
                    ans.setSizecode(ja.getString("sizecode"));
                    ans.setSizename(ja.getString("sizename"));
                    ans.setQty(ja.getString("qty"));
                    ans.setDetslno(ja.getString("detslno"));
                    aist.add(ans);
                }
                submitpackPojo.setLstProductionDetail(aist);
            }

        } catch (JSONException e) {
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        API_Interface api_interface = APIClient.getService();
        commonUsedModelCall=api_interface.saveDetailsProductions(submitpackPojo);
        commonUsedModelCall.enqueue(new Callback<SubmitDataProductionModel>() {
            @Override
            public void onResponse(@NonNull Call<SubmitDataProductionModel> call, @NonNull Response<SubmitDataProductionModel> response) {
                assert response.body() != null;
                String msg = response.body().getMessage();
                boolean status = response.body().getStatus();
                progressDialog.dismiss();
                if (status){
                    Toast.makeText(ProductionActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
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


    @Override
    public void customerFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            designCode = partyCode;
            designName = partyName;
            designFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_design_name.setText(partyName);

        }


    }

    @Override
    public void supplierFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            managerSpoinnerCode = partyCode;
            managerSpinerName = partyName;
            managerFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_mangerPart_name.setText(partyName);

        }



    }

    @Override
    public void marketerFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            customerCode = partyCode;
            customerName = partyName;
            masterFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_master_party_name.setText(partyName);

        }


    }

    @Override
    public void sizeFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            sizeCode = partyCode;
            sizeName = partyName;
            sizeFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_Size_name.setText(partyName);

        }


    }
}