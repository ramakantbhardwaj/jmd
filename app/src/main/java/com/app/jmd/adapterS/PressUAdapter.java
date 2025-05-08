package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.activity.ZoomViewActivity;
import com.app.jmd.interfaces.GetLotNoList;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.interfaces.UpdateQtyInterface;
import com.app.jmd.mode.GetUniqueLotModel;
import com.app.jmd.mode.GetUniqueLotModelsPress;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

public class PressUAdapter extends RecyclerView.Adapter<PressUAdapter.PressViewHolder> {
    GetUniqueLotModelsPress getUniqueLotModelsPress;
    Context context;
    MyItemClickListener myItemClickListener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private boolean isSelectionEnabled;
    UpdateQtyInterface updateQtyInterFace;
    String chkQty="";
    ArrayList<String> addLots= new ArrayList<>();
    GetLotNoList getLotNoList;
    public PressUAdapter(GetUniqueLotModelsPress getUniqueLotModelsPress, Context context, boolean isSelectionEnabled) {
        this.getUniqueLotModelsPress = getUniqueLotModelsPress;
        this.context = context;
        this.isSelectionEnabled=isSelectionEnabled;
    }
    @NonNull
    @Override

    public PressUAdapter.PressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unique_fabric,parent,false);
        return new PressUAdapter.PressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PressUAdapter.PressViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position%2==0){
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }else {
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.tv_design.setText(getUniqueLotModelsPress.getLstlot().get(position).getDesignname());
        holder.tvLotNo.setText(getUniqueLotModelsPress.getLstlot().get(position).getLotno());
        holder.tvQty.setText(getUniqueLotModelsPress.getLstlot().get(position).getQty());
        String fileName="";

        if (isSelectionEnabled) {

            holder.cbSelect.setChecked(true);
        }
//        holder.etQty.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                chkQty = s.toString().trim();
//                updateQtyInterFace.updateQty(position, chkQty);
//
//
//            }
//        });



        String fullPath = getUniqueLotModelsPress.getLstlot().get(position).getDesignpath();

        if (fullPath == null || fullPath.trim().isEmpty()) {

        } else {
            String[] parts = fullPath.split("\\\\"); // Double escaping for backslash
            fileName = parts[parts.length - 1];
            Log.d("jfhfftftttfneww",fileName);

        }



        String imageUrl = "http://103.91.90.186/jmdapi/Images/"+fileName;
        holder.imageDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZoomViewActivity.class);
                intent.putExtra("image_url", imageUrl); // Pass the image URL to the ZoomActivity
                intent.putExtra("productname", "Press Issue"); // Pass the image URL to the ZoomActivity
                context.startActivity(intent);
            }
        });

        Log.d("imaggege", imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.dum_img) // Dummy image while loading
                .error(R.drawable.dum_img ) // Dummy image if there's an error
                .into(holder.imageDesign);


//        String imageUrl = "http://103.91.90.186/jmdapi/Images/"+getUniqueLotModelsPress.getLstlot().get(position).getDesignpath();
        Log.d("imaggege", imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.dum_img) // Dummy image while loading
                .error(R.drawable.dum_img ) // Dummy image if there's an error
                .into(holder.imageDesign);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                if (myItemClickListener!=null) {
                    myItemClickListener.onItemClick(position);
                }

            }
        });

        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    addLots.addAll(Arrays.asList(
                            getUniqueLotModelsPress.getLstlot().get(position).getLotno()// Current lot number
                            // Another lot
                    ));
                    getLotNoList.GetListLot(addLots);
//                    Toast.makeText(context, ""+addLots.size(), Toast.LENGTH_SHORT).show();

                }else {
                    addLots.remove(getUniqueLotModelsPress.getLstlot().get(position).getLotno());
                }
                Log.d("hshsygysg",getUniqueLotModelsPress.getLstlot().get(position).getLotno());
            }
        });

    }

    @Override
    public int getItemCount() {
        return getUniqueLotModelsPress.getLstlot().size();
    }

    public class PressViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llledgerParty;
        ImageView imageDesign;
        EditText etQty;
        CheckBox cbSelect;
        TextView tv_design,tvLotNo,tvQty;
        public PressViewHolder(@NonNull View itemView) {
            super(itemView);
            llledgerParty=itemView.findViewById(R.id.llledgerParty);
            tv_design=itemView.findViewById(R.id.tv_design);
            tvLotNo=itemView.findViewById(R.id.tvLotNo);
            cbSelect=itemView.findViewById(R.id.cbSelect);
            imageDesign=itemView.findViewById(R.id.imageDesign);
            tvQty=itemView.findViewById(R.id.tvQty);
            myItemClickListener= (MyItemClickListener) context;
            updateQtyInterFace = (UpdateQtyInterface) context;
            getLotNoList = (GetLotNoList) context;
        }
    }
}
