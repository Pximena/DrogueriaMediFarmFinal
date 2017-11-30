package com.i044114_i012114.proyectofinalandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.i044114_i012114.proyectofinalandroid.Helpers.SqliteHelper;
import com.i044114_i012114.proyectofinalandroid.Models.IdUser;
import com.i044114_i012114.proyectofinalandroid.Views.ProductListActivity;
import com.i044114_i012114.proyectofinalandroid.Views.RegisterUserActivity;

public class LoginActivity extends AppCompatActivity {

    SqliteHelper sqliteHelper;
    private Cursor fila;
    EditText editTextUser;
    EditText editTextPassword;
    Toolbar toolbar;
    private CheckBox opcionMostrar;
    private EditText Contrasena;
    RadioButton radioButton;

    Boolean isActivateRadioButton;

    private static final String STRING_PREFERENCES ="proyectofinalandroid.Helpers.SqliteHelper";
    private static final String PREFERENCES_ESTADO_BUTTON_SESION ="estado.login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqliteHelper = new SqliteHelper(this, "db_contact", null, 1);

        editTextUser = (EditText) findViewById(R.id.id_et_users);
        editTextPassword = (EditText) findViewById(R.id.id_et_password);

        opcionMostrar = (CheckBox)findViewById(R.id.opcion_mostrar);
        Contrasena = (EditText)findViewById(R.id.id_et_password);
        radioButton= (RadioButton) findViewById(R.id.id_rb_s);
        isActivateRadioButton = radioButton.isChecked();

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);

        showToolbar(getResources().getString(R.string.str_tb_p1));

        if(getState()){
            Intent intent = new Intent(this, ProductListActivity.class);
            this.startActivity(intent);
            finish();

        }

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isActivateRadioButton){
                    radioButton.setChecked(false);
                }
                isActivateRadioButton = radioButton.isChecked();
            }
        });



    }

    public  void  userVali(View view){

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        String stringName = editTextUser.getText().toString();
        String stringPhone = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(stringName)){
            Toast.makeText(this, "El campo de id esta vacio", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(stringPhone)){
            Toast.makeText(this, "El campo nombre esta vacio", Toast.LENGTH_SHORT).show();
        }else {

            String usuario=editTextUser.getText().toString();
            String contrasena=editTextPassword.getText().toString();
            fila=db.rawQuery("select id, cedula, password from users where cedula='"+usuario+"' and password='"+contrasena+"'",null);
            //preguntamos si el cursor tiene algun valor almacenado
            if(fila.moveToFirst()==true){
                //capturamos los valores del cursos y lo almacenamos en variable
                int idusua=fila.getInt(0);
                String usua=fila.getString(1);
                String pass=fila.getString(2);
                saveState();
                //preguntamos si los datos ingresados son iguales
                if (usuario.equals(usua)&&contrasena.equals(pass)) {
                    //si son iguales entonces vamos a otra ventana
                    //Menu es una nueva actividad empty
                    Intent ven = new Intent(this, ProductListActivity.class);
                    IdUser idUsu = new IdUser();
                    idUsu.setIdusu(idusua);
                    startActivity(ven);
                    overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
                    Toast.makeText(this, "Bienvenido a MediFarm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else {
                //limpiamos las las cajas de texto

                editTextUser.setText("");
                editTextPassword.setText("");
                Toast.makeText(this, "El usuario y contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }

        }

    }
    public void mostrarContraseña(View v){
        // Salvar cursor
        int cursor = Contrasena.getSelectionEnd();

        if(opcionMostrar.isChecked()){
            Contrasena.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            Contrasena.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        // Restaurar cursor
        Contrasena.setSelection(cursor);
    }

    public void saveState(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES,  MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(PREFERENCES_ESTADO_BUTTON_SESION,radioButton.isChecked()).apply();

    }

    public boolean getState(){
        SharedPreferences sharedPreferences = getSharedPreferences(STRING_PREFERENCES,  MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREFERENCES_ESTADO_BUTTON_SESION,false);

    }


    public static void changeState(Context c, boolean  b){
        SharedPreferences sharedPreferences = c.getSharedPreferences(STRING_PREFERENCES,  MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(PREFERENCES_ESTADO_BUTTON_SESION,b).apply();

    }



    public void goToResgister (View view){
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);

    }


    public void showToolbar(String title){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

    }



}
