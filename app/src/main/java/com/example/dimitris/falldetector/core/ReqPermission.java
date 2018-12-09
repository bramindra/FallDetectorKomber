package com.example.dimitris.falldetector.core;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

import com.example.dimitris.falldetector.R;

public abstract class ReqPermission extends Activity{
    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mErrorString = new SparseIntArray();
    }

    public abstract void onPermissionGranted(int requestCode);

    public void requestAppPermission(final String[]requestedPermission, final int stringId, final int requestCode){
        mErrorString.put(requestCode,stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermission = false;

        for(String permission:requestedPermission){
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this,permission);
            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }

        if(permissionCheck!=PackageManager.PERMISSION_GRANTED){
            if(showRequestPermission){
                Snackbar.make(findViewById(R.id.content), stringId, Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(ReqPermission.this, requestedPermission, requestCode);
                    }
                }).show();
            }else{
                ActivityCompat.requestPermissions(this, requestedPermission, requestCode);
            }
        }else{
            onPermissionGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requesCode, @NonNull String[] permissions, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requesCode, permissions, grantResult);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for(int permission : grantResult){
            permissionCheck = permissionCheck+permission;
        }

        if((grantResult.length>0) && PackageManager.PERMISSION_GRANTED == permissionCheck){
            onPermissionGranted(requesCode);
        }else{
            Snackbar.make(findViewById(R.id.content), mErrorString.get(requesCode), Snackbar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.setData(Uri.parse("package"+getPackageName()));
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(i);
                }
            }).show();
        }

    }
}
