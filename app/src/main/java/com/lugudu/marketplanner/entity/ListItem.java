package com.lugudu.marketplanner.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ListItem implements Parcelable {
    private String item;
    private boolean done;

    public ListItem (String item, boolean done){
        this.item = item;
        this.done = done;
    }

    protected ListItem(Parcel in) {
        item = in.readString();
        done = in.readByte() != 0;
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeByte((byte) (done ? 1 : 0));
    }
}
