package com.example.gidon.mydistributor.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gidon.mydistributor.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login) EditText login;
    @BindView(R.id.pwd) EditText pwd;
    @BindView(R.id.visibility) CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.visibility)
    public void visibility(View view){
        if(check.){

        }
        else
            Toast.makeText(this.getBaseContext(),"Mot de passe incorect",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.validate_login)
    public void validate(View view){
        if(login.getText().toString() =="a" && pwd.getText().toString()== "b" ){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this.getBaseContext(),"Mot de passe incorect",Toast.LENGTH_LONG).show();
    }
}
