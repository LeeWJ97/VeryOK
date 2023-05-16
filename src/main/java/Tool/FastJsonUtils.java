package Tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class FastJsonUtils {

    /**
     * 将对象转换为JSON字符串
     *
     * @param obj 要转换的对象
     * @return 转换后的JSON字符串
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将对象转换为排序过的JSON字符串
     *
     * @param jobject 要转换的jobject对象
     * @return 转换后排序过的JSON字符串
     */
    public static String toSortedJsonString(JSONObject jobject) {
        Map<String, Object> sortedMap = new TreeMap<>(jobject.getInnerMap());
        JSONObject sortedJsonObject = new JSONObject(sortedMap);
        return sortedJsonObject.toJSONString();
    }


    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz 目标对象的类型
     * @param <T>   目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param json         JSON字符串
     * @param typeReference 目标对象的类型引用，用于处理泛型类型
     * @param <T>          目标对象的泛型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * 将 JSON 字符串转换为 JObject
     *
     * @param json JSON 字符串
     * @return 转换后的 JObject
     */
    public static JSONObject toJObject(String json) {
        return JSON.parseObject(json);
    }
}
