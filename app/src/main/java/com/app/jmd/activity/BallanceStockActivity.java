package com.app.jmd.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.adapterS.AdapterPendingStock;
import com.app.jmd.adapterS.DesignFilterAdapter;
import com.app.jmd.adapterS.MasterFilterAdapter;
import com.app.jmd.adapterS.SizeFilterAdapter;
import com.app.jmd.interfaces.FilterSpinner;
import com.app.jmd.mode.BalanceStockMainModel;
import com.app.jmd.mode.DesignDataModel;
import com.app.jmd.mode.JobWorkStockMainModel;
import com.app.jmd.mode.LstDesign;
import com.app.jmd.mode.LstParty;
import com.app.jmd.mode.LstSize;
import com.app.jmd.mode.MasterMainModel;
import com.app.jmd.mode.Party;
import com.app.jmd.mode.SizeDataModel;
import com.app.jmd.retrofit.APIClient;
import com.app.jmd.retrofit.API_Interface;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BallanceStockActivity extends AppCompatActivity implements View.OnClickListener, FilterSpinner {
    TextView tv_header,tv_design_name,tv_Size_name;
    ImageView iv_back;
    API_Interface api_interface;
    Call<BalanceStockMainModel> balanceStockMainModelCall;
    Call<JobWorkStockMainModel> jobWorkStockMainModelCall;
    BalanceStockMainModel balanceStockMainModel;
    JobWorkStockMainModel jobWorkStockMainModel;
    AdapterPendingStock adapterPendingStock;
    RecyclerView rvBalaceStock;
    Context context;
    EditText edt_from_date,edt_to_date;
    LinearLayout ll_sale_report;
    private int mYear, mMonth, mDay;
    DatePickerDialog dpd, dpd1;
    String fromDate = "", toDate = "";
    ImageView ic_to_date,ic_from_date;
    DesignFilterAdapter designFilterAdapter;
    MasterFilterAdapter masterFilterAdapter;
    RelativeLayout relaLayoutDesign,relaLayoutSize;
    List<LstDesign> designNameList = new ArrayList<>();
    List<LstDesign> designCodeList = new ArrayList<>();
    List<LstParty> customerPartyList = new ArrayList<>();
    List<LstParty> customerCodeList = new ArrayList<>();
    AlertDialog alertDialog;
    String designCode="";
    String designName="";
    String partyCodeN="";
    String partyNameN="";
    ProgressDialog progressDialog;
    DesignDataModel designDataModel;
    MasterMainModel masterMainModel;
    private RadioGroup radioGroup;
    private  RadioButton radioButFabri,radioButSubDesign;
    AppCompatButton btn_upload;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballance_stock);
        context=BallanceStockActivity.this;
        tv_header=findViewById(R.id.tv_header);
        iv_back=findViewById(R.id.iv_back);
        edt_from_date=findViewById(R.id.edt_from_date);
        edt_to_date=findViewById(R.id.edt_to_date);
        ll_sale_report=findViewById(R.id.ll_sale_report);
        edt_from_date.setOnClickListener(this);
        rvBalaceStock=findViewById(R.id.rvBalaceStock);
        ic_to_date=findViewById(R.id.ic_to_date);
        ic_from_date=findViewById(R.id.ic_from_date);
        radioGroup=findViewById(R.id.radioGroup);
        radioButFabri=findViewById(R.id.radioButFabri);
        radioButSubDesign=findViewById(R.id.radioButSubDesign);
        relaLayoutSize=findViewById(R.id.relaLayoutSize);
        relaLayoutDesign=findViewById(R.id.relaLayoutDesign);
        tv_design_name=findViewById(R.id.tv_design_name);
        tv_Size_name=findViewById(R.id.tv_Size_name);
        btn_upload=findViewById(R.id.btn_upload);
        tv_header.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        tv_header.setText("Balance Stock");
        fromDate = "01-Aug-2024";
        Calendar c = Calendar.getInstance();
        int YearD = c.get(Calendar.YEAR);
        int MonthD = c.get(Calendar.MONTH);
        int mDayD = c.get(Calendar.DAY_OF_MONTH);
        String fromdate_monthM = new DateFormatSymbols().getShortMonths()[MonthD];
        fromdate_monthM=fromdate_monthM.substring(0,3);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");// HH:mm:ss");
        edt_to_date.setText(String.valueOf(mDayD)+"-"+fromdate_monthM+"-"+String.valueOf(YearD));
        toDate=edt_to_date.getText().toString();
        edt_from_date.setText(fromDate);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        btn_upload.setOnClickListener(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity2.class));
                finish();
            }
        });
        showAllDesignList();
        showAllMasterList();
        showAllLedgerPartyData("","");
        radioButFabri.setChecked(true);
        edt_from_date.setOnClickListener(this);
        edt_to_date.setOnClickListener(this);
        ic_from_date.setOnClickListener(this);
        ic_to_date.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedText = selectedRadioButton.getText().toString();
                if (selectedText.equalsIgnoreCase("Fabricator")){
                    showAllMasterList();
                      showAllLedgerPartyData("","");
                    masterFilterAdapter = new MasterFilterAdapter(customerPartyList, context);
                    masterFilterAdapter.notifyDataSetChanged();
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                }else if(selectedText.equalsIgnoreCase("Pressman")){
                    showAllMasterListForPress();
                    showAllLedgerPartyDataPress();
                    masterFilterAdapter = new MasterFilterAdapter(customerPartyList, context);
                    masterFilterAdapter.notifyDataSetChanged();
//                    Toast.makeText(BallanceStockActivity.this, "Selected: " + selectedText, Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });
//        tv_submit.setOnClickListener(this);

        relaLayoutDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BallanceStockActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(BallanceStockActivity.this);
                builder.setTitle("Select Party");
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
                        partyCodeN = "";
                        partyNameN = "Select Party";
                        tv_Size_name.setText(partyNameN);
                        dialog.dismiss();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });



    }
    private void showAllLedgerPartyData(String partyCode, String designCodeN) {
        ProgressDialog pd = new ProgressDialog(BallanceStockActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        jobWorkStockMainModelCall = api_interface.allJobWorkStock("03", partyCode,designCodeN,edt_to_date.getText().toString());
        jobWorkStockMainModelCall.enqueue(new Callback<JobWorkStockMainModel>() {
            @Override
            public void onResponse(@NonNull Call<JobWorkStockMainModel> call, @NonNull Response<JobWorkStockMainModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                jobWorkStockMainModel = response.body();
                if (status) {
                    if (!jobWorkStockMainModel.getLststock().isEmpty()) {
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BallanceStockActivity.this);
                        rvBalaceStock.setLayoutManager(linearLayoutManager);
                        adapterPendingStock = new AdapterPendingStock(jobWorkStockMainModel, BallanceStockActivity.this);
                        rvBalaceStock.setAdapter(adapterPendingStock);
                        pd.dismiss();
                        ll_sale_report.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(BallanceStockActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        rvBalaceStock.setAdapter(null);
                        ll_sale_report.setVisibility(View.GONE);
                    }
                } else {
                    pd.dismiss();
                    rvBalaceStock.setAdapter(null);
                    ll_sale_report.setVisibility(View.GONE);
                    Toast.makeText(BallanceStockActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<JobWorkStockMainModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
                rvBalaceStock.setAdapter(null);
//                ll_ledger_report.setVisibility(View.GONE);

            }
        });

    }
    private void showAllLedgerPartyDataPress() {
        ProgressDialog pd = new ProgressDialog(BallanceStockActivity.this);
        pd.show();
        pd.setMessage("Please Wait....");
        api_interface = APIClient.getService();
        jobWorkStockMainModelCall = api_interface.allJobWorkStock("60","'"+partyCodeN+"'","'"+designCode+",",edt_to_date.getText().toString());
        jobWorkStockMainModelCall.enqueue(new Callback<JobWorkStockMainModel>() {
            @Override
            public void onResponse(@NonNull Call<JobWorkStockMainModel> call, @NonNull Response<JobWorkStockMainModel> response) {
                assert response.body() != null;
                boolean status = response.body().getStatus();
                String msg = response.body().getMessage();
                jobWorkStockMainModel = response.body();
                if (status) {
                    if (!jobWorkStockMainModel.getLststock().isEmpty()) {
                        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BallanceStockActivity.this);
                        rvBalaceStock.setLayoutManager(linearLayoutManager);
                        adapterPendingStock = new AdapterPendingStock(jobWorkStockMainModel, BallanceStockActivity.this);
                        rvBalaceStock.setAdapter(adapterPendingStock);
                        pd.dismiss();
                        ll_sale_report.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(BallanceStockActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        rvBalaceStock.setAdapter(null);
                        ll_sale_report.setVisibility(View.GONE);
                    }
                } else {
                    pd.dismiss();
                    rvBalaceStock.setAdapter(null);
                    ll_sale_report.setVisibility(View.GONE);
                    Toast.makeText(BallanceStockActivity.this, "Record not Available", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<JobWorkStockMainModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
                rvBalaceStock.setAdapter(null);
//                ll_ledger_report.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edt_to_date:
                validateField();
                break;
            case R.id.ic_to_date:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                // Launch Date Picker Dialog
                dpd = new DatePickerDialog(this,

                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth)
                            {

                                String todate_month = new DateFormatSymbols().getShortMonths()[ monthOfYear];
                                todate_month=todate_month.substring(0,3);
//                                String[] tomons = new DateFormatSymbols().getShortMonths();


//                                toDate   = String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year;
                                SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");

                                edt_to_date.setText(String.format("%02d", dayOfMonth) + "-"
                                        + ((todate_month)) + "-" + year);

                                toDate =  (monthOfYear + 1) + "-" + todate_month+ "-"+year ;
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
                break;
            case R.id.ic_from_date:
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);
                // Launch Date Picker Dialog
                dpd1 = new DatePickerDialog(this,
                        (view, year, monthOfYear, dayOfMonth) -> {
                            // Display Selected date in textbox
                            String fromdate_month = new DateFormatSymbols().getShortMonths()[monthOfYear];
                            fromdate_month =fromdate_month.substring(0,3);
//                                String[] mons = new DateFormatSymbols().getShortMonths();
//                                fromDate   = String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year;
                            SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
                            edt_from_date.setText(String.format("%02d", dayOfMonth) + "-"
                                    + ((fromdate_month)) + "-" + year);
                            fromDate = (monthOfYear + 1) + "-" + fromdate_month+ "-"+year;
                        }, mYear, mMonth, mDay);
                dpd1.show();

                break;
            case R.id.btn_upload:
                if ((designCode == null || designCode.trim().isEmpty()) &&
                        (partyCodeN == null || partyCodeN.trim().isEmpty())) {

                    Toast.makeText(context, "Select Design or Party First", Toast.LENGTH_SHORT).show();

                } else if (!designCode.trim().isEmpty()) {
                    showAllLedgerPartyData("", "'"+designCode+"'");

                } else if (!partyCodeN.trim().isEmpty()) {
                    showAllLedgerPartyData("'"+partyCodeN+"'", "");
                }




        }



    }
    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(Calendar.getInstance().getTime());
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

    }

    @Override
    public void marketerFilter(String partyCode, String partyName) {
        if (partyCode.length() > 0) {
            partyCodeN = partyCode;
            partyNameN = partyName;
            masterFilterAdapter.getFilter().filter("");
            alertDialog.dismiss();
            tv_Size_name.setText(partyNameN);

        }

    }

    @Override
    public void sizeFilter(String partyCode, String partyName) {


    }
    private void validateField() {
        boolean cancel = false;
        if (edt_from_date.getText().toString().isEmpty()) {
            AppPrefrences.alertBox("Please choose from date", BallanceStockActivity.this);
            cancel = true;
            return;
        }
        if (edt_to_date.getText().toString().isEmpty()) {
            AppPrefrences.alertBox("Please choose to date", BallanceStockActivity.this);
            cancel = true;
            return;
        }


//            showAllLedgerPartyData();

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
                    Toast.makeText(BallanceStockActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(BallanceStockActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BallanceStockActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(BallanceStockActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showAllMasterListForPress() {

        try{
            api_interface = APIClient.getService();
            Call<MasterMainModel> call = api_interface.getMasterData("60","");
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
                    Toast.makeText(BallanceStockActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(BallanceStockActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}