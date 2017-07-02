package com.example.gidon.myrealm.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gidon on 26/06/2017.
 */

public class Tag extends RealmObject {

    private String name;

    @PrimaryKey
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
