package com.app.jmd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterFabricator;
import com.app.jmd.adapterS.FabricUDataAdapter;
import com.app.jmd.adapterS.FilterFabricatorAdapter;
import com.app.jmd.adapterS.GoDownFilterAdapter;
import com.app.jmd.adapterS.MasterFilterAdapter;
import com.app.jmd.interfaces.CheckBoxInterface;
import com.app.jmd.interfaces.FilterSpinner;
import com.app.jmd.interfaces.GetLotNoList;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.interfaces.UpdateQtyInterface;
import com.app.jmd.mode.FabicatorModel;
import com.app.jmd.mode.GetUniqueLotModel;
import com.app.jmd.mode.GoDownModel;
import com.app.jmd.mode.LstEmbDetail;
import com.app.jmd.mode.LstGodown;
import com.app.jmd.mode.LstParty;
import com.app.jmd.mode.LstProductionDetail;
import com.app.jmd.mode.Lstlot;
import com.app.jmd.mode.LstlotUnique;
import com.app.jmd.mode.MasterMainModel;
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

public class FabricatorIssueActivity extends AppCompatActivity implements View.OnClickListener, FilterSpinner, MyItemClickListener, UpdateQtyInterface, GetLotNoList {
    Button getLotNo;
    EditText etLotNo;
    RecyclerView rv_fabricator;
    API_Interface api_interface;
    Call<FabicatorModel> fabicatorModelCall;
    Call<GetUniqueLotModel> getUniqueLotModelCall;
    //    FabicatorModel fabicatorModel;
    FabricUDataAdapter fabricUDataAdapter;
    GetUniqueLotModel getUniqueLotModel;
    Context context;
    TextView tv_header;
    ImageView iv_back;
    String lotNo = "";
    LinearLayout ll_ledger_report;
    FilterFabricatorAdapter filterFabricatorAdapter;
    TextView tv_fabric_name, tv_master_name, tv_godown_name;
    RelativeLayout relaLayoutFabricr, relaLayoutMaster, relaLayoutGodown;
    String customerCode = "";
    String customerName = "";
    String masterCode = "";
    String masterName = "";
    String goDownCode = "";
    String goDownName = "", currentDate, myjson, strRemark;
    List<LstParty> customerPartyList = new ArrayList<>();
    List<LstParty> masterPartyList = new ArrayList<>();
    List<LstParty> masterPartyListSpecfic = new ArrayList<>();
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
    List<Lstlot> lstEmbDetailList = new ArrayList<>();
    SaveFabModel fabModel = new SaveFabModel();
    List<LstEmbDetail> addList = new ArrayList<>();
    int posit;
    private List<FabicatorModel> fabicatorModels;
    ArrayList<String> arrayListLots = new ArrayList<>();
    SearchView searchViewFabri;
    ArrayList<LstlotUnique> newListSaveCheced;
    String searchQuery;
    boolean isLoading = false;
    int currentPage = 1;
    private boolean hasLoadedApi = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabricator_issue);
        context = FabricatorIssueActivity.this;
        tv_fabric_name = findViewById(R.id.tv_fabric_name);
        tv_master_name = findViewById(R.id.tv_master_name);
        tv_godown_name = findViewById(R.id.tv_godown_name);
        btn_upload = findViewById(R.id.btn_upload);
        relaLayoutGodown = findViewById(R.id.relaLayoutGodown);
        relaLayoutFabricr = findViewById(R.id.relaLayoutFabricr);
        relaLayoutMaster = findViewById(R.id.relaLayoutMaster);
        etRemakr = findViewById(R.id.etRemakr);
        searchViewFabri = findViewById(R.id.searchViewFabri);
        tv_header = findViewById(R.id.tv_header);
        ll_ledger_report = findViewById(R.id.ll_ledger_report);
        iv_back = findViewById(R.id.iv_back);
        rv_fabricator = findViewById(R.id.rv_fabricator);
        tv_header.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        tv_header.setText("Fabricator Issue");
        btn_upload.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        currentDate = getCurrentDate();
        searchViewFabri.setQueryHint("Search By Design No");
        searchViewFabri.setIconified(false);
        searchViewFabri.clearFocus();


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity2.class));
                AppPrefrences.getInstance(FabricatorIssueActivity.this).removeFromPref("save_checedbox");
                finish();
            }
        });

