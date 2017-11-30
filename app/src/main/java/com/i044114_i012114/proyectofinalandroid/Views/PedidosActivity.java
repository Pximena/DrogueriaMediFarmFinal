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
import android.widget.TextView;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Adapters.PedidoAdapter;
import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.LoginActivity;
import com.i044114_i012114.proyectofinalandroid.Models.IdUser;
import com.i044114_i012114.proyectofinalandroid.Models.Pedido;
import com.i044114_i012114.proyectofinalandroid.R;

import java.util.ArrayList;
import java.util.List;

public class PedidosActivity extends AppCompatActivity {
Toolbar toolbar;
    SqliteHelper sqliteHelper;
    RecyclerView recyclerViewPedido;
    PedidoAdapter pedidoAdapter;

    List<Pedido> pedidoList = new ArrayList<>();
    IdUser idUsu = new IdUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        showToolbar(getResources().getString(R.string.str_tb_p6));
        sqliteHelper = new SqliteHelper(this, "db_contact", null, 1);

        recyclerViewPedido = (RecyclerView) findViewById(R.id.id_rv_pedi);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPedido.setLayoutManager(linearLayoutManager);

        listPedido();
    }

    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

    }
    public void listPedido(){
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select pe.id_pedi, pe.canti_pedido, pe.id_produc, p.namepro, p.cantidad, p.description, p.url from products p inner join pedido pe on p.id = pe.id_produc where pe.id_usua = "+ idUsu.getIdusu(), null);

        while (cursor.moveToNext()){
            Pedido pro = new Pedido();
            pro.setId_pedi(cursor.getInt(0));
            pro.setCanti_pedido(cursor.getInt(1));
            pro.setId_produc(cursor.getInt(2));
            pro.setName(cursor.getString(3));
            pro.setCantidad(cursor.getString(4));
            pro.setDescription(cursor.getString(5));
            pro.setUrl(cursor.getString(6));
            pedidoList.add(pro);

        }

        cursor.close();

        if (pedidoList.size() != 0){
            processData();
        }else{
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
        }

    }
    public void processData(){
        pedidoAdapter = new PedidoAdapter(pedidoList, getApplicationContext());
        recyclerViewPedido.setAdapter(pedidoAdapter);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_pe, menu);
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
                Intent intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
            case R.id.load3:{
                LoginActivity.changeState(PedidosActivity.this,false);
                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
        }

        return true;

    }

}
