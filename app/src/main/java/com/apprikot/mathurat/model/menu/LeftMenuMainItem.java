package com.apprikot.mathurat.model.menu;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Checkable;

import com.apprikot.mathurat.controller.enums.ListItemType;
import com.apprikot.mathurat.controller.enums.MenuMainItem;
import com.apprikot.mathurat.controller.interfaces.Listable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LeftMenuMainItem implements Listable, Checkable, Parcelable {


    public MenuMainItem menuMainItem;
    public boolean isChecked;


    public LeftMenuMainItem() {
    }


    public LeftMenuMainItem(MenuMainItem menuMainItem) {
        this.menuMainItem = menuMainItem;
    }

    public LeftMenuMainItem(MenuMainItem menuMainItem, boolean isChecked) {
        this.menuMainItem = menuMainItem;
        this.isChecked = isChecked;
    }


    protected LeftMenuMainItem(Parcel in) {
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LeftMenuMainItem> CREATOR = new Creator<LeftMenuMainItem>() {
        @Override
        public LeftMenuMainItem createFromParcel(Parcel in) {
            return new LeftMenuMainItem(in);
        }

        @Override
        public LeftMenuMainItem[] newArray(int size) {
            return new LeftMenuMainItem[size];
        }
    };

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(menuMainItem).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LeftMenuMainItem)) {
            return false;
        }
        LeftMenuMainItem rhs = ((LeftMenuMainItem) other);
        return new EqualsBuilder().append(menuMainItem, rhs.menuMainItem).isEquals();
    }

    @Override
    public ListItemType getListItemType() {
        return ListItemType.MENU_ITEM;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        isChecked = !isChecked;
    }
}