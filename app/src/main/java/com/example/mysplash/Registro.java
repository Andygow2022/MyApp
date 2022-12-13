package com.example.mysplash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mysplash.des.MyDesUtil;
import com.example.mysplash.json.MyData;
import com.example.mysplash.json.MyInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class Registro extends AppCompatActivity implements View.OnClickListener {
    private Button button4,pick;
    private EditText usuario,pswd,mail,num,fec,nombre;
    private CheckBox box1, box2, box3;
    private Spinner spinner;
    private RadioButton r1,r2;
    private int dia, mes , ano;
    private static final String TAG = "MainActivity";
    public static final String archivo = "S.json";
    String json = null;
    public static String usr,password,email,numero,fecha,region,nom;
    public static boolean sw= false;
    public static boolean activado;
    public static String[] box = new String[3];
    public static List<MyInfo> list =new ArrayList<MyInfo>();
    public static List<MyData> lista;
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    public MyDesUtil myDesUtil= new MyDesUtil().addStringKeyBase64(KEY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Contraseñas nuevas
        lista= new ArrayList<>();
        MyData myData=null;
        spinner = findViewById(R.id.spinner);
        String [] opciones = {"Norte","Sur","Centro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner.setAdapter(adapter);
        pick = findViewById(R.id.pick);
        button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
         usuario = findViewById(R.id.usuario);
         pswd = findViewById(R.id.pswd);
         mail = findViewById(R.id.email);
         num = findViewById(R.id.num);
         fec = findViewById(R.id.fec);
         fec.setEnabled(false);
         nombre = findViewById(R.id.nombre);
         box1 = findViewById(R.id.checkBox1);
         box2 = findViewById(R.id.checkBox2);
         box3 = findViewById(R.id.checkBox3);
         r1 = findViewById(R.id.radioButton3);
         r2 = findViewById(R.id.radioButton4);
        Switch switch1 = findViewById(R.id.switch1);
        Read();
        json2List(json);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, activity_login.class);
                startActivity(intent);
            }
        });
        pick.setOnClickListener(this);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfo info= new MyInfo();

                usr= String.valueOf(usuario.getText());
                password = String.valueOf(pswd.getText());
                email= String.valueOf(mail.getText());
                numero = String.valueOf(num.getText());
                fecha = String.valueOf(fec.getText());
                region = spinner.getSelectedItem().toString();
                nom = String.valueOf(nombre.getText());

                if(box1.isChecked()==true){
                    box[0]="opcion1";
                }else{
                    box[0]="no";
                }
                if(box2.isChecked()==true){
                    box[1]="opcion2";
                }else{
                    box[1]="no";
                }
                if(box3.isChecked()==true){
                    box[2]="opcion3";
                }else{
                    box[2]="no";
                }
                if(r1.isChecked()==true){
                    activado=true;
                }
                if(r2.isChecked()==true){
                    activado=true;
                }
                if(switch1.isChecked()){
                    sw= true;
                }
                //Validaciones
                if(usr.equals("")||password.equals("")||email.equals("")){
                    Log.d(TAG,"vacio");
                    Log.d(TAG,usr);
                    Log.d(TAG,password);
                    Log.d(TAG,email);
                    Toast.makeText(getApplicationContext(), "LLena los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(Metodos.validarEmail(email)){
                        if(list.isEmpty()){
                            Log.d(TAG,"lleno");
                            Metodos.fillInfo(info);
                            List2Json(info,list);
                        }else{
                            if(Metodos.usuarios(list,usr,email)){
                                Log.d(TAG,"esta ocupado mano");
                                Toast.makeText(getApplicationContext(), "El nombre de usuario o correo, están ocupado, cambialo", Toast.LENGTH_LONG).show();
                            }else{
                                Metodos.fillInfo(info);
                                info.setContras(lista);
                                List2Json(info,list);
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Introduzca un correo válido", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
    public void List2Json(MyInfo info,List<MyInfo> list){
        Gson gson =null;
        String json= null;
        gson =new Gson();
        list.add(info);
        json =gson.toJson(list, ArrayList.class);
        if (json == null)
        {
            Log.d(TAG, "Error json");
        }
        else
        {
            Log.d(TAG, json);
            json=myDesUtil.cifrar(json);
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }
    private boolean writeFile(String text){
        File file =null;
        FileOutputStream fileOutputStream =null;
        try{
            file=getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write( text.getBytes(StandardCharsets.UTF_8) );
            fileOutputStream.close();
            Log.d(TAG, "Hola");
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }

    public boolean Read(){
        if(!isFileExits()){
            return false;
        }
        File file = getFile();
        FileInputStream fileInputStream = null;
        byte[] bytes = null;
        bytes = new byte[(int)file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            json=new String(bytes);
            Log.d(TAG,json);
            json= myDesUtil.desCifrar(json);
            Log.d(TAG,json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private boolean isFileExits( )
    {
        File file = getFile( );
        if( file == null )
        {
            return false;
        }
        return file.isFile() && file.exists();
    }
    public void json2List( String json)
    {
        Gson gson = null;
        String mensaje = null;
        if (json == null || json.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Error json null or empty", Toast.LENGTH_LONG).show();
            return;
        }
        gson = new Gson();
        Type listType = new TypeToken<ArrayList<MyInfo>>(){}.getType();
        list = gson.fromJson(json, listType);
        if (list == null || list.size() == 0 )
        {
            Toast.makeText(getApplicationContext(), "Error list is null or empty", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if(v==pick){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    fec.setText(i2+"/"+(i1+1)+"/"+i);
                }
            }
            ,dia,mes,ano);
            datePickerDialog.show();
        }
    }
}