package com.astdev.indexeye.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.astdev.indexeye.R;
import com.astdev.indexeye.models.FuitesModel;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

    private final List<FuitesModel> listFuite;
    public static String strPosition, strShowName, strShowPhone, strAtHome, strQuartier, strDescription;
    public Adapter(List<FuitesModel> listFuite) {
        this.listFuite = listFuite;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fuites_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        try{
            String name = listFuite.get(position).getNomPrenom();
            String phone = listFuite.get(position).getPhone();
            String lieu = listFuite.get(position).getAtHome();
            String localisation = listFuite.get(position).getPosition();
            String quartier = listFuite.get(position).getQuartier();
            String decription = listFuite.get(position).getDescription();

            holder.setData(name, quartier);

            holder.btnVoir.setOnClickListener(view -> {
                //view.getContext().startActivity(new Intent(view.getContext(), FuitesDetails.class));

                strPosition = localisation;
                strShowName = name;
                strShowPhone = phone;
                strAtHome = lieu;
                strQuartier = quartier;
                strDescription = decription;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listFuite.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private final TextView txtView;
        final Button btnVoir;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.txtView);
            btnVoir = itemView.findViewById(R.id.btnVoir);
        }

        @SuppressLint("SetTextI18n")
        public void setData(String nomPrenom, String quartier){
            txtView.setText(nomPrenom+" a signalé(e) une fuite à "+quartier);
        }
    }
}
