package com.i044114_i012114.proyectofinalandroid.Views;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Adapters.ProductAdapter;
import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.LoginActivity;
import com.i044114_i012114.proyectofinalandroid.Models.Favorite;
import com.i044114_i012114.proyectofinalandroid.Models.Product;
import com.i044114_i012114.proyectofinalandroid.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView recyclerViewContacts;
    ProductAdapter productosAdapter;
    List<Product> productList = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerViewContacts = (RecyclerView) findViewById(R.id.id_rv_contacts);
        sqliteHelper = new SqliteHelper(this, "db_contact", null, 1);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        showToolbar(getResources().getString(R.string.str_tb_p3));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContacts.setLayoutManager(linearLayoutManager);

        listProduct();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.id_fb_cs);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.changeState(ProductListActivity.this,false);
                finish();
            }
        });

    }
    public void listProduct(){
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id,namepro,description,cantidad,url from products order by id desc", null);

        while (cursor.moveToNext()){
            Product product = new Product();
            product.setId(cursor.getInt(0));
            product.setNamepro(cursor.getString(1));
            product.setDescription(cursor.getString(2));
            product.setCantidad(cursor.getInt(3));
            product.setUrl(cursor.getString(4));
            productList.add(product);
        }

        cursor.close();

        if (productList.size() != 0){
            processData();
        }else{
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
        }
    }

    public void processData(){
        productosAdapter = new ProductAdapter(productList, getApplicationContext());
        recyclerViewContacts.setAdapter(productosAdapter);
    }
    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_total, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.load1:{
                Intent intent = new Intent(this, FavoriteActivity.class);
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
                LoginActivity.changeState(ProductListActivity.this,false);
                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
        }

        return true;

    }
}
