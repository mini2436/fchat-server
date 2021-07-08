package xyz.mini2436.fchat.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import xyz.mini2436.fchat.exceptions.ParameterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Json工具类
 *
 * @author mini2436
 * @date 2021-07-07 10:49
 **/
public class JsonUtils {
    /**
     * json转换成对象
     * @param jsonStr json字符串
     * @param obj 转换的 转换后的对象
     * @return 返回转换后的对象信息
     */
    public static <T> T jsonToObj(String jsonStr, T obj){
        if (ObjectUtils.isEmpty(jsonStr)){
            throw new ParameterException("转换的JSON数据为空");
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            obj = (T) mapper.readValue(jsonStr, obj.getClass());
        } catch (JsonProcessingException e) {
            throw new ParameterException("JSON数据转换对象发生失败");
        }
        return obj;
    }
    /**
     * 对象转换成json
     * @param obj 转换的对象
     * @return 转换后的json字符串
     */
    public static String objToJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new ParameterException("对象转换为JSON数据发生失败");
        }
    }

    /**
     * 测试
     * @param args jvm入参
     */
    public static void main(String[] args) {
        // 测试转化纯数组json
        var listJson = """
                ["key1","key2","key3","key4","这个是key5","这个是key6"]
                """;
        var listJson2 = " [\"key1\",\"key2\",\"key3\",\"key4\",\"这个是key5\",\"这个是key6\"]";
        List<String> strings = jsonToObj(listJson, new ArrayList<String>());

        strings.forEach(System.out::println);
        System.out.println("========================");
        var objJson = """
                {
                    "code": 200,
                    "message": "request success",
                    "data": ["key1","key2","key3","key4","这个是key5","这个是key6"]
                }
                """;
        HashMap<String, Object> map = jsonToObj(objJson, new HashMap<String, Object>());
        map.forEach((k,v)->{
            System.out.println("key:"+k+" value:"+v);
        });
        System.out.println("========================");
        List<String> data = (ArrayList<String>) map.get("data");
        data.forEach(System.out::println);
        System.out.println("========================");
        String hello = objToJson("hello");
        System.out.println("测试纯字符串"+hello);
    }
}
