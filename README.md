# Okhttp_using

####Okhttp_using的介绍<br>
####使用Okhttp的同步方案，实例如下：<br>
    OkHttpClient client = new OkHttpClient();
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
####一些注意的点
由于安卓主线程不能进行网络的访问,所以需要再开启一个子线程来进行网络访问，并将结果用Handler来改变UI主线程<br>
###具体开启新线程，并进行网络访问，然后回调给Handler来改变主UI线程：<br>
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        show_json = (TextView) findViewById(R.id.test1);
        textView4 = (TextView) findViewById(R.id.textView4);
        cityName = (EditText) findViewById(R.id.cityName);
        new Thread(showJson).start();
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
####Gson的封装
具体的流程可以搜一下网上的资源，简单的配置就能够正常使用Gson来随心所欲的控制json格式
####其他的一些话
由于我的mac电脑的androidstudio模拟器不支持中文输入法，所以我就采用了hashmap 映射的方式来实现输入拼音来正常调用api中中文的部分。大家可以看看
