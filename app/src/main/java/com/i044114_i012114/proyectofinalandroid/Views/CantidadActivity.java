package com.i044114_i012114.proyectofinalandroid.Views;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.LoginActivity;
import com.i044114_i012114.proyectofinalandroid.Models.IdUser;
import com.i044114_i012114.proyectofinalandroid.R;
import com.i044114_i012114.proyectofinalandroid.Utilities.Constants;
import com.squareup.picasso.Picasso;

public class CantidadActivity extends AppCompatActivity {
Toolbar toolbar;
    TextView textViewname;
    TextView textViewdescription;
    ImageView imageViewcantidad;
    TextInputEditText textInputEditTextcan;
    TextInputEditText textInputEditTextdirec;
    SqliteHelper sqliteHelper;
    private Cursor fila;
    private Cursor can;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantidad);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        showToolbar(getResources().getString(R.string.str_tb_p7));
        textViewname= (TextView) findViewById(R.id.id_tv_name_cantidad);
        textViewdescription= (TextView) findViewById(R.id.id_tv_description_cantidad);

        imageViewcantidad = (ImageView) findViewById(R.id.id_img_imagen_cantidad);
        textInputEditTextcan = (TextInputEditText)  findViewById(R.id.id_cant_pedido);
        textInputEditTextdirec = (TextInputEditText) findViewById(R.id.id_direc_pedido);
        textViewname.setText( getIntent().getExtras().getString("nameprod") );
        textViewdescription.setText( getIntent().getExtras().getString("description") );
        Picasso.with(this).load(getIntent().getExtras().getString("url")).into(imageViewcantidad);
        sqliteHelper = new SqliteHelper(this, "db_contact", null, 1);
    }

    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
    }
    public void hacerPedido(View view){
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        IdUser idu = new IdUser();
        ContentValues values = new ContentValues();
        values.put(Constants.TABLA_FIELD_ID_USUA, idu.getIdusu());
        values.put(Constants.TABLA_FIELD_ID_PRODUC, getIntent().getExtras().getInt("id"));
        values.put(Constants.TABLA_FIELD_ID_CANTIDAD_PEDIDO, Integer.parseInt(textInputEditTextcan.getText().toString()));
        values.put(Constants.TABLA_FIELD_ID_DIRECCION, textInputEditTextdirec.getText().toString());
        db.insert(Constants.TABLA_NAME_PEDIDO, Constants.TABLA_FIELD_ID_PEDI, values);

       fila = db.rawQuery(" select cantidad from products where id="+ getIntent().getExtras().getInt("id"), null);

        if(fila.moveToFirst()==true) {
            Integer ced = fila.getInt(0);
            if (ced==0){
                Toast.makeText(this, "El producto esta agotado " , Toast.LENGTH_SHORT).show();
            }else if(ced<Integer.parseInt(textInputEditTextcan.getText().toString())){
                Toast.makeText(this, "No hay suficientes productos disponibles " , Toast.LENGTH_SHORT).show();
            }else{
                Integer res = ced-Integer.parseInt(textInputEditTextcan.getText().toString());
                db.execSQL("update products set cantidad = "+ res+ " where id = "+getIntent().getExtras().getInt("id"));
                Toast.makeText(this, "Se realizo satisfactoriamente su pedido "   , Toast.LENGTH_SHORT).show();

            }

        }

    }

    public void listarLosPedidos(View view){
        Intent intent = new Intent(this,PedidosActivity.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_des, menu);
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
                Intent intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
            case R.id.load4:{
                LoginActivity.changeState(CantidadActivity.this,false);
                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
        }

        return true;

    }
}
