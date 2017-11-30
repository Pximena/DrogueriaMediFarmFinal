package com.i044114_i012114.proyectofinalandroid.Views;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.LoginActivity;
import com.i044114_i012114.proyectofinalandroid.R;
import com.i044114_i012114.proyectofinalandroid.Utilities.Constants;

public class RegisterUserActivity extends AppCompatActivity {


    private Cursor fila;

    TextInputEditText textInputEditTextName;
    TextInputEditText textInputEditTextCedula;
    TextInputEditText textInputEditTextPassword;
    Toolbar toolbar;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.id_tv_name);
        textInputEditTextCedula = (TextInputEditText) findViewById(R.id.id_tv_cedula);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.id_tv_password);

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        showToolbar(getResources().getString(R.string.str_tb_p2));
        sqliteHelper = new SqliteHelper(this, "db_contact", null, 1);
    }

    public void onClickCreateUser(View view){

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String cedula=textInputEditTextCedula.getText().toString();
        fila=db.rawQuery("select cedula from users where cedula='"+cedula+"'",null);

        if(fila.moveToFirst()==true){
            String ced=fila.getString(0);
            if (cedula.equals(ced)) {
                textInputEditTextName.setText("");
                textInputEditTextPassword.setText("");
                textInputEditTextCedula.setText("");
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                     }
             }else {
                String stringName = textInputEditTextName.getText().toString();
                String stringCedula = textInputEditTextCedula.getText().toString();
                String stringPassword = textInputEditTextPassword.getText().toString();

                if (TextUtils.isEmpty(stringName)) {
                    Toast.makeText(this, "El campo de nombre esta vacio", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringCedula)) {
                    Toast.makeText(this, "El campo de cedula esta vacio", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringPassword)) {
                    Toast.makeText(this, "El campo de contraseña esta vacio", Toast.LENGTH_SHORT).show();

                } else {
                    createUser();
                }

            }
    }


    public void createUser(){
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TABLA_FIELD_NAME, textInputEditTextName.getText().toString());
        values.put(Constants.TABLA_FIELD_CEDULA, textInputEditTextCedula.getText().toString());
        values.put(Constants.TABLA_FIELD_PASSWORD, textInputEditTextPassword.getText().toString());

        Long idResult = db.insert(Constants.TABLA_NAME_USERS, Constants.TABLA_FIELD_ID, values);

        textInputEditTextName.setText("");
        textInputEditTextCedula.setText("");
        textInputEditTextPassword.setText("");

       Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
    }
    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

    }
}
