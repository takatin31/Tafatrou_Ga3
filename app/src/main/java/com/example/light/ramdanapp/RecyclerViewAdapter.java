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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<ProductModel> listProducts;
    private Context context;
    String mail;

    public RecyclerViewAdapter(List<ProductModel> listProducts, Context context, String mail) {
        this.listProducts = listProducts;
        this.context = context;
        this.mail = mail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
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
            viewHolder.quantite.setText("");
            viewHolder.quantite.setHint("كمية");
        }else{
            viewHolder.quantite.setText(String.valueOf(quantite));
        }

        viewHolder.unite.setText(productModel.getUnite());

        viewHolder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.menuBtn);
                popupMenu.inflate(R.menu.item_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.remove:
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();

                                DatabaseReference infoRef = myRef.child("Jam3iyates");
                                String mail2 = mail.replace('.', ',');
                                DatabaseReference jamMailRef = infoRef.child(mail2);
                                DatabaseReference productsRef = jamMailRef.child("products");
                                DatabaseReference productRef = productsRef.child(productName);

                                productRef.removeValue();
                                notifyDataSetChanged();
                                break;
                            case R.id.modify:
                                Intent intent = new Intent(context, Pop.class);
                                intent.putExtra("product", productModel);
                                intent.putExtra("email", mail);
                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       public TextView product;
       public TextView quantite;
       public TextView unite;
       public Button menuBtn;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           product = itemView.findViewById(R.id.product);
           quantite = itemView.findViewById(R.id.quantite);
           unite = itemView.findViewById(R.id.unite);
           menuBtn = itemView.findViewById(R.id.menuBtn);
       }
   }
}



