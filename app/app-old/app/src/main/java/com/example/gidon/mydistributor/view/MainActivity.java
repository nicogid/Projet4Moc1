package com.example.gidon.mydistributor.view;

import com.example.gidon.mydistributor.service.GithubService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gidon.mydistributor.R;


import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity {

    //@BindView(R.id.login) EditText login;
    //@BindView(R.id.pwd) EditText pwd;
    //@BindView(R.id.visibility) CheckBox check;

    EditText login,pwd;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check= (CheckBox)findViewById(R.id.visibility);
        pwd = (EditText)findViewById(R.id.pwd);

        if(check.isChecked()){
            check.setChecked(true);
            pwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else{
            check.setChecked(false);
            pwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }

    @OnClick(R.id.validate_login)
    public void validate(View view){
        if(login.getText().toString() =="a" && pwd.getText().toString()== "b" ){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this,"Mot de passe incorect",Toast.LENGTH_LONG).show();
    }
}
