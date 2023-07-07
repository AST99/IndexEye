package com.astdev.indexeye.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astdev.indexeye.R;
import com.astdev.indexeye.models.PlumberModels;

import java.util.List;

public class PlumberListAdapter extends RecyclerView.Adapter<PlumberListAdapter.Viewholder> {

    private final List<PlumberModels> modelList;

    public static String strShowName, strShowPhone, strShowMail, strShowPass;


    public PlumberListAdapter(List<PlumberModels> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plumber_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        try{
            String name = modelList.get(position).getName();
            String phone = modelList.get(position).getPhone();
            String mail = modelList.get(position).getMail();
            String pass = modelList.get(position).getPassWrd();
            holder.setData(name, mail);

            /*holder.cardView.setOnClickListener(view -> {
               // view.getContext().startActivity(new Intent(view.getContext(), UserProfil.class));

                strShowName = name;
                strShowPhone = phone;
                strShowMail = mail;
                strShowPass = pass;
            });*/


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private final TextView name, mail;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.plumberName);
            mail = itemView.findViewById(R.id.plumberPhone);
            //cardView = itemView.findViewById(R.id.cardV);
        }

        public void setData(String txtNom, String txtmail){
            name.setText(txtNom);
            mail.setText(txtmail);
        }
    }
}
