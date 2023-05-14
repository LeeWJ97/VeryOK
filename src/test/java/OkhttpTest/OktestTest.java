package OkhttpTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OktestTest {
    String postjson = """
                        {
                            "method": "***method***",
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

    void checkRes(String testjson) {
        //跑
        Oktest ot = new Oktest();
        String result = ot.run(testjson);

        System.out.println(ot.getMethod());
        //打印全部结果
        System.out.println(result);
        //获取指定返回头
        System.out.println(Oktest.getResHeaderValue(result, "date"));
        //获取返回体
        String resBodyValue = Oktest.getResBodyValue(result);


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
        int statusCode = Oktest.getResCode(result);
        System.out.println(statusCode);


        //对请求内容进行断言
        JSONObject reqObject = JSONObject.parseObject(testjson);
        String methodStr = reqObject.getString("method");
        String urlStr = reqObject.getString("url");

        assertEquals(methodStr, ot.getMethod());
        assertEquals(urlStr, ot.getUrl());

        //对返回内容进行断言
        assertTrue(timestamp > 1);
        assertEquals("SUCCESS::接口调用成功",successMessage);
        assertEquals(200,statusCode);

        System.out.println("=================================================");
    }

    @Test
    @Order(1)
    void get() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        checkRes(postjson.replace("***method***", methodName));
    }
    @Test
    @Order(2)
    void post() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        checkRes(postjson.replace("***method***", methodName));
    }

    @Test
    @Order(3)
    void put() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        checkRes(postjson.replace("***method***", methodName));
    }

    @Test
    @Order(4)
    void delete() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        checkRes(postjson.replace("***method***", methodName));
    }
}