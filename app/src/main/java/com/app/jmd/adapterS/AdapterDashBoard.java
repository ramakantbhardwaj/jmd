package com.app.jmd.adapterS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jmd.AppPrefrences;
import com.app.jmd.R;
import com.app.jmd.activity.BallanceStockActivity;
import com.app.jmd.activity.CostSheetActivity;
import com.app.jmd.activity.FabricatorIssueActivity;
import com.app.jmd.activity.PendingProductionActivity;
import com.app.jmd.activity.PressIssueActivity;
import com.app.jmd.activity.ProductionActivity;

public class AdapterDashBoard extends RecyclerView.Adapter<AdapterDashBoard.MyViewHolder> {

    Context context;
    public AdapterDashBoard(Context context){this.context=context;}
    String appType=AppPrefrences.getInstance(context).getDataFromPrefs("app_type");
    int arrImgAdmin[]={R.drawable.cost_sheet,R.drawable.production,R.drawable.fabrication,R.drawable.press_issue,R.drawable.pending_production,R.drawable.balance};
    String arrAdminTitle[] = {"Cost Sheet","Production Planning", "Fabrication Issue","Press Issue","Pending Production Planning","Balance Stock at Job Work"};

    int arrImgCustomer[]={R.drawable.ledger_2x,R.drawable.sales_report,R.drawable.pending_order,R.drawable.courier_1,R.drawable.branch_icon,R.drawable.complain_icon,R.drawable.contact_icon};
    String arrCustomerTitle[] = {"Ledger","Sales Reports", "Pending Order","Courier","Branch","Complain","Contact Us"};

    int arrImgSalesman[]={R.drawable.order_booking,R.drawable.sales_report,R.drawable.pending_order,R.drawable.courier_1,R.drawable.chk_cred_icon,R.drawable.branch_icon,R.drawable.contact_icon};
    String arrSalesmanTitle[] = {"Order Booking","Sales Reports", "Pending Order","Courier","Check Credit Limit","Branch","Contact Us"};

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dashboard,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        if (appType.equalsIgnoreCase("A")) {

            holder.iv_logo.setImageResource(arrImgAdmin[position]);
            holder.tv_title.setText(arrAdminTitle[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, CostSheetActivity.class);
                        context.startActivity(intent);
                    }
                    if (position == 1) {
                        Intent intent = new Intent(context, ProductionActivity.class);
                        context.startActivity(intent);
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                    if (position == 2) {
                        Intent intent = new Intent(context, FabricatorIssueActivity.class);
                        context.startActivity(intent);


                    }
                    if (position == 3) {
                        Intent intent = new Intent(context, PressIssueActivity.class);
                        context.startActivity(intent);
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();
                    }
                    if (position == 4) {
                        Intent intent = new Intent(context, PendingProductionActivity.class);
                        context.startActivity(intent);
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                    if (position == 5) {
                        Intent intent = new Intent(context, BallanceStockActivity.class);
                        context.startActivity(intent);
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
//                    if (position == 6) {
//                        Intent intent = new Intent(context, BranchActivity.class);
//                        context.startActivity(intent);
////                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();
//
//                    }
//                    if (position == 7) {
//                        Intent intent = new Intent(context, ComplaintActivity.class);
//                        context.startActivity(intent);
////                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();
//
//                    }
//                    if (position == 8) {
//                        Intent intent = new Intent(context, ContactUsActivity.class);
//                        context.startActivity(intent);
////                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();
//
//                    }
                }
            });

        if (appType.equalsIgnoreCase("U")) {
            holder.iv_logo.setImageResource(arrImgCustomer[position]);
            holder.tv_title.setText(arrCustomerTitle[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(context, LedgerReportActivity.class);
//                        context.startActivity(intent);
                    }
                    if (position == 1) {
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(context, SalesReportActivity.class);
//                        context.startActivity(intent);
                    }
                    if (position == 2) {
//                        Intent intent = new Intent(context, OdrerCompeteActivity.class);
//                        context.startActivity(intent);
                    }
                    if (position == 3) {
//
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                    if (position == 4) {
//                        Intent intent = new Intent(context, BranchActivity.class);
//                        context.startActivity(intent);
//
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                    if (position == 5) {
//                        Intent intent = new Intent(context, ComplaintActivity.class);
//                        context.startActivity(intent);
//
//                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                    if (position == 6) {
//
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        if (appType.equalsIgnoreCase("S")) {
            holder.iv_logo.setImageResource(arrImgSalesman[position]);
            holder.tv_title.setText(arrSalesmanTitle[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
//                        Intent intent = new Intent(context, NewOrderActivity.class);
//                        context.startActivity(intent);
                    }
                    if (position == 1) {
//                        Intent intent = new Intent(context, SalesReportActivity.class);
//                        context.startActivity(intent);
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
                    if (position == 2) {
//                        Intent intent = new Intentent);t(context, OdrerCompeteActivity.class);
//                        context.startActivity(in
                    }
                    if (position == 3) {
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();

                    }
//                    if (position == 4) {
//                        Intent intent = new Intent(context, CheckCreditLimitActivity.class);
//                        context.startActivity(intent);
//
//                    }
//                    if (position == 5) {
//                        Intent intent = new Intent(context, BranchActivity.class);
//                        context.startActivity(intent);
//
//                    }
                    if (position == 6) {
                        Toast.makeText(context, "Arriving Soon", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    @Override
    public int getItemCount() {

            return arrAdminTitle.length;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_logo;
        TextView tv_title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_logo =  itemView.findViewById(R.id.iv_logo);
            tv_title =  itemView.findViewById(R.id.tv_title);
        }
    }
}
