package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.R;
import com.app.jmd.activity.FabSelectActivity;
import com.app.jmd.activity.ZoomViewActivity;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.interfaces.UpdateQtyInterface;
import com.app.jmd.mode.LstEmbDetail;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterPressman extends RecyclerView.Adapter<AdapterPressman.PManViewHolder> {
    Context context;
//    MyItemClickListener myItemClickListener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private boolean isSelectionEnabled;
//    UpdateQtyInterface updateQtyInterFace;
    String chkQty="";
    private List<LstEmbDetail> itemList;
    public AdapterPressman( List<LstEmbDetail> itemList, Context context, boolean isSelectionEnabled) {
        this.itemList = itemList;
        this.context = context;
        this.isSelectionEnabled=isSelectionEnabled;
    }
    @Override
    public AdapterPressman.PManViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_press,parent,false);
        return new AdapterPressman.PManViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPressman.PManViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position%2==0){
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }else {
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        String fileName="";

        LstEmbDetail currentItem = itemList.get(position);

        holder.tv_design.setText(currentItem.getDesignname());
        holder.etQty.setText(String.valueOf(currentItem.getQty()));
        holder.tv_size.setText(currentItem.getSizename());
        holder.tvLotNo.setText(currentItem.getLotno());

        if (isSelectionEnabled) {

            holder.cbSelect.setChecked(true);
        }
        holder.etQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    int updatedQty = Integer.parseInt(s.toString());
                    currentItem.setQty(String.valueOf(updatedQty)); // Update the model
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                chkQty = s.toString().trim();
//                updateQtyInterFace.updateQty(position, chkQty);


            }
        });

        String fullPath = itemList.get(position).getDesignpath();

        if (fullPath == null || fullPath.trim().isEmpty()) {

        } else {
            String[] parts = fullPath.split("\\\\"); // Double escaping for backslash
            fileName = parts[parts.length - 1];
            Log.d("jfhfftftttfnewwpp",fileName);

        }




        String imageUrl = "http://103.91.90.186/jmdapi/Images/"+fileName;



//        String imageUrl = "http://103.91.90.186/jmdapi/Images/"+itemList.get(position).getDesigncode();
        Log.d("imaggege", imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.dum_img) // Dummy image while loading
                .error(R.drawable.dum_img) // Dummy image if there's an error
                .into(holder.imageDesign);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                selectedPosition = holder.getAdapterPosition();
//                notifyDataSetChanged();
////                if (myItemClickListener!=null) {
////                    myItemClickListener.onItemClick(position);
////                }
//
//            }
//        });
        holder.imageDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZoomViewActivity.class);
                intent.putExtra("image_url", imageUrl); // Pass the image URL to the ZoomActivity
                intent.putExtra("productname", "Press Issue"); // Pass the image URL to the ZoomActivity
                context.startActivity(intent);
            }
        });

        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FabSelectActivity.class);
                intent.putExtra("lot_no_data",itemList.get(position).getLotno() );
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
         return itemList.size();
    }

    public class PManViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llledgerParty;
        ImageView imageDesign;
        EditText etQty;
        CheckBox cbSelect;
        TextView tv_design,tvLotNo,tv_size;
        public PManViewHolder(@NonNull View itemView) {
            super(itemView);
            llledgerParty=itemView.findViewById(R.id.llledgerParty);
            tv_design=itemView.findViewById(R.id.tv_design);
            tvLotNo=itemView.findViewById(R.id.tvLotNo);
            tv_size=itemView.findViewById(R.id.tv_size);
            etQty=itemView.findViewById(R.id.etQty);
            cbSelect=itemView.findViewById(R.id.cbSelect);
            imageDesign=itemView.findViewById(R.id.imageDesign);
//            myItemClickListener= (MyItemClickListener) context;
//            updateQtyInterFace = (UpdateQtyInterface) context;
        }
    }
}
