package com.huawei.agguard.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.cert.CertificateException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView showImage;
    private TextView appLabel;
    private String packageName = "";
    private EditText getPkgName;
    private  TextView currentPkg;
    private TextView publicKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImage = findViewById(R.id.showImage);
        appLabel = findViewById(R.id.appLabel);
        currentPkg = findViewById(R.id.currentPkg);

        publicKey = findViewById(R.id.publicKey);
        getPkgName = findViewById(R.id.getPkgName);
        getPkgName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();

                if(!temp.equals(packageName)) {
                    currentPkg.setText(temp);
                }
                packageName = temp;
            }
        });
        findViewById(R.id.getIconLabel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, 0);
                    Drawable drawable = packageInfo.applicationInfo.loadIcon(getPackageManager());
                    CharSequence app_name = packageInfo.applicationInfo.loadLabel(getPackageManager());


//                    Resources resourcesForApplication = getPackageManager().getResourcesForApplication(packageName);
//                    int identifier = resourcesForApplication.getIdentifier("app_name", "string", packageName);
//                    CharSequence app_name = getPackageManager().getText(packageName, identifier, null);
                    Log.e(TAG, "appName:"+app_name);
                    appLabel.setText(app_name);
//                    int identifier1 = resourcesForApplication.getIdentifier("appmarket_icon", "drawable", packageName);
//                    Drawable appmarket_icon = getPackageManager().getDrawable(packageName, identifier1, null);
//
////                    Drawable appmarket_icon2 = getPackageManager().getDrawable("com.hihonor.appmarket.overlay", 0x7f010000, null);
//                    Log.e(TAG, "appmarket_icon:0x"+Integer.toHexString(identifier1));
                    showImage.setImageDrawable(drawable);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.getPublicKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pk = CertUtils.getPublicKey(MainActivity.this, packageName);

                    publicKey.setText(pk);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
