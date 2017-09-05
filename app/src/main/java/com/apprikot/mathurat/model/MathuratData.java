package com.apprikot.mathurat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.interfaces.Listable;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class MathuratData implements Listable, Parcelable {


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
    @JsonProperty("refresh_counter")
    public boolean refresh_counter = false;

    @JsonProperty("sat_counter")
    public int sat_counter;
    @JsonProperty("sun_counter")
    public int sun_counter;
    @JsonProperty("mon_counter")
    public int mon_counter;
    @JsonProperty("tus_counter")
    public int tus_counter;
    @JsonProperty("wen_counter")
    public int wen_counter;
    @JsonProperty("thu_counter")
    public int thu_counter;
    @JsonProperty("fri_counter")
    public int fri_counter;

    @JsonProperty("reset_sat_counter")
    public boolean reset_sat_counter;
    @JsonProperty("reset_sun_counter")
    public boolean reset_sun_counter;
    @JsonProperty("reset_mon_counter")
    public boolean reset_mon_counter;
    @JsonProperty("reset_tus_counter")
    public boolean reset_tus_counter;
    @JsonProperty("reset_wen_counter")
    public boolean reset_wen_counter;
    @JsonProperty("reset_thu_counter")
    public boolean reset_thu_counter;
    @JsonProperty("reset_fri_counter")
    public boolean reset_fri_counter;

    @JsonProperty("evaluation_day")
    public int evaluation_day = -1;


    @JsonProperty("thaker_benefit_ar")
    public String thaker_benefit_ar;
    @JsonProperty("thaker_benefit_en")
    public String thaker_benefit_en;
    @JsonProperty("thaker_source_ar")
    public String thaker_source_ar;
    @JsonProperty("thaker_source_en")
    public String thaker_source_en;
    @JsonProperty("isNotify")
    public boolean isNotify = false;
    @JsonProperty("isAlarm")
    public boolean isAlarm = false;

    @JsonProperty("notify_hour")
    public int notify_hour;
    @JsonProperty("notify_min")
    public int notify_min;
    @JsonProperty("notify_am_pm")
    public int notify_am_pm;

    @JsonProperty("notify_days")
    public String notify_days;
    @JsonProperty("sound_ar_name")
    public String sound_ar_name;
    @JsonProperty("sound_ar_echo")
    public String sound_ar_echo;
    @JsonProperty("sound_ar_fast")
    public String sound_ar_fast;
    @JsonProperty("sound_en_name")
    public String sound_en_name;

    @JsonProperty("img_ar")
    public String img_ar;
    @JsonProperty("img_en")
    public String img_en;


    @JsonProperty("isArabic_txt")
    public boolean isArabic_txt = true;

    @JsonProperty("langchange")
    public boolean langchange = false;
    @JsonProperty("speedChange")
    public boolean speedChange = false;
    @JsonProperty("isAnchor")
    public boolean isAnchor = false;

    public ListItemType listItemType = ListItemType.MINI_THAKER;

//    public OnItemSwipeListener onItemSwipeListener;



    public MathuratData() {
    }

    public MathuratData(MathuratData mathurat) {
        id = mathurat.id;
        text_ar = mathurat.text_ar;
        text_en = mathurat.text_en;
    }


    protected MathuratData(Parcel in) {
        id = in.readInt();
        text_ar = in.readString();
        text_en = in.readString();
        counter = in.readInt();
        min_counter = in.readInt();
        max_counter = in.readInt();
        refresh_counter = in.readByte() != 0;
        sat_counter = in.readInt();
        sun_counter = in.readInt();
        mon_counter = in.readInt();
        tus_counter = in.readInt();
        wen_counter = in.readInt();
        thu_counter = in.readInt();
        fri_counter = in.readInt();

        reset_sat_counter = in.readByte() != 0;
        reset_sun_counter = in.readByte() != 0;
        reset_mon_counter = in.readByte() != 0;
        reset_tus_counter = in.readByte() != 0;
        reset_wen_counter = in.readByte() != 0;
        reset_thu_counter = in.readByte() != 0;
        reset_fri_counter = in.readByte() != 0;


        evaluation_day = in.readInt();
        thaker_benefit_ar = in.readString();
        thaker_benefit_en = in.readString();
        thaker_source_ar = in.readString();
        thaker_source_en = in.readString();
        isNotify = in.readByte() != 0;
        isAlarm = in.readByte() != 0;
        notify_hour = in.readInt();
        notify_min = in.readInt();
        notify_am_pm = in.readInt();
        notify_days = in.readString();
        sound_ar_name = in.readString();
        sound_ar_echo = in.readString();
        sound_ar_fast = in.readString();
        sound_en_name = in.readString();
        img_ar = in.readString();
        img_en = in.readString();
        isArabic_txt = in.readByte() != 0;
        langchange = in.readByte() != 0;
        speedChange = in.readByte() != 0;
        isAnchor = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text_ar);
        dest.writeString(text_en);
        dest.writeInt(counter);
        dest.writeInt(min_counter);
        dest.writeInt(max_counter);
        dest.writeByte((byte) (refresh_counter ? 1 : 0));
        dest.writeInt(sat_counter);
        dest.writeInt(sun_counter);
        dest.writeInt(mon_counter);
        dest.writeInt(tus_counter);
        dest.writeInt(wen_counter);
        dest.writeInt(thu_counter);
        dest.writeInt(fri_counter);


        dest.writeByte((byte) (reset_sat_counter ? 1 : 0));
        dest.writeByte((byte) (reset_sun_counter ? 1 : 0));
        dest.writeByte((byte) (reset_mon_counter ? 1 : 0));
        dest.writeByte((byte) (reset_tus_counter ? 1 : 0));
        dest.writeByte((byte) (reset_wen_counter ? 1 : 0));
        dest.writeByte((byte) (reset_thu_counter ? 1 : 0));
        dest.writeByte((byte) (reset_fri_counter ? 1 : 0));


        dest.writeInt(evaluation_day);
        dest.writeString(thaker_benefit_ar);
        dest.writeString(thaker_benefit_en);
        dest.writeString(thaker_source_ar);
        dest.writeString(thaker_source_en);
        dest.writeByte((byte) (isNotify ? 1 : 0));
        dest.writeByte((byte) (isAlarm ? 1 : 0));
        dest.writeInt(notify_hour);
        dest.writeInt(notify_min);
        dest.writeInt(notify_am_pm);
        dest.writeString(notify_days);
        dest.writeString(sound_ar_name);
        dest.writeString(sound_ar_echo);
        dest.writeString(sound_ar_fast);
        dest.writeString(sound_en_name);
        dest.writeString(img_ar);
        dest.writeString(img_en);
        dest.writeByte((byte) (isArabic_txt ? 1 : 0));
        dest.writeByte((byte) (langchange ? 1 : 0));
        dest.writeByte((byte) (speedChange ? 1 : 0));
        dest.writeByte((byte) (isAnchor ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MathuratData> CREATOR = new Creator<MathuratData>() {
        @Override
        public MathuratData createFromParcel(Parcel in) {
            return new MathuratData(in);
        }

        @Override
        public MathuratData[] newArray(int size) {
            return new MathuratData[size];
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
        if (!(other instanceof MathuratData)) {
            return false;
        }
        MathuratData rhs = ((MathuratData) other);
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }


    @Override
    public ListItemType getListItemType() {
        return listItemType;
    }

}