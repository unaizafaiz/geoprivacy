package com.hci.geoprivacy.geoprivacy.MyRecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hci.geoprivacy.geoprivacy.AppList;
import com.hci.geoprivacy.geoprivacy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by unaizafaiz on 11/10/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<AppList> app_list;
    private Context context;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<AppList> app_list) {
        this.mInflater = LayoutInflater.from(context);
        this.app_list = app_list;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);    }

    // binds the data to the imageView in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       //  Log.d("App name ", app_list.get(position).getAppName());
       //  Log.d("Permission for the app", app_list.get(position).getPermissions());
        //holder.myTextView.setText(app_list.get(position).getAppName());
        //Picasso.with(context).load(app_list.get(position).getAppIcon()).resize(2,2).into(holder.myAppView);

        holder.myAppView.setImageDrawable(app_list.get(position).getIcon());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return app_list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myAppView;
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myAppView = itemView.findViewById(R.id.appIcon);
          //  myTextView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return app_list.get(id).getAppName();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
