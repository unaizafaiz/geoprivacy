package com.hci.geoprivacy.geoprivacy;

import android.graphics.drawable.Drawable;

/**
 * Created by unaizafaiz on 11/10/17.
 */

public class AppList {

    private String appName;
    private String appIcon;
    private String packageName;
    private Drawable icon;
    private String[] permissions;

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }


}
