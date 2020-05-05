package com.lako.walletcount;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class RecyclerView extends androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerView";

    private ArrayList<String> locationName = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();
    private Context context;

    public RecyclerView(ArrayList<String> locationName, ArrayList<String> price, Context context) {
        this.locationName = locationName;
        this.price = price;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.location.setText("Test");
        holder.price.setText("1.00");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        ImageView icon;
        TextView location;
        TextView price;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageView);
            location = itemView.findViewById(R.id.textView5);
            price = itemView.findViewById(R.id.textView6);
            layout = itemView.findViewById(R.id.listLayouts);
        }
    }
}
