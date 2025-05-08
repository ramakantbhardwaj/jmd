package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.activity.CostSheetActivity;
import com.app.jmd.activity.FabSelectActivity;
import com.app.jmd.activity.ZoomViewActivity;
import com.app.jmd.interfaces.GetLotNoList;
import com.app.jmd.interfaces.MyItemClickListener;
import com.app.jmd.interfaces.UpdateQtyInterface;
import com.app.jmd.mode.FabicatorModel;
import com.app.jmd.mode.GetUniqueLotModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FabricUDataAdapter extends RecyclerView.Adapter<FabricUDataAdapter.FabricViewHolder> {
    GetUniqueLotModel getUniqueLotModel;
    Context context;
    MyItemClickListener myItemClickListener;
    UpdateQtyInterface updateQtyInterFace;
    String chkQty="";
    ArrayList<String> addLots= new ArrayList<>();
    ArrayList<String> addSetTrue= new ArrayList<>();
    GetLotNoList getLotNoList;
    private int selectedPosition = -1;




    public FabricUDataAdapter(GetUniqueLotModel getUniqueLotModel, Context context) {
        this.getUniqueLotModel = getUniqueLotModel;
        this.context = context;
    }
    @Override
    public FabricUDataAdapter.FabricViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fabicator_row_first, parent, false);
        return new FabricUDataAdapter.FabricViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FabricUDataAdapter.FabricViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position%2==0){
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }else {
            holder.llledgerParty.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.isRecyclable();
        String fileName="";

        holder.tv_design.setText(getUniqueLotModel.getLstlot().get(position).getDesignname());

        holder.tvQty.setText(getUniqueLotModel.getLstlot().get(position).getQty());
        holder.tvRepeat.setText(getUniqueLotModel.getLstlot().get(position).getRepeat());
        holder.cbSelect.setChecked(getUniqueLotModel.getLstlot().get(position).isChecked());
        List<String> savedChecked = AppPrefrences.getInstance(context).getArrayList("save_checedbox");

//        AppPrefrences.getInstance(context).getArrayList("save_checedbox");


//        if ( AppPrefrences.getInstance(context).getArrayList("save_checedbox")!=null) {
//            if (AppPrefrences.getInstance(context).getArrayList("save_checedbox").contains(getUniqueLotModel.getLstlot().get(position).getLotno())) {
//
//                holder.cbSelect.setChecked(true);
//            }
//        }

        if (savedChecked != null && savedChecked.contains(getUniqueLotModel.getLstlot().get(position).getLotno())) {
            holder.cbSelect.setChecked(true);
        } else {
            holder.cbSelect.setChecked(false);
        }


        holder.cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {

//            addLots.add(getUniqueLotModel.getLstlot().get(position).getLotno());
//            addLots.addAll(Arrays.asList(
//                           getUniqueLotModel.getLstlot().get(position).getLotno()));//
//            if (!addLots.contains(getUniqueLotModel.getLstlot().get(position).getLotno())) {
//                addLots.add(getUniqueLotModel.getLstlot().get(position).getLotno());
//            }else {
//                addLots.remove(getUniqueLotModel.getLstlot().get(position).getLotno());
//            }
//            getLotNoList.GetListLot(addLots);// Current lot number
//                                                                // Another lot
//            Log.d("hshsygysgUUU", String.valueOf(addLots));

            //////////////////////////////////////
            if (isChecked){

                getUniqueLotModel.getLstlot().get(position).setChecked(true);
                addLots.add(getUniqueLotModel.getLstlot().get(position).getLotno());
                AppPrefrences.getInstance(context).saveArrayList(addLots,"save_checedbox");

            }else {
                getUniqueLotModel.getLstlot().get(position).setChecked(false);
                addLots.remove(getUniqueLotModel.getLstlot().get(position).getLotno());
                AppPrefrences.getInstance(context).saveArrayList(addLots,"save_checedbox");

            }

            getLotNoList.GetListLot(addLots);// Current lot number



        });

        ////////////////////////////////////////////////////////////////////////////////////////////////





//        if (isSelectionEnabled) {
//
//            holder.cbSelect.setChecked(true);
//        }
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



        String fullPath = getUniqueLotModel.getLstlot().get(position).getDesignpath();

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
                intent.putExtra("productname", "Fabricator Issue"); // Pass the image URL to the ZoomActivity
                context.startActivity(intent);
            }
        });

        Log.d("imaggege", imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.dum_img) // Dummy image while loading
                .error(R.drawable.dum_img )
                .override(200, 200) // fixed width & height in pixels
                .centerCrop()// Dummy image if there's an error
                .into(holder.imageDesign);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                selectedPosition = holder.getAdapterPosition();
//                notifyDataSetChanged();
//                if (myItemClickListener!=null) {
//                    myItemClickListener.onItemClick(position);
//                }
//
//            }
//        });


//        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
////                    holder.cbSelect.setChecked((getUniqueLotModel.getLstlot().get(position).);
////                    addLots.addAll(Arrays.asList(
////                            getUniqueLotModel.getLstlot().get(position).getLotno()// Current lot number
////                                                                 // Another lot
////                    ));
////                    getLotNoList.GetListLot(addLots);
//////                    Toast.makeText(context, ""+addLots.size(), Toast.LENGTH_SHORT).show();
//
//                }
////                else {
////                    addLots.remove(getUniqueLotModel.getLstlot().get(position).getLotno());
////                }
//                Log.d("hshsygysg",getUniqueLotModel.getLstlot().get(position).getLotno());
//            }
//        });
//        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notifyDataSetChanged();
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return getUniqueLotModel.getLstlot().size();
    }
//    @Override
//    public void onViewRecycled(@NonNull FabricViewHolder holder) {
//        super.onViewRecycled(holder);
//
//        // Clean up listeners to avoid memory leaks and avoid multiple listener bindings
//        holder.cbSelect.setOnCheckedChangeListener(null); // Removing the listener
//    }

    public class FabricViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llledgerParty;
        ImageView imageDesign;
        EditText etQty;
        CheckBox cbSelect;
        TextView tv_design,tvQty,tvRepeat;
        public FabricViewHolder(@NonNull View itemView) {
            super(itemView);
            llledgerParty=itemView.findViewById(R.id.llledgerParty);
            tv_design=itemView.findViewById(R.id.tv_design);
            cbSelect=itemView.findViewById(R.id.cbSelect);
            imageDesign=itemView.findViewById(R.id.imageDesign);
            tvQty=itemView.findViewById(R.id.tvQty);
            tvRepeat=itemView.findViewById(R.id.tvRepeat);
            myItemClickListener= (MyItemClickListener) context;
            updateQtyInterFace = (UpdateQtyInterface) context;
            getLotNoList = (GetLotNoList) context;

        }
    }

}

