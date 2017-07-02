package com.example.gidon.myrealm.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by gidon on 26/06/2017.
 */

public class Url extends RealmObject {


    private String Title;
    private String Description;
    private RealmList<Tag> tag;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public RealmList<Tag> getTag() {
        return tag;
    }

    public void setTag(RealmList tag) {
        this.tag = tag;
    }
}
