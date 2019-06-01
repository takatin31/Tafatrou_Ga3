package com.example.light.ramdanapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{

    private List<ProductModel> listProducts;
    private Context context;

    public RecyclerViewAdapter2(List<ProductModel> listProducts, Context context) {
        this.listProducts = listProducts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rawi_info, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int i) {
        final ProductModel productModel = listProducts.get(i);

        final String productName = productModel.getProduct();
        int quantite = productModel.getQuantite();

        if (productName.compareTo("") == 0){
            viewHolder.product.setHint("مادة");
        }else{
            viewHolder.product.setText(productName);
        }

        if (quantite == 0){
            viewHolder.quantite.setHint("كمية");
        }else{
            viewHolder.quantite.setText(String.valueOf(quantite));
        }

        viewHolder.unite.setText(productModel.getUnite());


    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView product;
        public TextView quantite;
        public TextView unite;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.product);
            quantite = itemView.findViewById(R.id.quantite);
            unite = itemView.findViewById(R.id.unite);
        }
    }
}



