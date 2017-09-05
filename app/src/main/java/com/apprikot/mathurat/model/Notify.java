package com.apprikot.mathurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Notify implements Parcelable {



    @JsonProperty("Id")
    public int id;
    @JsonProperty("isNotify")
    public boolean isNotify;
    @JsonProperty("notify_hour")
    public int notify_hour;
    @JsonProperty("notify_min")
    public int notify_min;
    @JsonProperty("notify_am_pm")
    public int notify_am_pm ;
    @JsonProperty("notify_days")
    public String notify_days;

    public Notify() {
    }

    protected Notify(Parcel in) {
        id = in.readInt();
        isNotify = in.readByte() != 0;
        notify_hour = in.readInt();
        notify_min = in.readInt();
        notify_am_pm = in.readInt();
        notify_days = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isNotify ? 1 : 0));
        dest.writeInt(notify_hour);
        dest.writeInt(notify_min);
        dest.writeInt(notify_am_pm);
        dest.writeString(notify_days);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notify> CREATOR = new Creator<Notify>() {
        @Override
        public Notify createFromParcel(Parcel in) {
            return new Notify(in);
        }

        @Override
        public Notify[] newArray(int size) {
            return new Notify[size];
        }
    };

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Notify)) {
            return false;
        }
        Notify rhs = ((Notify) other);
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }

}