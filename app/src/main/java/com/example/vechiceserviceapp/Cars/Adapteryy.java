package com.example.vechiceserviceapp.Cars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vechiceserviceapp.R;

import java.util.List;

public class Adapteryy extends RecyclerView.Adapter<Adapteryy.MyViewHolder> {

    private Context cContext;
    private List<Carmodelclass> cData;


    public Adapteryy(Context cContext, List<Carmodelclass> cData) {
        this.cContext = cContext;
        this.cData = cData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        LayoutInflater inflater
                = LayoutInflater.from(cContext);
        v = inflater.inflate(R.layout.car_detaileditem, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

          //holder.id.setText(cData.get(position).getId());
         holder.ratings.setText(cData.get(position).getRatings());
        holder.location.setText(cData.get(position).getLocation());
           holder.time.setText(cData.get(position).getTime());
           holder.pickup.setText(cData.get(position).getPickup());
        holder.contactno.setText(cData.get(position).getContactno());
             holder.speciality.setText(cData.get(position).getSpeciality());
          holder.recommended.setText(cData.get(position).getRecommended());
        holder.servicecentername.setText(cData.get(position).getServicecentername());
          holder.description.setText(cData.get(position).getDescription());
          holder.cost.setText(cData.get(position).getCost());


        Glide.with(cContext).load(cData.get(position).getImage())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return cData.size();
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
