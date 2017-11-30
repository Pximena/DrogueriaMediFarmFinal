package com.i044114_i012114.proyectofinalandroid.Adapters;

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
import com.i044114_i012114.proyectofinalandroid.Models.Favorite;
import com.i044114_i012114.proyectofinalandroid.Models.IdUser;
import com.i044114_i012114.proyectofinalandroid.R;
import com.i044114_i012114.proyectofinalandroid.Views.CantidadActivity;
import com.i044114_i012114.proyectofinalandroid.Views.DescriptionActivity;
import com.i044114_i012114.proyectofinalandroid.Views.FavoriteActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by equipo14 on 27/11/17.
 */

public class FavoriteAdapter extends  RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    List<Favorite> favoriteList = new ArrayList<>();
    Context context;
    SqliteHelper sqliteHelper;

    public FavoriteAdapter(List<Favorite> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.textViewid_fav.setText(Integer.toString(favoriteList.get(position).getId_fav()));
        //holder.textViewid_user.setText(Integer.toString(favoriteList.get(position).getId_user()));
        //holder.textViewid_prod.setText(Integer.toString(favoriteList.get(position).getId_prod()));
        //Picasso.with(context).load(contactList.get(position).getUrl()).into(holder.imageView);

        holder.textViewid_fav.setText(favoriteList.get(position).getName());
        //holder.textViewid_prod.setText(favoriteList.get(position).getCantidad());
        Picasso.with(context).load(favoriteList.get(position).getUrl()).into(holder.imageView_url);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteHelper = new SqliteHelper(v.getContext(), "db_contact", null, 1);
                IdUser idUsu = new IdUser();
                SQLiteDatabase db = sqliteHelper.getWritableDatabase();

                db.delete("favorite","id_fav =  " + favoriteList.get(position).getId_fav() +" and id_user =" +idUsu.getIdusu(), null);
                Toast.makeText(context, "eliminado "+idUsu.getIdusu(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, FavoriteActivity.class);
                context.startActivity(intent);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context contextItem = view.getContext();
                Intent intent = new Intent(context, CantidadActivity.class);
                intent.putExtra("id", favoriteList.get(position).getId_prod());
                intent.putExtra("nameprod", favoriteList.get(position).getName());
                intent.putExtra("description", favoriteList.get(position).getDescription());
                intent.putExtra("cantidad", favoriteList.get(position).getCantidad());
                intent.putExtra("url", favoriteList.get(position).getUrl());
                contextItem.startActivity(intent);
                Toast.makeText(context, "Realizar pedido", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewid_fav;
        ImageView imageView_url;
        CheckBox checkBox;
        Button button;


        public ViewHolder(View item) {
            super(item);
            item.setOnClickListener(this);
            textViewid_fav = (TextView) item.findViewById(R.id.id_tv_item_name_fav);
            imageView_url =(ImageView) item.findViewById(R.id.id_img_item_cardview_fav);
            checkBox= (CheckBox) item.findViewById(R.id.id_fav);
            button= (Button) item.findViewById(R.id.id_btn_fav_pedido);

        }

        @Override
        public void onClick(View view) {
            Context contextItem = view.getContext();
            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.putExtra("nameprod",favoriteList.get(getLayoutPosition()).getName() );
            intent.putExtra("description",favoriteList.get(getLayoutPosition()).getDescription());
            intent.putExtra("cantidad", favoriteList.get(getLayoutPosition()).getCantidad());
            intent.putExtra("url", favoriteList.get(getLayoutPosition()).getUrl());
            contextItem.startActivity(intent);
        }

    }
}
