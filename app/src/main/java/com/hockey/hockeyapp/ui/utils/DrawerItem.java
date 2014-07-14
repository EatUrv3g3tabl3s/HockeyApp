package com.hockey.hockeyapp.ui.utils;

/**
 * Created by Owner on 7/10/2014.
 */
public class DrawerItem {
    public static final int DEFAULT = 0;
    public int layoutId;
    public String title;
    public int imageId;

    public DrawerItem(int layoutId, String title, int imageId) {
        this.layoutId = layoutId;
        this.title = title;
        this.imageId = imageId;
    }
}
