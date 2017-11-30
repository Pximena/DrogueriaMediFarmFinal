package com.i044114_i012114.proyectofinalandroid.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.i044114_i012114.proyectofinalandroid.LoginActivity;
import com.i044114_i012114.proyectofinalandroid.R;
import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity {
    TextView textViewname;
    TextView textViewdesc;
    TextView textViewcanti;
    ImageView imageView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        textViewname= (TextView) findViewById(R.id.id_tv_name);
        textViewdesc = (TextView) findViewById(R.id.id_tv_description);
        //textViewcanti = (TextView) findViewById(R.id.id_tv_cantidad);
        imageView=(ImageView) findViewById(R.id.id_img_imagen);

        textViewname.setText( getIntent().getExtras().getString("nameprod") );
        textViewdesc.setText( getIntent().getExtras().getString("description") );
        //textViewcanti.setText( getIntent().getExtras().getString("cantidad") );
        Picasso.with(this).load(getIntent().getExtras().getString("url")).into(imageView);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        showToolbar(getResources().getString(R.string.str_tb_p4));
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);

    }

    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

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
                LoginActivity.changeState(DescriptionActivity.this,false);
                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
            }
            break;
        }

        return true;

    }
}
