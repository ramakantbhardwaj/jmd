package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.activity.CostSheetActivity;
import com.app.jmd.activity.ProductionActivity;
import com.app.jmd.mode.DesignDataModel;
import com.app.jmd.mode.DesignDetailsMainModel;
import com.app.jmd.mode.GetDesignModel;
import com.app.jmd.mode.JobWorkStockMainModel;
import com.app.jmd.retrofit.APIClient;
import com.app.jmd.retrofit.API_Interface;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPendingStock extends RecyclerView.Adapter<AdapterPendingStock.BalanceViewHolder> {
    JobWorkStockMainModel jobWorkStockMainModel;
    Context context;
    API_Interface api_interface;
    DesignDataModel designDataModel;






    public AdapterPendingStock(JobWorkStockMainModel jobWorkStockMainModel, Context context) {
        this.jobWorkStockMainModel = jobWorkStockMainModel;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterPendingStock.BalanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock_balance,parent,false);
        return new AdapterPendingStock.BalanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPendingStock.BalanceViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position%2==0)
        {
            holder.llsaleParty.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }else {
            holder.llsaleParty.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_partyName.setText(jobWorkStockMainModel.getLststock().get(position).getPartyname());
        holder.tv_issue.setText(jobWorkStockMainModel.getLststock().get(position).getIssue());
        if (!jobWorkStockMainModel.getLststock().get(position).getVchdate().isEmpty()){
//            String vchDate= jobWorkStockMainModel.getLststock().get(position).getVchdate();
//            String strvchDate[] = vchDate.split(" ");
//            String strvch = strvchDate[0];
//            String strvchFormate[] = strvch.split("-");
            holder.tv_vch_date.setText(jobWorkStockMainModel.getLststock().get(position).getVchdate());
        }
        holder.tv_designName.setText(jobWorkStockMainModel.getLststock().get(position).getDesignname());
        holder.tv_size.setText(jobWorkStockMainModel.getLststock().get(position).getSizename());
        holder.tv_colours.setText(jobWorkStockMainModel.getLststock().get(position).getColourname());
        holder.tv_ballance.setText(jobWorkStockMainModel.getLststock().get(position).getBalance());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllDesignList(jobWorkStockMainModel.getLststock().get(position).getDesignname());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobWorkStockMainModel.getLststock().size();
    }

    public class BalanceViewHolder extends RecyclerView.ViewHolder {
        TextView tv_slno,tv_designName,tv_size,tv_colours,tv_ballance,tv_issue,tv_partyName,tv_vch_date;
        LinearLayout llsaleParty;
        public BalanceViewHolder(@NonNull View itemView) {
            super(itemView);
            llsaleParty = itemView.findViewById(R.id.llsaleParty);
            tv_designName = itemView.findViewById(R.id.tv_designName);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_colours = itemView.findViewById(R.id.tv_colours);
            tv_partyName = itemView.findViewById(R.id.tv_partyName);
            tv_ballance = itemView.findViewById(R.id.tv_ballance);
            tv_issue = itemView.findViewById(R.id.tv_issue);
            tv_slno = itemView.findViewById(R.id.tv_slno);
            tv_vch_date = itemView.findViewById(R.id.tv_vch_date);

        }
    }
    private void showImagePopup(String imageUrl) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_image_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView popupImage = dialog.findViewById(R.id.popup_image);
        ImageButton closeButton = dialog.findViewById(R.id.close_button);

        Glide.with(context).load(imageUrl).into(popupImage);

        closeButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
    private void showAllDesignList(String designName) {

        try{
            api_interface = APIClient.getService();
            Call<DesignDataModel> call = api_interface.getDesignData("", designName);
            ProgressDialog pd=new ProgressDialog(context);
            pd.show();
            pd.setMessage("Please Wait....");
            call.enqueue(new Callback<DesignDataModel>() {
                @Override
                public void onResponse(@NonNull Call<DesignDataModel> call, @NonNull Response<DesignDataModel> response) {

                    assert response.body() != null;
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    designDataModel = response.body();
                    String fileName="";

                    if (status && designDataModel.getLstDesign()!=null) {
                        for (int i=0; i<designDataModel.getLstDesign().size();i++) {
                            designDataModel.getLstDesign().get(i).getDesigncode();

                            String fullPath= designDataModel.getLstDesign().get(i).getDesignpath();
//                            String fullPath = getUniqueLotModel.getLstlot().get(position).getDesignpath();

                            if (fullPath == null || fullPath.trim().isEmpty()) {

                            } else {
                                String[] parts = fullPath.split("\\\\"); // Double escaping for backslash
                                fileName = parts[parts.length - 1];
                                Log.d("jfhfftftttfneww",fileName);
                                pd.dismiss();

                            }



                            String imageUrl = "http://103.91.90.186/jmdapi/Images/"+fileName;


                            showImagePopup(imageUrl);
//                            Toast.makeText(context, "position is " + String.valueOf(i), Toast.LENGTH_SHORT).show();
                            Log.d("kdhddh", designDataModel.getLstDesign().get(i).getDesigncode());
                        }

                        pd.dismiss();
                    }else {
                        pd.dismiss();
                    }              }

                @Override
                public void onFailure(@NonNull Call<DesignDataModel> call, @NonNull Throwable t) {
                    pd.dismiss();
//                    spn_master.setAdapter(null);
                    Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch(Exception exception){
            Toast.makeText(context, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
