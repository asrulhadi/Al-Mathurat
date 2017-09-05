package com.apprikot.mathurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TasbehData implements Parcelable {


    @JsonProperty("Id")
    public int id;
    @JsonProperty("text_ar")
    public String text_ar;
    @JsonProperty("text_en")
    public String text_en;
    @JsonProperty("counter")
    public int counter;
    @JsonProperty("min_counter")
    public int min_counter;
    @JsonProperty("max_counter")
    public int max_counter;

    @JsonProperty("isSound")
    public boolean isSound = false;
    @JsonProperty("isMaxSet")
    public boolean isMaxSet = false;

    @JsonProperty("refresh_counter")
    public boolean refresh_counter = false;



    public TasbehData() {
    }

    public TasbehData(TasbehData mathurat) {
        id = mathurat.id;
        text_ar = mathurat.text_ar;
        text_en = mathurat.text_en;
    }


    protected TasbehData(Parcel in) {
        id = in.readInt();
        text_ar = in.readString();
        text_en = in.readString();
        counter = in.readInt();
        min_counter = in.readInt();
        max_counter = in.readInt();
        isSound = in.readByte() != 0;
        isMaxSet = in.readByte() != 0;
        refresh_counter = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text_ar);
        dest.writeString(text_en);
        dest.writeInt(counter);
        dest.writeInt(min_counter);
        dest.writeInt(max_counter);
        dest.writeByte((byte) (isSound ? 1 : 0));
        dest.writeByte((byte) (isMaxSet ? 1 : 0));
        dest.writeByte((byte) (refresh_counter ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TasbehData> CREATOR = new Creator<TasbehData>() {
        @Override
        public TasbehData createFromParcel(Parcel in) {
            return new TasbehData(in);
        }

        @Override
        public TasbehData[] newArray(int size) {
            return new TasbehData[size];
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
        if (!(other instanceof TasbehData)) {
            return false;
        }
        TasbehData rhs = ((TasbehData) other);
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }


}