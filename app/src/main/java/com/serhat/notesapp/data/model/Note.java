package com.serhat.notesapp.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int note_id;

    @ColumnInfo(name = "note_title")
    @NonNull
    private String note_title;

    @ColumnInfo(name = "note_content")
    @NonNull
    private String note_content;

    @ColumnInfo(name = "created_at")
    @NonNull
    private String created_at;

    @ColumnInfo(name = "color")
    @NonNull
    private int color;

    public Note(int note_id, @NonNull String note_title, @NonNull String note_content, @NonNull String created_at, int color) {
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
        this.created_at = created_at;
        this.color = color;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    @NonNull
    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(@NonNull String note_title) {
        this.note_title = note_title;
    }

    @NonNull
    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(@NonNull String note_content) {
        this.note_content = note_content;
    }

    @NonNull
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(@NonNull String created_at) {
        this.created_at = created_at;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
