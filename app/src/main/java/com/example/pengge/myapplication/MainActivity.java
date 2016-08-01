package com.example.pengge.myapplication;

import com.example.pengge.myapplication.Gson.gsonAnalysis;
import com.example.pengge.myapplication.OkHttpNet.OkHttpBase;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private TextView show_json;
    private TextView textView4;
    private  EditText cityName;
    private String urlParam;
    private String url = "http://gc.ditu.aliyun.com/geocoding?a=%E5%8C%97%E4%BA%AC%E5%B8%82";
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        show_json = (TextView) findViewById(R.id.test1);
        textView4 = (TextView) findViewById(R.id.textView4);
        cityName = (EditText) findViewById(R.id.cityName);
        new Thread(showJson).start();
        dialog = new ProgressDialog(this);
                    dialog.setTitle("提示信息");
                    dialog.setMessage("正在获取，请稍后...");
                    dialog.setCancelable(false);
        dialog.show();


        //handler.post(showJson);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void show_data(View view) throws IOException {
        Map cityMap = new HashMap();
        cityMap.put("beijing","北京市");
        cityMap.put("tianjin","天津市");
        cityMap.put("fuzhou","福州市");
       //cityName.getText();
        Editable cityNameText = cityName.getText();
        //url = cityNameText.toString();
        urlParam = cityNameText.toString();
        String url_param = null;
        if(cityMap.containsKey(urlParam))
        {
             url_param = cityMap.get(urlParam).toString();
            //url = "http://gc.ditu.aliyun.com/geocoding?a=%E7%A6%8F%E5%B7%9E%E5%B8%82";
            url = "http://gc.ditu.aliyun.com/geocoding?a="+url_param;
            Toast.makeText(MainActivity.this,url_param, Toast.LENGTH_SHORT).show();
            new Thread(showJson).start();
            dialog = new ProgressDialog(this);
            dialog.setTitle("提示信息");
            dialog.setMessage("正在获取，请稍后...");
            dialog.setCancelable(false);
            dialog.show();
        }else
        {
            Toast.makeText(MainActivity.this,"并未找到该城市,请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg) {
            try {
                Thread.sleep(1000);//延迟1秒再执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Gson gson = new Gson();
            gsonAnalysis json_class = gson.fromJson(val,gsonAnalysis.class);
            String lon = json_class.getLon();
            String lat = json_class.getLat();
            //Log.d("tag",order_name);
            //String order_name = response;
            //Log.d("tag",val);
            dialog.dismiss();
            show_json.setText(lon);
            textView4.setText(lat);
        }
    };
    Runnable showJson = new Runnable() {
        @Override
        public void run() {
/*            if(urlParam == null)
            {
                url = "http://gc.ditu.aliyun.com/geocoding?a=%E5%8C%97%E4%BA%AC%E5%B8%82";
            }else
            {
                url = "http://gc.ditu.aliyun.com/geocoding?a="+urlParam;
            }*/
            OkHttpBase Httpclient = new OkHttpBase();
            String response = null;
            try {
                response = Httpclient.run(url);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", response);
                msg.setData(data);
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
