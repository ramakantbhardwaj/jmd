package com.app.jmd.adapterS;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.mode.JobWorkStockMainModel;

public class AdapterPendingStock extends RecyclerView.Adapter<AdapterPendingStock.BalanceViewHolder> {
    JobWorkStockMainModel jobWorkStockMainModel;
    Context context;
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
    public void onBindViewHolder(@NonNull AdapterPendingStock.BalanceViewHolder holder, int position) {
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
            String vchDate= jobWorkStockMainModel.getLststock().get(position).getVchdate();
            String strvchDate[] = vchDate.split(" ");
            String strvch = strvchDate[0];
            String strvchFormate[] = strvch.split("-");
            holder.tv_vch_date.setText(strvchFormate[1]+"-"+strvchFormate[0]+"-"+strvchFormate[2]);
        }
        holder.tv_designName.setText(jobWorkStockMainModel.getLststock().get(position).getDesignname());
        holder.tv_size.setText(jobWorkStockMainModel.getLststock().get(position).getSizename());
        holder.tv_colours.setText(jobWorkStockMainModel.getLststock().get(position).getColourname());
        holder.tv_ballance.setText(jobWorkStockMainModel.getLststock().get(position).getBalance());

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
}
