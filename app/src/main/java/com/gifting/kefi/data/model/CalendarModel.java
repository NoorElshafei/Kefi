package com.gifting.kefi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class CalendarModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    Long cid;
    @ColumnInfo(name = "id_date")
    private String id_date ;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "data_to")
    private Long data_to ;
    @ColumnInfo(name = "data_from")
    private Long data_from ;
    @ColumnInfo(name = "color_select")
    private String color_select ;

    @Ignore
    public CalendarModel(){

    }



    public CalendarModel(String title, String note, Long data_to, Long data_from, String id_date,String color_select) {

        this.title = title;
        this.note = note;
        this.data_to = data_to;
        this.data_from=data_from;
        this.id_date = id_date;
        this.color_select=color_select;
    }

    protected CalendarModel(Parcel in) {
        if (in.readByte() == 0) {
            cid = null;
        } else {
            cid = in.readLong();
        }

        title = in.readString();
        note = in.readString();
        id_date = in.readString();
        data_from = in.readLong();
        data_to = in.readLong();
        color_select = in.readString();

    }

    public static final Creator<CalendarModel> CREATOR = new Creator<CalendarModel>() {
        @Override
        public CalendarModel createFromParcel(Parcel in) {
            return new CalendarModel(in);
        }

        @Override
        public CalendarModel[] newArray(int size) {
            return new CalendarModel[size];
        }
    };

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getData_to() {
        return data_to;
    }

    public void setData_to(Long data_to) {
        this.data_to = data_to;
    }
    public Long getData_from() {
        return data_from;
    }

    public void setData_from(Long data_from) {
        this.data_from = data_from;
    }
    public String getId_date() {
        return id_date;
    }

    public void setId_date(String id_date) {
        this.id_date = id_date;
    }
    public String getColor_select() {
        return color_select;
    }

    public void setColor_select(String color_select) {
        this.color_select = color_select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (cid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cid);
        }

        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(id_date);
        dest.writeLong(data_from);
        dest.writeLong(data_to);
        dest.writeString(color_select);

    }



}