//        showAllLedgerPartyData("", "");
        if (!hasLoadedApi) {
            hasLoadedApi = true;
            showAllLedgerPartyData("", "");
        }

        rv_fabricator.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutManager != null && layoutManager.findLastVisibleItemPosition() == getUniqueLotModel.getLstlot().size() - 1) {
                    isLoading = true;
                    // Trigger pagination here (e.g., fetch next page)
                    showAllLedgerPartyData("", ""); // 👈 only call when needed
                }
            }
        });


        searchViewFabri.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                 searchQuery= query.toUpperCase();
                showAllLedgerPartyData(searchQuery, "");
                AppPrefrences.getInstance(FabricatorIssueActivity.this).removeFromPref("save_checedbox");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        ImageView closeButton = searchViewFabri.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(v -> {
            // Clear the text
            searchViewFabri.setQuery("", false);

            // Optionally clear focus
            searchViewFabri.clearFocus();
            showAllLedgerPartyData("", "");
            AppPrefrences.getInstance(FabricatorIssueActivity.this).removeFromPref("save_checedbox");



            // 👉 Now hit your API or reset the list
        });
        showAllFabList();
        showAllMasterList();
        showAllGoDownList();
        relaLayoutFabricr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FabricatorIssueActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(FabricatorIssueActivity.this);
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
                masterPartyList.addAll(masterPartyListSpecfic);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(FabricatorIssueActivity.this);
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
                        goDownCode = "";
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
        switch (v.getId()) {
//                lotNo= etLotNo.getText().toString().trim();
//                if (lotNo.isEmpty()){
//                    Toast.makeText(context, "Please enter a lot number", Toast.LENGTH_SHORT).show();
//                }else {

//                }
//                break;
            case R.id.btn_upload:
                if (masterCode == "") {
                    Toast.makeText(context, "Select Master First", Toast.LENGTH_SHORT).show();

                }
                else if (goDownCode == "") {
                Toast.makeText(context, "Select Godown First", Toast.LENGTH_SHORT).show();

            }
                else {
                    if (arrayListLots.size() > 0) {
                        Intent intent = new Intent(context, FabSelectActivity.class);
                        intent.putStringArrayListExtra("list_lots_no", arrayListLots);
                        intent.putExtra("fabricator_code", customerCode);
                        intent.putExtra("master_code", masterCode);
                        intent.putExtra("godown_code", goDownCode);
                        intent.putExtra("remark_data", etRemakr.getText().toString().trim());
                        AppPrefrences.getInstance(FabricatorIssueActivity.this).removeFromPref("save_checedbox");
                        startActivity(intent);

                    } else {
                        Toast.makeText(context, "Select Any Lot No First", Toast.LENGTH_SHORT).show();


                    }


                }
//                validateField();
        }


    }

    private void showAllLedgerPartyData(String lotNo, String masterCode) {
        ProgressDialog pd = new ProgressDialog(FabricatorIssueActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        getUniqueLotModelCall = api_interface.getFabricatorUniqueLots(lotNo, masterCode);
        getUniqueLotModelCall.enqueue(new Callback<GetUniqueLotModel>() {
            @Override
            public void onResponse(@NonNull Call<GetUniqueLotModel> call, @NonNull Response<GetUniqueLotModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                getUniqueLotModel = response.body();
                if (status) {
                    if (!getUniqueLotModel.getLstlot().isEmpty()) {
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FabricatorIssueActivity.this);
                        rv_fabricator.setLayoutManager(linearLayoutManager);
                        fabricUDataAdapter = new FabricUDataAdapter(getUniqueLotModel, FabricatorIssueActivity.this);
                        rv_fabricator.setAdapter(fabricUDataAdapter);


//                        addList.add(new LstEmbDetail("",fabicatorModel.getLstlot().get(posit).getDesigncode()
//                                ,fabicatorModel.getLstlot().get(posit).getSizecode(),
//                                fabicatorModel.getLstlot().get(posit).getColourcode(),
//                                fabicatorModel.getLstlot().get(posit).getLotno(),
//                                fabicatorModel.getLstlot().get(posit).getQty()));
                        pd.dismiss();
                        ll_ledger_report.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(FabricatorIssueActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        rv_fabricator.setAdapter(null);
                        ll_ledger_report.setVisibility(View.GONE);
                    }
                } else {
                    pd.dismiss();
                    rv_fabricator.setAdapter(null);
                    ll_ledger_report.setVisibility(View.GONE);
                    Toast.makeText(FabricatorIssueActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUniqueLotModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
                rv_fabricator.setAdapter(null);
//                ll_ledger_report.setVisibility(View.GONE);

            }
        });

    }

    private void showAllFabList() {

        try {
            api_interface = APIClient.getService();
            Call<MasterMainModel> call = api_interface.getMasterData("03", "");
            progressDialog.show();
            call.enqueue(new Callback<MasterMainModel>() {
                @Override
                public void onResponse(@NonNull Call<MasterMainModel> call, @NonNull Response<MasterMainModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    masterMainModel = response.body();

                    if (status && masterMainModel.getLstParty() != null) {
                        customerPartyList.clear();
                        customerPartyList.addAll(masterMainModel.getLstParty());
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabricatorIssueActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exception) {
            Toast.makeText(FabricatorIssueActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllMasterList() {

        try {
            api_interface = APIClient.getService();
            Call<MasterMainModel> call = api_interface.getMasterData("02", "");
            progressDialog.show();
            call.enqueue(new Callback<MasterMainModel>() {
                @Override
                public void onResponse(@NonNull Call<MasterMainModel> call, @NonNull Response<MasterMainModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    masterMainModel = response.body();

                    if (status && masterMainModel.getLstParty() != null) {
                        masterPartyList.clear();
                        masterPartyList.addAll(masterMainModel.getLstParty());
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabricatorIssueActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exception) {
            Toast.makeText(FabricatorIssueActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllMasterListForSpecific(String lotNo) {

        try {
            api_interface = APIClient.getService();
            Call<MasterMainModel> call = api_interface.getMasterDataSpecificData(lotNo);
            progressDialog.show();
            call.enqueue(new Callback<MasterMainModel>() {
                @Override
                public void onResponse(@NonNull Call<MasterMainModel> call, @NonNull Response<MasterMainModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    masterMainModel = response.body();

                    if (status && masterMainModel.getLstParty() != null) {
                        masterPartyListSpecfic.clear();
                        masterPartyListSpecfic.addAll(masterMainModel.getLstParty());

                        tv_master_name.setText(masterMainModel.getLstParty().get(0).getPartyname());
                        masterCode = masterMainModel.getLstParty().get(0).getPartycode();
                        showAllLedgerPartyData(searchQuery, masterMainModel.getLstParty().get(0).getPartycode());

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MasterMainModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabricatorIssueActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exception) {
            Toast.makeText(FabricatorIssueActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllGoDownList() {

        try {
            api_interface = APIClient.getService();
            Call<GoDownModel> call = api_interface.getGoDownData("", "");
            progressDialog.show();
            call.enqueue(new Callback<GoDownModel>() {
                @Override
                public void onResponse(@NonNull Call<GoDownModel> call, @NonNull Response<GoDownModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    goDownModel = response.body();

                    if (status && goDownModel.getLstGodown() != null) {
                        goDownPartyList.clear();
                        goDownPartyList.addAll(goDownModel.getLstGodown());
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GoDownModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(FabricatorIssueActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception exception) {
            Toast.makeText(FabricatorIssueActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

    private void validateField() {
        boolean cancel = false;
        strRemark = etRemakr.getText().toString().trim();


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
                fabModel.setMastercode(masterCode);
                fabModel.setVchdate(currentDate);
                fabModel.setPartycode(customerCode);
                fabModel.setGodowncode(goDownCode);
                fabModel.setRemark(strRemark);
                fabModel.setSlno("1");
                fabModel.setLstEmbDetail(addList);

                Gson gson = new Gson();
                myjson = gson.toJson(fabModel);
                sendAnswer(myjson);

            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }

    private void sendAnswer(String json) {

        SaveFabModel submitpackPojo = new SaveFabModel();

        try {
            JSONObject jsonObject = new JSONObject(json);
            submitpackPojo.setPartycode(jsonObject.getString("partycode"));
            submitpackPojo.setMastercode(jsonObject.getString("mastercode"));
            submitpackPojo.setGodowncode(jsonObject.getString("godowncode"));
            submitpackPojo.setRemark(jsonObject.getString("remark"));
            submitpackPojo.setVchdate(jsonObject.getString("vchdate"));
            submitpackPojo.setSlno(jsonObject.getString("slno"));
            submitpackPojo.setLstEmbDetail(jsonObject.getString("lstEmbDetail"));
            JSONArray j1 = jsonObject.getJSONArray("lstEmbDetail");
            if (j1 != null && j1.length() > 0) {
                List<LstEmbDetail> aist = new ArrayList<>();
                for (int i = 0; i < j1.length(); i++) {
                    JSONObject ja = j1.getJSONObject(i);
                    LstEmbDetail ans = new LstEmbDetail();
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
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        API_Interface api_interface = APIClient.getService();
        commonUsedModelCall = api_interface.saveDetailsFabIssue(submitpackPojo);
        commonUsedModelCall.enqueue(new Callback<SubmitDataProductionModel>() {
            @Override
            public void onResponse(@NonNull Call<SubmitDataProductionModel> call, @NonNull Response<SubmitDataProductionModel> response) {
                assert response.body() != null;
                String msg = response.body().getMessage();
                boolean status = response.body().getStatus();
                progressDialog.dismiss();
                if (status) {
                    Toast.makeText(FabricatorIssueActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish(); // Finish the current activity
                    startActivity(intent);
                } else {
                    AppPrefrences.alertBox(msg, context);
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
        posit = position;

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
            showAllLedgerPartyData("", masterCode);
//            btn_upload.setVisibility(View.GONE);

        }

    }

    @Override
    public void sizeFilter(String partyCode, String partyName) {

    }

    @Override
    public void updateQty(int position, String newText) {

    }

    @Override
    public void GetListLot(ArrayList<String> stringArrayList) {
        arrayListLots = stringArrayList;
        if (arrayListLots.size() > 0) {

                showAllMasterListForSpecific(arrayListLots.get(0));

        }

        Log.d("jsdjhdhdhd", String.valueOf(stringArrayList));



}

    @Override
    public void onBackPressed() {
        AppPrefrences.getInstance(FabricatorIssueActivity.this).removeFromPref("save_checedbox");
        super.onBackPressed();
    }
}