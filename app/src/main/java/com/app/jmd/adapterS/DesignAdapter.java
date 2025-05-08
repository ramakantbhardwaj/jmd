package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.interfaces.CheckBoxInterface;
import com.app.jmd.interfaces.DesignCodeInterface;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.mode.GetDesignModel;

public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.DesignNameViewHolder> {
    GetDesignModel getDesignModel;
    Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;
//    MyItemClickListener myItemClickListener;
    DesignCodeInterface designCodeInterface;
    CheckBoxInterface checkBoxInterface;
    boolean isCheched=false;


    public DesignAdapter(GetDesignModel getDesignModel, Context context) {
        this.getDesignModel = getDesignModel;
        this.context = context;
    }

    @NonNull
    @Override
    public DesignAdapter.DesignNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_layout,parent,false);
        return new DesignAdapter.DesignNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignAdapter.DesignNameViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position%2==0){
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }else {
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.tvDesignName.setText(getDesignModel.getLstDesign().get(position).getDesignname());
        holder.cbDesignName.setChecked(position == selectedPosition);
        holder.cbDesignName.setOnClickListener(v -> {
            if (selectedPosition != position) {
                // Update selected position
                selectedPosition = position;

                // Notify the adapter to refresh the view
                notifyDataSetChanged();
                designCodeInterface.getPosAndCode(position,getDesignModel.getLstDesign().get(position).getDesigncode());
                checkBoxInterface.
                             getCheced(position,isCheched);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != position) {
                    // Update selected position
                    selectedPosition = position;

                    // Notify the adapter to refresh the view
                    notifyDataSetChanged();
                    designCodeInterface.getPosAndCode(position,getDesignModel.getLstDesign().get(position).getDesigncode());

                }

//                holder.cbDesignName.setChecked(true);

//                selectedPosition = holder.getAdapterPosition();
//                notifyDataSetChanged();
//                if (myItemClickListener!=null) {
//                    myItemClickListener.onItemClick(position);
//                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return getDesignModel.getLstDesign().size();
    }

    public class DesignNameViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbDesignName;
        TextView tvDesignName;
        LinearLayout llledgerParty;
        public DesignNameViewHolder(@NonNull View itemView) {
            super(itemView);
            cbDesignName=itemView.findViewById(R.id.cbDesignName);
            tvDesignName=itemView.findViewById(R.id.tvDesignName);
            llledgerParty=itemView.findViewById(R.id.llledgerParty);
            designCodeInterface= (DesignCodeInterface) context;
            checkBoxInterface= (CheckBoxInterface) context;


        }
    }
}
