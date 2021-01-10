package com.example.mapstest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Page1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);


        }

//戻るボタンの設定
    public void Return_Homepage(View v){
        Intent intent = new Intent(this,MapsActivity.class);
        try {
            startActivity(intent);//  画面を開く
        }catch(Exception e){
            Toast.makeText(this, "対象のアプリがありません", Toast.LENGTH_SHORT).show();
        }

    }

    //Shareボタンの設定
    public boolean on(){
        ImageButton id = (ImageButton)findViewById(R.id.sharebutton);
        ApplicationInfo api = getApplicationContext().getApplicationInfo();
        String apkpath = api.sourceDir;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/vnd.android.package-archive");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
        startActivity(Intent.createChooser(intent,"shareVia"));
        return true;
    }





    // クリック処理　画像をクリックしたら画面遷移
            /* 明示的インテントとは, インテントにパッケージ名とクラス名を指定してアクティビティを起動する方法である.
        Intent01.java
        インテントのインスタンスを生成する.
        Intent#setClassNameメソッドで, パッケージ名とクラス名をセットする.
        startActivityメソッドを使って, 他のアプリを起動する.
         */
    public void onButton_googlemap1(View v){
        String packageName = "jp.co.yahoo.android.yshopping";
        String className = "jp.co.yahoo.android.yshopping.YShopMainActivity";
        Intent intent = new Intent().setClassName(packageName,className);
        try {
            startActivity(intent);//  画面を開く
        }catch(Exception e){
            Toast.makeText(this, "対象のアプリがありません", Toast.LENGTH_SHORT).show();
        }

    }


}