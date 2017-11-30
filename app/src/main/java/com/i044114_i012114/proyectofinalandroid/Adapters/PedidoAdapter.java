package com.i044114_i012114.proyectofinalandroid.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.i044114_i012114.proyectofinalandroid.Models.Pedido;
import com.i044114_i012114.proyectofinalandroid.R;
import com.i044114_i012114.proyectofinalandroid.Utilities.Constants;
import com.i044114_i012114.proyectofinalandroid.Views.DescriptionActivity;
import com.i044114_i012114.proyectofinalandroid.Views.FavoriteActivity;
import com.i044114_i012114.proyectofinalandroid.Views.PedidosActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 29/11/2017.
 */

public class PedidoAdapter extends  RecyclerView.Adapter<PedidoAdapter.ViewHolder> {
    List<Pedido> pedidoList = new ArrayList<>();
    Context context;
    IdUser idUser = new IdUser();
    SqliteHelper sqliteHelper;
    private Cursor fila;

    public PedidoAdapter(List<Pedido> pediList, Context context) {
        this.pedidoList = pediList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textViewna.setText(pedidoList.get(position).getName());
        holder.textViewcant.setText(pedidoList.get(position).getCantidad());

        Picasso.with(context).load(pedidoList.get(position).getUrl()).into(holder.imageView_url);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteHelper = new SqliteHelper(context, "db_contact", null, 1);
                SQLiteDatabase db = sqliteHelper.getWritableDatabase();
                if (holder.checkBox.isChecked()) {
                    fila = db.rawQuery("select id_prod from favorite where id_prod ='" + pedidoList.get(position).getId_produc() + "'", null);

                    if (fila.moveToFirst() == true) {
                        Integer ced = fila.getInt(0);
                        if (pedidoList.get(position).getId_produc() == (ced)) {

                        }

                    Toast.makeText(context, "El producto ya existe en su lista de favoritos " + ced, Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues values = new ContentValues();
                        values.put(Constants.TABLA_FIELD_ID_US, idUser.getIdusu());
                        values.put(Constants.TABLA_FIELD_ID_PROD, pedidoList.get(position).getId_produc());
                        db.insert(Constants.TABLA_NAME_FAVORITE, Constants.TABLA_FIELD_ID_FAV, values);
                        Toast.makeText(context, "Agregado a la lista de favoritos ", Toast.LENGTH_SHORT).show();
                    }
                    }else{
                        Toast.makeText(context, "eliminado " + idUser.getIdusu(), Toast.LENGTH_SHORT).show();
                        db.execSQL("delete from favorite where id_fav = (select id_fav from favorite where id_user =" + idUser.getIdusu() + " and id_prod = " + pedidoList.get(position).getId_produc() + " )");
                    }
                }

        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteHelper = new SqliteHelper(context, "db_contact", null, 1);
                SQLiteDatabase db = sqliteHelper.getWritableDatabase();

                fila = db.rawQuery(" select cantidad from products where id="+ pedidoList.get(position).getId_produc(), null);
                if(fila.moveToFirst()==true) {
                    Integer ced = fila.getInt(0);
                        Integer res = ced + pedidoList.get(position).getCanti_pedido();
                        db.execSQL("update products set cantidad = "+ res+ " where id = "+ pedidoList.get(position).getId_produc());
                        db.execSQL("delete from pedido where id_pedi= "+pedidoList.get(position).getId_pedi()+" and id_usua= "+idUser.getIdusu());
                    Toast.makeText(context, "El pedido fue eliminado"+ pedidoList.get(position).getId_pedi() , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PedidosActivity.class);
                    context.startActivity(intent);
                    }

            }
        });

        }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewna;
        TextView textViewcant;
        ImageView imageView_url;
        CheckBox checkBox;
        Button button;


        public ViewHolder(View item) {
            super(item);
            item.setOnClickListener(this);

            textViewna = (TextView) item.findViewById(R.id.id_tv_item_prod_name);
            textViewcant = (TextView) item.findViewById(R.id.id_tv_item_prod_can);
            imageView_url = (ImageView) item.findViewById(R.id.id_img_item_prod_cardview);
            checkBox = (CheckBox) item.findViewById(R.id.id_fav_pedi);
            button = (Button) item.findViewById(R.id.id_btn_pedi_eliminar);

        }
        @Override
        public void onClick(View view) {
            Context contextItem = view.getContext();
            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.putExtra("nameprod", pedidoList.get(getLayoutPosition()).getName() );
            intent.putExtra("description", pedidoList.get(getLayoutPosition()).getDescription());
            intent.putExtra("cantidad", pedidoList.get(getLayoutPosition()).getCantidad());
            intent.putExtra("url", pedidoList.get(getLayoutPosition()).getUrl());
            contextItem.startActivity(intent);
        }

    }
}
