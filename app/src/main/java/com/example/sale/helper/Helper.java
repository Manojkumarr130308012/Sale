package com.example.sale.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    @SuppressLint("WrongConstant")
    public static boolean checkForPermissions(final Activity activity, final String[] permissions, final int permRequestCode, int reasonToUseStringResId, View view) {
        final List<String> permissionsNeeded = new ArrayList<>();
        Snackbar snackbar = null;
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    //add explanation to why you need this permission
                    permissionsNeeded.add(perm);
                    if (snackbar == null) {
                        snackbar = Snackbar.make(view, reasonToUseStringResId, BaseTransientBottomBar.LENGTH_INDEFINITE);
                        snackbar.show();
                    }
                } else {
                    // add the request.
                    permissionsNeeded.add(perm);
                }
            }
        }
        if (permissionsNeeded.size() > 0) {
            // go ahead and request permissions
            ActivityCompat.requestPermissions(activity,
                    permissionsNeeded.toArray(new String[permissionsNeeded.size()]),
                    permRequestCode);
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }
    }

}
