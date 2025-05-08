package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.mode.LstProductionDetail;

import java.util.List;

public class DesignSizeAdapterPro extends RecyclerView.Adapter<DesignSizeAdapterPro.DesignSizeViewHolder> {
    List<LstProductionDetail> sizeList;
    //    OnClickItemInterface onClickItemInterface;
    Context context;

    public DesignSizeAdapterPro(List<LstProductionDetail> sizeList, Context context) {
        this.sizeList = sizeList;
        this.context = context;
//        this.onClickItemInterface = onClickItemInterface;
    }
    @NonNull
    @Override
    public DesignSizeAdapterPro.DesignSizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_size_det_second,parent,false);
        return new DesignSizeAdapterPro.DesignSizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignSizeAdapterPro.DesignSizeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_sizes.setText(sizeList.get(position).getSizename());
        holder.tv_design.setText(sizeList.get(position).getDesignname());
        holder.tv_pcs.setText(sizeList.get(position).getQty());
//        holder.tv_sl_no.setText(sizeList.get(position).getDetslno());
        holder.tv_sl_no.setText(String.valueOf(position + 1));
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this record ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sizeList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public class DesignSizeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sl_no,tv_design,tv_sizes,tv_pcs,tv_delete;
        public DesignSizeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sizes = itemView.findViewById(R.id.tv_sizes);
            tv_sl_no = itemView.findViewById(R.id.tv_sl_no);
            tv_design = itemView.findViewById(R.id.tv_design);
            tv_pcs = itemView.findViewById(R.id.tv_pcs);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
