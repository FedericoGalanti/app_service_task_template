package com.bttoy.service_task_template;

import android.os.Parcel;
import android.os.Parcelable;

public class ListExampleItem implements Parcelable {

    private String title;
    private String descr1;
    private String descr2;

    public ListExampleItem(String title, String descr1, String descr2) {
        this.title = title;
        this.descr1 = descr1;
        this.descr2 = descr2;
    }

    protected ListExampleItem(Parcel in) {
        title = in.readString();
        descr1 = in.readString();
        descr2 = in.readString();
    }

    public static final Creator<ListExampleItem> CREATOR = new Creator<ListExampleItem>() {
        @Override
        public ListExampleItem createFromParcel(Parcel in) {
            return new ListExampleItem(in);
        }

        @Override
        public ListExampleItem[] newArray(int size) {
            return new ListExampleItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(descr1);
        dest.writeString(descr2);
    }

    public String getTitle() {
        return title;
    }

    public String getDescr1() {
        return descr1;
    }

    public String getDescr2() {
        return descr2;
    }
}
