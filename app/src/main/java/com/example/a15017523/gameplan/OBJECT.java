package com.example.a15017523.gameplan;

import java.io.Serializable;

/**
 * Created by 15017523 on 1/8/2017.
 */

public class OBJECT implements Serializable {

        String id;
        String categories;
        String title;
        String description;
        String datetime;
        String reminder;

    public OBJECT(String id, String categories, String title, String description, String datetime, String reminder) {
        this.id = id;
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.reminder = reminder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }
}

