package com.apprikot.mathurat.model;

import android.content.Context;

import com.apprikot.mathurat.controller.enums.MenuMainItem;
import com.apprikot.mathurat.model.menu.LeftMenuMainItem;

import java.util.ArrayList;
import java.util.List;

public class MenuLeftDataAdapter {
    public static final String TAG = MenuLeftDataAdapter.class.getSimpleName();

    private Context context;
    public List<LeftMenuMainItem> menuMainItems;

    public MenuLeftDataAdapter(Context context) {
        this.context = context;
        menuMainItems = new ArrayList<>();
    }

    public void load() {
        MenuMainItem[] mainItems = MenuMainItem.values();
//        UserData user = UserData.getInstance(context);
        menuMainItems.clear();
//        for (MenuMainItem mainItem : mainItems) {
//            if (mainItem == MenuMainItem.CHANGE_LANGUAGE) {
//                if (PrefHelp.getLang(context).equalsIgnoreCase("ar")) {
//                    menuMainItems.add(new LeftMenuMainItem(mainItem, true));
//                } else {
//                    menuMainItems.add(new LeftMenuMainItem(mainItem, false));
//                }
//            } else if (mainItem == MenuMainItem.LOGIN) {
//                if (user == null) {
//                    menuMainItems.add(new LeftMenuMainItem(mainItem));
//                }
//            } else if (mainItem == MenuMainItem.LOGOUT) {
//                if (user != null) {
//                    menuMainItems.add(new LeftMenuMainItem(mainItem));
//                }
//            } else {
//                menuMainItems.add(new LeftMenuMainItem(mainItem));
//            }
//        }

        for (MenuMainItem mainItem : mainItems) {
            menuMainItems.add(new LeftMenuMainItem(mainItem));
        }

    }
}
