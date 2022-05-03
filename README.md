# VeryOK

Okhttp4.x封装，只需一段json字符串就能控制发包，方便测试调用，方便地run.

![image](https://user-images.githubusercontent.com/71825704/166253440-50c4c549-4663-441b-92be-3307831644e7.png)

完整返回字符串示例：
{"recieveTime":"1651504040804","resBody":"405 Not Allowed!","resCode":"405","resHeaders":"{\"connection\":[\"keep-alive\"],\"content-length\":[\"150\"],\"content-type\":[\"text/html\"],\"date\":[\"Mon, 02 May 2022 15:07:20 GMT\"],\"server\":[\"nginx\"]}","sendTime":"1651504040769"}

可通过getResHeaderValue、getResBodyValue、getResCode方法解析返回头、返回体、返回状态码。
