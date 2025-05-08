package com.app.jmd.adapterS;

import static com.app.jmd.activity.MainActivity2.tv_laser_current_bal;

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
import com.app.jmd.mode.PendingProductionModel;

public class AdapterPendingProduction extends RecyclerView.Adapter<AdapterPendingProduction.ViewHolder> {
    PendingProductionModel pendingProductionModel;
    Context context;

    public AdapterPendingProduction(PendingProductionModel pendingProductionModel, Context context) {
        this.pendingProductionModel = pendingProductionModel;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPendingProduction.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pending_production,parent,false);
        return new AdapterPendingProduction.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPendingProduction.ViewHolder holder, int position) {
        if (position%2==0){
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }else {
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.tv_slno.setText(String.valueOf(position+1));
        holder.tv_qty.setText(pendingProductionModel.getLstprod().get(position).getQty());
        holder.tv_design.setText(pendingProductionModel.getLstprod().get(position).getDesign());
        holder.tv_master.setText(pendingProductionModel.getLstprod().get(position).getMaster());
        holder.tv_manager.setText(pendingProductionModel.getLstprod().get(position).getManager());
        holder.tv_days.setText(pendingProductionModel.getLstprod().get(position).getDays());


    }

    @Override
    public int getItemCount() {
        return pendingProductionModel.getLstprod().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_qty,tv_design,tv_master,tv_manager,tv_slno,tv_days;

        LinearLayout llledgerParty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_slno = itemView.findViewById(R.id.tv_slno);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_design = itemView.findViewById(R.id.tv_design);
            tv_master = itemView.findViewById(R.id.tv_master);
            tv_manager = itemView.findViewById(R.id.tv_manager);
            llledgerParty = itemView.findViewById(R.id.llledgerParty);
            tv_days = itemView.findViewById(R.id.tv_days);

        }
    }
}
