### ObjectMapper

- writeValue javaBean->json

  ```
  public void writeValue(File resultFile, Object value)
  public String writeValueAsString(Object value)
  public byte[] writeValueAsBytes(Object value)
  ```

- readValue jsonstr->javabean

  ```
  public <T> T readValue(String content, Class<T> valueType)；简单型，就是 直接  UserBase.class 就可。
  public <T> T readValue(String content, TypeReference<T> valueTypeRef)；复杂的可以 用这个
  public <T> T readValue(String content, JavaType valueType)；这个书写起来比较麻烦，就不说明了，不常用，前2个已经彻底满足了。
  public <T> T readValue(File src, Class<T> valueType)
  ```

    content满足camelCase 默认是大小写敏感

- 取消大小写敏感：

  - @JsonProperty("A")
  - mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);

- 修改命名风格（序列化时使用，反序列化时需要用getBytes(UTF8)）

  被public修饰的字段/序列化的字段名时被public修饰的getter方法转换过来的

  当前几个都是大写的字母，都会转换成小写，直到不是大写为止，若小写后面还有大写，则保持大写

  @JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE)

  mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); //下划线 and all_lower_letter 

  string str=""在双引号里 alter+enter 选择字符串类型 edit fragment

- readTree json->jsonNode

  String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }"; 

  JsonNode jsonNode = objectMapper.readTree(json);

   String color = jsonNode.get("color").asText(); 

- json List String->java list

  objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

  Car[] cars = objectMapper.readValue(jsonCarArray, Car[].class);

  或

  List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});

- json->Map

  String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }"; 

  Map<String, Object> map   = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});

- Date字段 序列化时默认getTime Long类型

  ```
  DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
  mapper.setDateFormat(df);
  ```

  此时反序列化该字段也要按照这个格式

## 自定义序列化

```
public class **CustomCarSerializer** extends **StdSerializer**<**Car**>

SimpleModule module =   new SimpleModule("CustomCarSerializer", new Version(1, 0, 0, null, null, null));
module.addSerializer(Car.class, new CustomCarSerializer()); mapper.registerModule(module); 
Car car = new Car("yellow", "renault"); 
String carJson = mapper.writeValueAsString(car);
```

public class **CustomCarDeserializer** extends **StdDeserializer**<**Car**>

## serializable Annotation

- @JsonAnyGetter

  Marker annotation that can be used to define a non-static, no-argument method to be an "any getter"; accessor for getting a set of key/value pairs, to be serialized as part of containing POJO (similar to unwrapping) along with regular property values it has.Note that the **return type of annotated methods must be java.util.Map**.

  ```
  public class ExtendableBean {
      public String name;
      private Map<String,Object> map=new HashMap<>();
  
      @JsonAnyGetter
      public Map<String, Object> getMap() {
          return map;
      }
  
      public ExtendableBean(String name) {
          this.name = name;
      }
  
      public void add(String key,Object val){
          this.map.put(key,val);
      }
  }
  ```

```
ExtendableBean bean = new ExtendableBean("bean1");
bean.add("k1","v1");
bean.add("k2","v2");
```

- @JsonGetter 

  Marker annotation that can be used to define a non-static, no-argument value-returning (non-void) method to be used as a "getter" for a logical property 更多用@JsonProperty

- @JsonPropertyOrder

  We can use the *@JsonPropertyOrder* annotation to specify **the order of properties on serialization**. 对map在bean中序列化的位置无效

  *alphabetic*=true 按照字符排序

- @JsonRawValue

  The *@JsonRawValue* annotation can **instruct Jackson to serialize a property exactly as is**.  raw meaning原生

  ```
  @JsonRawValue
  public String id;
  
  ExtendableBean bean = new ExtendableBean("bean1","{\"a_id\": 1,\"BID\": 2,\"c\": 3}");
  
  ```

- @JsonValue

  *@JsonValue* indicates **a single method** that the library will use to **serialize the entire instance**.

## inclusion Annotation

- @JsonIgnoreProperties

  **@JsonIgnoreProperties is a class-level annotation that marks a property or a list of properties that Jackson will ignore.**

  1. value 

     ​	Names of properties to ignore.

  2. ignoreUnkown

     Property that defines whether it is ok to just ignore any      unrecognized properties during deserialization

  3. allowGetters 

  ​     **prevent ignoral** of getters for **properties listed in value**()

- @JsonIgnore

  @JsonIgnore annotation is used to mark a property to be ignored at the field level.

- @JsonInclude

  **We can use @JsonInclude to exclude properties with empty/null/default values.**