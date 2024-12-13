package com.kutubuddin.logic_todo_app.model;

import java.util.Date;

public class TodoModel {

    private  int id;
    private  String titel;

    private  String description;
    private Date date;
    private  String todotype;
    private  String priority;

    public TodoModel() {

    }

    public TodoModel(int id, String titel, String description, Date date, String todotype, String priority) {
        this.id = id;
        this.titel = titel;
        this.description = description;
        this.date = date;
        this.todotype = todotype;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTodotype() {
        return todotype;
    }

    public void setTodotype(String todotype) {
        this.todotype = todotype;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
