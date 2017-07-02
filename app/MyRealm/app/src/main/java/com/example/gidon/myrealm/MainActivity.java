package com.example.gidon.myrealm;

import android.app.Activity;
import android.os.Bundle;

import 	android.util.Log;

import com.example.gidon.myrealm.Model.Tag;
import com.example.gidon.myrealm.Model.Url;

import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Url url = realm.createObject(Url.class);
                url.setDescription("Fido");
                url.setTitle("URL FIRST");

                Tag tag = realm.createObject(Tag.class);
                tag.setId(1);
                tag.setName("toto");

                RealmList<Tag> list = new RealmList<Tag>();
                list.add(tag);

                url.setTag(list);
                realm.copyToRealmOrUpdate(url);
            }
        },new Realm.Transaction.OnSuccess(){
            @Override
            public void onSuccess(){
                Log.i("test","Fonction success ok");
            }
        },new Realm.Transaction.OnError(){
            @Override
            public void onError(Throwable error){
                Log.i("test","Fonction error"+error.getMessage());
            }
        });
    }


}
