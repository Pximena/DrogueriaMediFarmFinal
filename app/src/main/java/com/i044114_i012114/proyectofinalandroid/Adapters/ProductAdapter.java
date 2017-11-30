package com.i044114_i012114.proyectofinalandroid.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.Models.IdUser;
import com.i044114_i012114.proyectofinalandroid.Models.Product;
import com.i044114_i012114.proyectofinalandroid.R;
import com.i044114_i012114.proyectofinalandroid.Utilities.Constants;
import com.i044114_i012114.proyectofinalandroid.Views.CantidadActivity;
import com.i044114_i012114.proyectofinalandroid.Views.DescriptionActivity;
import com.i044114_i012114.proyectofinalandroid.Views.FavoriteActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 21/11/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    SqliteHelper sqliteHelper;

    private Cursor fila;
    List<Product> productList = new ArrayList<>();
    Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textViewName.setText(productList.get(position).getNamepro());
        //holder.textViewDescription.setText(contactList.get(position).getDescription());
       // holder.textViewCantidad.setText(productList.get(position).getCantidad());
        Picasso.with(context).load(productList.get(position).getUrl()).into(holder.imageView);


        holder.checkBoxfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteHelper = new SqliteHelper(context, "db_contact", null, 1);
                IdUser idUsu = new IdUser();
                SQLiteDatabase db = sqliteHelper.getWritableDatabase();
                if (holder.checkBoxfav.isChecked()) {

                    fila = db.rawQuery("select id_prod from favorite where id_prod ='" + productList.get(position).getId() + "'", null);

                    if (fila.moveToFirst() == true) {
                        String ced = fila.getString(0);
                        if (productList.get(position).getId().equals(ced)) {

                        }

                        Toast.makeText(context, "El producto ya existe en su lista de favoritos " + ced, Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues values = new ContentValues();
                        values.put(Constants.TABLA_FIELD_ID_US, idUsu.getIdusu());
                        values.put(Constants.TABLA_FIELD_ID_PROD, productList.get(position).getId());
                        db.insert(Constants.TABLA_NAME_FAVORITE, Constants.TABLA_FIELD_ID_FAV, values);
                        Toast.makeText(context, "Agregado a la lista de favoritos ", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(context, "eliminado " + idUsu.getIdusu(), Toast.LENGTH_SHORT).show();
                    db.execSQL("delete from favorite where id_fav = (select id_fav from favorite where id_user =" + idUsu.getIdusu() + " and id_prod = " + productList.get(position).getId() + " )");

                }


            }
        });
        holder.buttonpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context contextItem = view.getContext();
                Intent intent = new Intent(context, CantidadActivity.class);
                intent.putExtra("id", productList.get(position).getId());
                intent.putExtra("nameprod", productList.get(position).getNamepro());
                intent.putExtra("description", productList.get(position).getDescription());
                intent.putExtra("cantidad", productList.get(position).getCantidad());
                intent.putExtra("url", productList.get(position).getUrl());
                contextItem.startActivity(intent);
                Toast.makeText(context, "Realizar pedido", Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        TextView textViewDescription;
        TextView textViewCantidad;
        ImageView imageView;
        CheckBox checkBoxfav;
        Button buttonpedido;


        public ViewHolder(View item) {
            super(item);
            item.setOnClickListener(this);

            textViewName = (TextView) item.findViewById(R.id.id_tv_item_name);
            //textViewDescription = (TextView) item.findViewById(R.id.id_tv_item_des);
            //textViewCantidad = (TextView) item.findViewById(R.id.id_tv_item_can);
            imageView = (ImageView) item.findViewById(R.id.id_img_item_cardview);
            checkBoxfav = (CheckBox) item.findViewById(R.id.id_fav_pa);
            buttonpedido = (Button) item.findViewById(R.id.id_btn_pedido);
      }

        @Override
        public void onClick(View view) {
            Context contextItem = view.getContext();

            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.putExtra("nameprod", productList.get(getLayoutPosition()).getNamepro());
            intent.putExtra("description", productList.get(getLayoutPosition()).getDescription());
            intent.putExtra("cantidad", productList.get(getLayoutPosition()).getCantidad());
            intent.putExtra("url", productList.get(getLayoutPosition()).getUrl());
            contextItem.startActivity(intent);

            //String valor = Integer.toString(albumModelList.get(getLayoutPosition()).getId());
            //Toast.makeText(contextItem, valor, Toast.LENGTH_SHORT).show();
        }
    }
}
