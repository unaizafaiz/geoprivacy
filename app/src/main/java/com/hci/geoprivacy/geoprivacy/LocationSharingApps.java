package com.hci.geoprivacy.geoprivacy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unaizafaiz on 11/28/17.
 */

public class LocationSharingApps {
    private static final String SYSTEM_PACKAGE_NAME = "android";
    private List<ApplicationInfo> apps;
    PackageManager packageManager;
    Context ct;

    public LocationSharingApps(Context ct){
        this.ct=ct;
        packageManager = ct.getPackageManager();
        apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    }

    /**
     *     Create a list of objects that get the app names and image
     *
     *    @return An array list of object type AppList
     **/
    public ArrayList<AppList> prepareData(){

        ArrayList geo_apps = new ArrayList<>();
        for(int i=0;i<apps.size();i++) {
            ApplicationInfo p = apps.get(i);
            try {
                //Getting permissions of this app and adding to the list
                PackageInfo packageInfo = packageManager.getPackageInfo(p.packageName, PackageManager.GET_PERMISSIONS);
                //Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
                boolean hasLocationPermission = false;
                if(requestedPermissions != null) {
                    for (int j = 0; j < requestedPermissions.length; j++) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            if (requestedPermissions[j].contains("LOCATION") || requestedPermissions[j].contains("GPS")) {
                                if ((packageInfo.requestedPermissionsFlags[j] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                                    hasLocationPermission = true;
                                }
                            }
                        }
                    }
                }

                //If the app has location permission then display the list
                if (hasLocationPermission) {
                    if(!isAppPreLoaded(p.packageName)) {

                        AppList appDetails = new AppList();
                        appDetails.setAppName(p.loadLabel(ct.getPackageManager()).toString());
                        appDetails.setPackageName(p.packageName);
                        appDetails.setIcon(p.loadIcon(ct.getPackageManager()));
                        appDetails.setPermissions(requestedPermissions);
                        geo_apps.add(appDetails);
                        Log.d("App Type ", appDetails.getAppName() + " is of type " + (p.FLAG_SYSTEM & ApplicationInfo.FLAG_SYSTEM));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return geo_apps;
    }

    /**
     * Match signature of application to identify that if it is signed by system
     * or not.
     *
     * @param packageName
     *            package of application. Can not be blank.
     * @return <code>true</code> if application is signed by system certificate,
     *         otherwise <code>false</code>
     */
    private boolean isSystemApp(String packageName) {
        try {
            if(!packageName.contains("camera")) {
                // Get packageinfo for target application
                PackageInfo targetPkgInfo = packageManager.getPackageInfo(
                        packageName, PackageManager.GET_SIGNATURES);
                // Get packageinfo for system package
                PackageInfo sys = packageManager.getPackageInfo(
                        SYSTEM_PACKAGE_NAME, PackageManager.GET_SIGNATURES);
                // Match both packageinfo for there signatures
                return (targetPkgInfo != null && targetPkgInfo.signatures != null && sys.signatures[0]
                        .equals(targetPkgInfo.signatures[0]));
            }else{
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Check if application is preloaded. It also check if the application is
     * signed by system certificate or not.
     *
     * @param packageName
     *            package name of application. Can not be null.
     * @return <code>true</code> if package is preloaded and system.
     */
    private boolean isAppPreLoaded(String packageName) {
        if (packageName == null) {
            throw new IllegalArgumentException("Package name can not be null");
        }
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(
                    packageName, 0);
            // First check if it is preloaded.
            // If yes then check if it is System app or not.
            if (ai != null
                    && (ai.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
                // Check if signature matches
                Log.d("Checking if System App", ai.className+"");
                if (isSystemApp(packageName) == true) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
