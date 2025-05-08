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
import com.app.jmd.mode.LstParty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MasterFilterAdapter extends RecyclerView.Adapter<MasterFilterAdapter.CustomerViewHolder> implements Filterable {

    Context context;
    List<LstParty> masterPartyList;
    List<LstParty> masterPartyAllList;
    FilterSpinner filterSpinner;
    boolean isSelected=false;

    public MasterFilterAdapter(List<LstParty> customerPartyList, Context context)
    {
        this.masterPartyList = customerPartyList;
        this.context = context;
        this.masterPartyAllList = new ArrayList<>(customerPartyList);
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_master_spinner, viewGroup, false);
        return new CustomerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_name.setText(masterPartyList.get(position).getPartyname());
        holder.itemView.setOnClickListener(v -> {
            holder.ll_rowItem.setBackgroundColor(Color.parseColor("#EFEFEF"));
            filterSpinner.marketerFilter(masterPartyList.get(position).getPartycode(),masterPartyList.get(position).getPartyname());
        });
    }
    @Override
    public int getItemCount()
    {
        return masterPartyList.size();
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
            List<LstParty> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(masterPartyAllList);
            }else
            {
                for (LstParty party:masterPartyAllList)
                {
                    String partyName=party.getPartyname();
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
                masterPartyList.clear();
                masterPartyList.addAll((Collection<? extends LstParty>) results.values);
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
