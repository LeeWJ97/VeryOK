package OkhttpTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Oktest {
    //OkHttpClient.builder对象
    private OkHttpClient.Builder builder;
    //Request.Builder对象
    private Request.Builder requestBuilder;
    //OkHttpClient对象
    private OkHttpClient client;
    //方法
    private String method;
    //URL
    private String url;
    //headers字典
    private Map<String,String> headers;
    //请求体
    private String body;
    //代理字符串 形如：127.0.0.1:8080
    private String proxystr;
    //超时时间
    private int timeout;
    //java版本
    private String javaVersion = System.getProperty("java.version");;

    private Oktest() {
        builder = new OkHttpClient.Builder();
    }

    //忽略SSL证书验证
    public void setBuilderIgnoreSSL() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            if (javaVersion.startsWith("1.8")) {
                builder.sslSocketFactory(sslSocketFactory);
            }
            else{
                builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager)trustAllCerts[0]);
            }

            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //设置代理
    public void setBuilderProxy(String hostport){
        try {
            String[] hostportarray = hostport.split(":");
            SocketAddress sa = new InetSocketAddress(hostportarray[0], Integer.parseInt(hostportarray[1]));
            builder.proxy(new Proxy(Proxy.Type.HTTP, sa));
        } catch (Exception e) {
            System.err.println("设置代理失败，错误的代理："+hostport);
            //e.printStackTrace();
            throw e;
        }
    }

    //构建client
    public void buildClient(){
        client = builder.connectTimeout(timeout, TimeUnit.SECONDS) //连接超时
                .readTimeout(timeout, TimeUnit.SECONDS) //读取超时
                .writeTimeout(timeout, TimeUnit.SECONDS) //写超时
                .build();
    }

    //获取返回结果
    private String getResponse(){
        //执行完try自动response.close()
        try(Response response = client.newCall(requestBuilder.build()).execute()){
            Map<String,String> resMap = new HashMap<>();
            resMap.put("resBody",response.body().string());

            //转换返回头为str json形式
            Map<String, List<String>> resHeadersMap = response.headers().toMultimap();
            String resHeadersString = JSON.toJSONString(resHeadersMap);

            resMap.put("resHeaders",resHeadersString);
            resMap.put("resCode",response.code()+"");
            resMap.put("sendTime",response.sentRequestAtMillis()+"");
            resMap.put("recieveTime",response.receivedResponseAtMillis()+"");

            String res = JSON.toJSONString(resMap);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get请求，不能有请求体
    public String get(String url, Map<String, String> headers, String body){
        return get(url, headers);
    }

    public String get(String url, Map<String, String> headers){
        setHeaders(headers);
        requestBuilder.url(url).get().build();
        return getResponse();
    }

    //Post请求
    public String post(String url, Map<String,String> headers, String body){
        setHeaders(headers);
        //创建请求体
        RequestBody rbody = RequestBody.create(body,getMediaType(headers));
        requestBuilder.url(url).post(rbody).build();
        return getResponse();
    }

    //Put请求
    public String put(String url, Map<String,String> headers, String body){
        setHeaders(headers);
        //创建请求体
        RequestBody rbody = RequestBody.create(body,getMediaType(headers));
        requestBuilder.url(url).put(rbody).build();
        return getResponse();
    }

    //Delete请求
    public String delete(String url, Map<String,String> headers, String body){
        setHeaders(headers);
        //创建请求体
        RequestBody rbody = RequestBody.create(body,getMediaType(headers));
        requestBuilder.url(url).delete(rbody).build();
        return getResponse();
    }

    //添加请求头
    private void setHeaders(Map<String,String> headers){
        //清空原有请求头
        requestBuilder = new Request.Builder();
        //循环添加请求头
        for(String key: headers.keySet()){
            requestBuilder.addHeader(key, headers.get(key));
        }
    }
    //根据传入的请求头Content-Type获取MediaType，获取失败就默认设置为application/json; charset=utf-8
    private MediaType getMediaType(Map<String, String> headers) {
        String contenttype;
        if (headers.get("Content-Type") == null){
            contenttype = "application/json; charset=utf-8";
            System.err.println("Content-Type为null，自动设置为application/json; charset=utf-8");
        }
        else{
            contenttype = headers.get("Content-Type");
        }
        try{
            MediaType mediatype= MediaType.get(contenttype);
            return mediatype;
        }
        catch(Exception e) {
            MediaType mediatype= MediaType.get("application/json; charset=utf-8");
            System.err.println("错误的Content-Type传入："+contenttype+"，自动设置为application/json");
            return mediatype;
        }

    }
    //获取指定返回头，需传入resResult去解析获取
    public static String getResHeaderValue(String resResult,String key){
        try {
            JSONObject resResultMap = JSON.parseObject(resResult);
            JSONObject jresHeaders = JSON.parseObject((String) resResultMap.get("resHeaders"));
            JSONArray resHeadersDateArray = (JSONArray) jresHeaders.get(key);
            return (String) resHeadersDateArray.get(0);
        } catch (Exception e) {
            System.err.println("解析返回头失败："+resResult +" 所需key："+key);
            throw e;
        }
    }
    //获取返回体，需传入resResult去解析获取
    public static String getResBodyValue(String resResult){
        try {
            JSONObject resResultMap = JSON.parseObject(resResult);
            return (String) resResultMap.get("resBody");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("解析返回体失败："+resResult);
        }
        return null;
    }
    //获取返回status code，需传入resResult去解析获取
    public static String getResCode(String resResult){
        try {
            JSONObject resResultMap = JSON.parseObject(resResult);
            return (String) resResultMap.get("resCode");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("解析返回状态码失败："+resResult);
        }
        return null;
    }

    public String invokeRequest(Object ... paramList) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //尝试查找3个参数的方法：paramList[0]为请求方法，paramList[1]为URL, paramList[2]为headers, paramList[3]为body
        Method target = null;
        try {
            //System.out.println((String) paramList[0]+paramList[1]+paramList[2]+paramList[3]);
            target = this.getClass().getDeclaredMethod((String) paramList[0], String.class, Map.class, String.class);
            return (String) target.invoke(this, paramList[1], paramList[2], paramList[3]);
        } catch (NoSuchMethodException e) {
            System.err.println("不支持的请求方法：" + paramList[0] + "  支持列表: " + target);
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    //方便的run
    public String run(String configstr){
        //解析入参json
        JSONObject config = JSON.parseObject(configstr);
        //获取代理信息
        proxystr = config.getString("proxy");
        //获取超时信息
        timeout = config.getIntValue("timeout");
        //获取请求方法
        method = config.getString("method");
        //获取URL
        url = config.getString("url");
        //获取headers字典
        Map<String, String> headers = (Map<String, String>) config.get("headers");
        //获取请求体
        body = config.getString("data");

        //为timeout设置默认值
        if (timeout <= 0){
            timeout = 10;
        }
        //开启代理并忽略SSL不信任证书
        if(proxystr != null){
            setBuilderIgnoreSSL();
            setBuilderProxy(proxystr);
        }
        else{
            //关闭代理及关闭忽略SSL不信任证书
            builder = new OkHttpClient.Builder();
        }
        //构建client
        buildClient();

        //反射发包获取返回
        try {
            return invokeRequest(method,url,headers,body);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
//        System.out.println(getResHeaderValue(result,"date"));   //获取指定返回头
//        System.out.println(getResBodyValue(result));    //获取返回体
//        System.out.println(getResCode(result));    //获取返回status code
    }






    public static void main(String[] args) throws IOException, InterruptedException {
        Random r = new Random();

        //        *********************入参示例*******************
//        {
//             "method":"post",
//            "url": "https://baidu.com",
//                "headers": {
//                               "a": "jige",
//                              "User-Agent": "aaaa"
//        },
//            "data": "{\"test1\":\"m\",\"test2\":\"一\",\"test3\":\"二\"}",
//                "proxy": "127.0.0.1:8080",   //不需要代理时去掉这个Key Value
//                "timeout": 7                //若没有这个key value，timeout默认为10秒
//        }

        //入参json
        //String postjson = "{\"method\":\"get\",\"url\":\"https://dog.ceo/api/breeds/image/random\",\"headers\":{\""+r.nextInt()+"\":\"jige\",\"User-Agent\":\"aaaa"+r.nextInt()+"\"},\"data\":\"{\\\"test1\\\":\\\"m\\\",\\\"test2\\\":\\\"中文\\\",\\\"test3\\\":\\\"测试\\\"}\",\"timeout\":15}";
        String postjson = """
            {
                "method": "post",
                "url": "https://acs.m.taobao.com/gw/mtop.common.getTimestamp/*",
                "headers": {
                    "a": "jige",
                    "User-Agent": "aaaa"
                },
                "data": {
                    "test1": "m",
                    "test2": "中文",
                    "test3": "测试"
                },
                "timeout": 15,
                //"proxy": "127.0.0.1:8080"

            }
    """;

        //跑
        Oktest ot = new Oktest();
        String result = ot.run(postjson);
        //打印全部结果
        System.out.println(result);
        //获取指定返回头
        System.out.println(Oktest.getResHeaderValue(result,"date"));
        //获取返回体
        String resBodyValue = Oktest.getResBodyValue(result);;
        System.out.println(resBodyValue);

        //解析json提取timestamp
        JSONObject jsonObject = JSONObject.parseObject(resBodyValue);
        String timestampString = jsonObject.getJSONObject("data").getString("t");
        long timestamp = Long.parseLong(timestampString);
        System.out.println(timestamp);
        //解析json提取successMessage
        JSONArray retArray = jsonObject.getJSONArray("ret");
        String successMessage = retArray.getString(0);
        System.out.println(successMessage);

        //获取返回status code
        System.out.println(Oktest.getResCode(result));
    }
}
