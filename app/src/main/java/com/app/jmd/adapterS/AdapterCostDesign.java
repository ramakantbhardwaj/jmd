package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.interfaces.FilterSpinner;
import com.app.jmd.mode.LstDesign;
import com.app.jmd.mode.LstSize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdapterCostDesign extends RecyclerView.Adapter<AdapterCostDesign.CustomerViewHolder> implements Filterable {

    Context context;
    List<LstDesign> sizeNameList;
    List<LstDesign> sizeNameAllList;
    FilterSpinner filterSpinner;
    boolean isSelected=false;

    public AdapterCostDesign(List<LstDesign> sizeNameList, Context context)
    {
        this.sizeNameList = sizeNameList;
        this.context = context;
        this.sizeNameAllList = new ArrayList<>(sizeNameList);
    }
    @NonNull
    @Override
    public AdapterCostDesign.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_master_spinner, viewGroup, false);
        return new AdapterCostDesign.CustomerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterCostDesign.CustomerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name.setText(sizeNameList.get(position).getDesignname());
        holder.itemView.setOnClickListener(v -> {
            holder.ll_rowItem.setBackgroundColor(Color.parseColor("#EFEFEF"));
            filterSpinner.sizeFilter(sizeNameList.get(position).getDesigncode(),sizeNameList.get(position).getDesignname());
        });
    }
    @Override
    public int getItemCount()
    {
        return sizeNameList.size();
    }
    @Override
    public Filter getFilter()
    {
        return filter;
    }

    Filter filter=new Filter()
    {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<LstDesign> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(sizeNameAllList);
            }else
            {
                for (LstDesign party:sizeNameAllList)
                {
                    String partyName=party.getDesignname();
                    if (partyName.toLowerCase().trim().replaceAll(" ", "").contains(constraint.toString().toLowerCase().trim().replaceAll(" ", "")))
                    {
                        filteredList.add(party);
                    }
                }
            }
            FilterResults filterResults= new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }
        //run on ui thread
        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.values != null)
            {
                sizeNameList.clear();
                sizeNameList.addAll((Collection<? extends LstDesign>) results.values);
                notifyDataSetChanged();
            }
        }
    };
    class CustomerViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name;
        LinearLayoutCompat ll_rowItem;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            filterSpinner = (FilterSpinner) context;
            tv_name=itemView.findViewById(R.id.tv_name);
            ll_rowItem=itemView.findViewById(R.id.ll_rowItem);
        }
    }
}
