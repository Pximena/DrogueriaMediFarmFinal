package com.i044114_i012114.proyectofinalandroid.Views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Adapters.FavoriteAdapter;
import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.LoginActivity;
import com.i044114_i012114.proyectofinalandroid.Models.Favorite;
import com.i044114_i012114.proyectofinalandroid.Models.IdUser;
import com.i044114_i012114.proyectofinalandroid.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    SqliteHelper sqliteHelper;
    RecyclerView recyclerViewFavorite;
    FavoriteAdapter favoriteAdapter;
    List<Favorite> favoriteList = new ArrayList<>();
    IdUser idUsu = new IdUser();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        sqliteHelper = new SqliteHelper(this, "db_contact", null, 1);

        recyclerViewFavorite = (RecyclerView) findViewById(R.id.id_rv_contacts);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        showToolbar(getResources().getString(R.string.str_tb_p5));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewFavorite.setLayoutManager(linearLayoutManager);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);

        listFavorite();

    }

    public void listFavorite(){

        SQLiteDatabase db = sqliteHelper.getReadableDatabase();



        Cursor cursor = db.rawQuery("select f.id_fav, f.id_prod, p.namepro, p.cantidad, p.description, p.url from products p inner join favorite f on p.id = f.id_prod where f.id_user = "+ idUsu.getIdusu(), null);

        while (cursor.moveToNext()){
            Favorite favorite = new Favorite();
            favorite.setId_fav(cursor.getInt(0));
            favorite.setId_prod(cursor.getInt(1));
            favorite.setName(cursor.getString(2));
            favorite.setCantidad(cursor.getString(3));
            favorite.setDescription(cursor.getString(4));
            favorite.setUrl(cursor.getString(5));
           favoriteList.add(favorite);

        }

        cursor.close();

        if (favoriteList.size() != 0){
            processData();
        }else{
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
        }
    }

    public void processData(){
        favoriteAdapter = new FavoriteAdapter(favoriteList, getApplicationContext());
        recyclerViewFavorite.setAdapter(favoriteAdapter);
    }


    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.load1:{
                Intent intent = new Intent(this, ProductListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
            case R.id.load2:{
                Intent intent = new Intent(this, PedidosActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
            case R.id.load3:{
                LoginActivity.changeState(FavoriteActivity.this,false);
                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
        }

        return true;

    }
}