package Tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FastJsonUtilsTest {

    @Test
    public void testClassToJson() {
        Person person = new Person("John", 25);
        String json = FastJsonUtils.toJsonString(person);
        System.out.println(json);
        assertEquals("{\"age\":25,\"name\":\"John\"}",json);
        // 在这里你可以添加更多的断言来验证生成的JSON字符串是否符合预期
    }


    @Test
    public void testFromJsontoClass() {
        String json = "{\"name\":\"John\",\"age\":25}";
        Person person = FastJsonUtils.fromJson(json, Person.class);

        assertNotNull(person);
        assertEquals("John", person.getName());
        assertEquals(25, person.getAge());
        // 在这里你可以添加更多的断言来验证解析的对象是否符合预期
    }

    @Test
    public void testFromJsonWithTypeReference() {
        String json = "[{\"name\":\"John\",\"age\":25},{\"name\":\"John2\",\"age\":252}]";
        List<Person> personList = FastJsonUtils.fromJson(json, new TypeReference<List<Person>>() {});

        assertNotNull(personList);
        assertEquals(2, personList.size());

        Person person = personList.get(0);
        assertEquals("John", person.getName());
        assertEquals(25, person.getAge());
        // 在这里你可以添加更多的断言来验证解析的对象是否符合预期
    }

    @Test
    public void testJObject() {
        String jsonString = "{\"name\": \"Tom\", \"skill\": {\"bigballs\": [\"football\", \"basketball\"], \"smallballs\": [\"badminton\"]}}";
        JSONObject jObject = FastJsonUtils.toJObject(jsonString);

        // 读取 JObject 的值
        String name = jObject.getString("name");
        JSONObject skill = jObject.getJSONObject("skill");
        JSONArray bigBalls = skill.getJSONArray("bigballs");
        JSONArray smallBalls = skill.getJSONArray("smallballs");
        System.out.println(name);
        System.out.println(skill);
        System.out.println(bigBalls);
        System.out.println(smallBalls.get(0));

        // 验证读取的值是否正确
        assertEquals("Tom", name);
        assertEquals(2, bigBalls.size());
        assertEquals(1, smallBalls.size());
        assertEquals("football", bigBalls.getString(0));
        assertEquals("basketball", bigBalls.getString(1));
        assertEquals("badminton", smallBalls.getString(0));

        //验证转换回String正确
        String newJsonString = FastJsonUtils.toSortedJsonString(jObject);
        System.out.println(newJsonString);
        assertEquals(jsonString.replace(" ",""), newJsonString);
    }


    @Test
    public void testToSortedString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Tom");
        jsonObject.put("skill", new JSONObject());
        jsonObject.getJSONObject("skill").put("bigballs", new String[]{"football", "basketball"});
        jsonObject.getJSONObject("skill").put("smallballs", new String[]{"badminton"});

        String sortedString = FastJsonUtils.toSortedJsonString(jsonObject);

        String expected = "{\"name\": \"Tom\", \"skill\": {\"bigballs\": [\"football\", \"basketball\"], \"smallballs\": [\"badminton\"]}}";
        System.out.println(sortedString);
        assertEquals(expected.replace(" ",""), sortedString);
    }

    @Test
    void testReadFromJsonnestedText() {
        String jsonString = "{\"name\": \"Tom\", \"skill\": {\"bigballs\": [\"football\", \"basketball\"], \"smallballs\": [\"badminton\"]}}";

        // 使用 TypeReference 解析嵌套 JSON
        TypeReference<NestedData> typeReference = new TypeReference<NestedData>() {};
        NestedData nestedData = FastJsonUtils.fromJson(jsonString, typeReference);

        // 验证解析的结果
        assertEquals("Tom", nestedData.getName());
        assertEquals(2, nestedData.getSkill().getBigBalls().size());
        assertEquals(1, nestedData.getSkill().getSmallBalls().size());
        assertEquals("football", nestedData.getSkill().getBigBalls().get(0));
        assertEquals("basketball", nestedData.getSkill().getBigBalls().get(1));
        assertEquals("badminton", nestedData.getSkill().getSmallBalls().get(0));

        System.out.println(nestedData.getSkill().getBigBalls().get(0));
    }

    // 内部类用于表示嵌套 JSON 结构
    private static class NestedData {
        private String name;
        private Skill skill;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Skill getSkill() {
            return skill;
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }
    }

    private static class Skill {
        private List<String> bigBalls;
        private List<String> smallBalls;

        public List<String> getBigBalls() {
            return bigBalls;
        }

        public void setBigBalls(List<String> bigBalls) {
            this.bigBalls = bigBalls;
        }

        public List<String> getSmallBalls() {
            return smallBalls;
        }

        public void setSmallBalls(List<String> smallBalls) {
            this.smallBalls = smallBalls;
        }
    }



    // 一个简单的Person类用于测试
    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Person() {
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
