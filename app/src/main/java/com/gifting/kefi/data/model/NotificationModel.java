package com.gifting.kefi.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("time_created")
    @Expose
    private long time_created;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("table_name")
    @Expose
    private String table_name;
    @SerializedName("id_table")
    @Expose
    private String id_table;
    @SerializedName("id")
    @Expose
    private String id;

    public NotificationModel(long time_created, String message, String table_name, String id_table, String id) {
        this.time_created = time_created;
        this.message = message;
        this.table_name = table_name;
        this.id_table = id_table;
        this.id = id;
    }

    public NotificationModel() {
    }

    public long getTime_created() {
        return time_created;
    }

    public void setTime_created(long time_created) {
        this.time_created = time_created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getId_table() {
        return id_table;
    }

    public void setId_table(String id_table) {
        this.id_table = id_table;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
