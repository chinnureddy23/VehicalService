package com.example.vechiceserviceapp.Bikes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vechiceserviceapp.R;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private Context bContext;
    private List<Bikemodelclass> bData;

    private boolean isLoading = true;

    @Override
    public int getItemViewType(int position) {
        return isLoading && position == getItemCount() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public Adaptery(Context bContext, List<Bikemodelclass> bData) {
        this.bContext = bContext;
        this.bData = bData;
    }
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void bindView() {

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        LayoutInflater inflater
                = LayoutInflater.from(bContext);
        v = inflater.inflate(R.layout.bike_detaileditem, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.id.setText(cData.get(position).getId());
        holder.ratings.setText(bData.get(position).getRatings());
        holder.location.setText(bData.get(position).getLocation());
        holder.time.setText(bData.get(position).getTime());
        holder.pickup.setText(bData.get(position).getPickup());
        holder.contactno.setText(bData.get(position).getContactno());
        holder.speciality.setText(bData.get(position).getSpeciality());
        holder.recommended.setText(bData.get(position).getRecommended());
        holder.servicecentername.setText(bData.get(position).getServicecentername());
        holder.description.setText(bData.get(position).getDescription());
        holder.cost.setText(bData.get(position).getCost());

        Glide.with(bContext).load(bData.get(position).getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return bData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // TextView id;
        TextView servicecentername;
        ImageView image;
        TextView speciality;
        TextView location;
        TextView ratings;
        TextView pickup;
        TextView contactno;
        TextView description;
        TextView time;
        TextView recommended;

        TextView cost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // id = itemView.findViewById(R.id.id_txt);
            description = itemView.findViewById(R.id.id_descriptiontxt);
            servicecentername = itemView.findViewById(R.id.id_servicecenternametxt);
            ratings = itemView.findViewById(R.id.id_ratingstxt);
            recommended = itemView.findViewById(R.id.id_recommendedtxt);
            time = itemView.findViewById(R.id.id_timetxt);
            pickup = itemView.findViewById(R.id.id_pickuptxt);
            contactno = itemView.findViewById(R.id.id_contactnotxt);
            speciality = itemView.findViewById(R.id.id_specialitytxt);
            location = itemView.findViewById(R.id.id_locationtxt);
            image = itemView.findViewById(R.id.id_imageview);
            cost=itemView.findViewById(R.id.id_cost);

        }
    }

}
