package com.example.project7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private EditText editTextA,editTextP;
    private Button buttonLogin;
    private String Username,Password;
    private String URL = "http://192.168.1.4/sinhvien2/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        statuscolor();
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.kma2);
        innit();
    }
    private void statuscolor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gainsboro,this.getTheme()));
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.gainsboro));
        }
    }
    private void innit() {
        editTextA = (EditText) findViewById(R.id.EditTextUsername);
        editTextP = (EditText) findViewById(R.id.EditTextPassword);
        buttonLogin = (Button) findViewById(R.id.ButtonLogin);
        Username = Password = "";
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = editTextA.getText().toString().trim();
                Password = editTextP.getText().toString().trim();
                if (!Username.isEmpty()&&!Password.isEmpty())
                {
                    login(Username,Password);
                }
                else {
                    editTextA.setError("");
                    editTextP.setError("");
                }
            }
        });

    }
    private void login(String Usermane,String Password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")){
                    Intent intent = new Intent(MainActivity.this,calendaractivity.class);
                    startActivity(intent);
                }
                else{

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("taikhoan",Usermane);
                data.put("password",Password);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}