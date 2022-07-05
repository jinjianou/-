# 依赖

```
<dependency>
  <groupId>com.google.guava</groupId>
  <artifactId>guava</artifactId>
  <version>31.0.1-jre</version>
  <!-- or, for Android: -->
  <version>31.0.1-android</version>

```

# 基本工具

- [使用和避免null](https://github.com/tiantiangao/guava-study/blob/master/doc/basic-utilities-using-avoiding-null.md)

  com.google.common.base.Optional<T>

  Optional.fromNullable(null).or(Object obj)

- [参数检查](https://github.com/tiantiangao/guava-study/blob/master/doc/basic-utilities-preconditions.md)

  com.google.common.base.Optional<T>

  Preconditions 类似于Assert

  Static convenience methods that help a method or constructor check whether it was invoked correctly (that is, whether its preconditions were met).

  ```
  int[] arr={1,2,3};
  Preconditions.checkElementIndex(1,arr.length,
          "Illegal Argument passed: invalid index");
  ```

- [比较器](https://github.com/tiantiangao/guava-study/blob/master/doc/basic-utilities-ordering.md)

  com.google.common.collect.Ordering<T>

  A comparator, with additional methods to support common operations. **This is an "enriched" version of Comparator for pre-Java-8 users,** in the same sense that FluentIterable is an enriched Iterable for pre-Java-8 users.

  ```
  //        Ordering ordering = Ordering.natural();
          Ordering<Comparable> ordering = Ordering.from((Comparable a, Comparable b) -> b.compareTo(a));
          List<Integer> numberList= Arrays.asList(10,20,5,11,5,9,null);
          Collections.sort(numberList,ordering.nullsFirst());//null优先 升序
  //        List<Integer> numberList2=ordering.allEqual().nullsFirst().sortedCopy(numberList);
  //        System.out.println(numberList2); //null, 10, 20, 5, 11, 5, 9 稳定 不保证排序结果
          System.out.println(numberList);
  ```

- [常用的对象方法](https://github.com/tiantiangao/guava-study/blob/master/doc/basic-utilities-object-methods.md)

  com.google.common.base.Objects

  * static boolean equal(Object a, Object b)
  * static int hashCode(Object... objects)

* 范围

  com.google.common.collect.Range<C>

  A range (or "interval") defines the boundaries(边界) around a contiguous（连续） span of values of some Comparable type;

  ```
  //create a range (a,b] = { x | a < x <= b}
  Range<Integer> range = Range.openClosed(0, 9);
  
  //create a range [a,b) = { x | a <= x < b}
  Range<Integer> range2 = Range.closedOpen(3, 5);
  
  //encloses  lowerBound.compareTo(other.lowerBound) <= 0 && upperBound.compareTo(other.upperBound) >= 0; otehr是不是在this的范围内
  System.out.println(range.encloses(range2)); //true
  ```


# [Throwable类](https://github.com/tiantiangao/guava-study/blob/master/doc/basic-utilities-throwables.md)

Static utility（使用） methods pertaining to（关于） instances of Throwable.

```
  try {
        sqrt(-3.0);
    } catch (Throwable e) {
        //check the type of exception and throw it
        Throwables.throwIfInstanceOf(e, InvalidInputException.class);
        Throwables.throwIfUnchecked(e);
    }


}
public double sqrt(double input) throws InvalidInputException{
    if(input < 0) throw new InvalidInputException();
    return Math.sqrt(input);
}
```
# 集合扩展

## [新集合类型](https://github.com/tiantiangao/guava-study/blob/master/doc/collections-new-collection-types.md)

###	com.google.common.collect.Multiset<E>

​        A collection that supports order-independent（与顺序无关） equality, like Set, but may have duplicate elements. A multiset is also sometimes called a bag.   **允许存在多个相同的值**，**统计维度**而不是元素维度

```java
        HashMultiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        multiset.add("b");
        System.out.println(multiset.count("b")); //4 occurrence of an element
        System.out.println(multiset.size()); //9
        //element:count
        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            System.out.println(entry.getElement()+" -- "+entry.getCount());
        }
        multiset.remove("b",3);  //remove extra occurrence
        System.out.println(multiset.count("b"));//1
```

### com.google.common.collect.Bimap<K，V>

A bimap (or "bidirectional双向 map") is a map that preserves保留 the uniqueness of its values as well as that of its keys. This constraint enables bimaps to support an "inverse view", which is another bimap containing the same entries as this bimap but with reversed keys and values. **双向map，必须保证key和value在各自维度都是unique**

```
HashBiMap<Integer,String> biMap = HashBiMap.create();
biMap.put(100,"A");
biMap.put(101,"B");
biMap.put(102,"C");
System.out.println(biMap.inverse().get("A"));
```
### com.google.common.collect.Table<R，C，V>

A collection that associates an ordered pair of keys, called a row key and a column key, with a single value

<R> – the type of the table **row keys**
<C> – the type of the table **column keys**
<V> – the type of the mapped **values**

```
HashBasedTable<String, String, String> employeeTable = HashBasedTable.create();tring
employeeTable.put("IBM", "101","Mahesh");
employeeTable.put("IBM", "102","Ramesh");
employeeTable.put("IBM", "103","Suresh");

employeeTable.put("Microsoft", "111","Sohan");
employeeTable.put("Microsoft", "112","Mohan");
employeeTable.put("Microsoft", "113","Rohan");

Map<String,String> ibmEmployees =  employeeTable.row("IBM");//Map<column,value>
Set<String> employers = employeeTable.rowKeySet();//IBM,Microsoft
Map<String,String> EmployerMap =  employeeTable.column("102");//Map<row,value>
```
# [缓存工具](https://github.com/tiantiangao/guava-study/blob/master/doc/caches.md)

com.google.common.cache.LoadingCache<K，V>

A semi-persistent（半持久） mapping from keys to values. Values are automatically loaded by the cache, and are stored in the cache until either evicted or manually invalidated. The common way to build instances is using CacheBuilder

**半持久化：数据保存在内存中，不定期通过异步方式保存在磁盘上**

```
//create a cache
LoadingCache cache =
        CacheBuilder.newBuilder()
                .maximumSize(100) // maximum 100 records can be cached
                .expireAfterAccess(30, TimeUnit.MILLISECONDS) // cache will expire after 30 minutes of access
                .build(new CacheLoader<String,String>(){
                    @Override
                    public String load(String empId) throws Exception {
                        //make the expensive call if not hit cache
                        return "empId"+empId;
                    }
                });

cache.put("A","1");
System.out.println(cache.get("A")); //1
Thread.sleep(1000);
System.out.println(cache.get("A")); //empIdA
```
# [函数式](https://github.com/tiantiangao/guava-study/blob/master/doc/functional-idioms.md)

#并发工具

- [可监听的Future](https://github.com/tiantiangao/guava-study/blob/master/doc/concurrency-listenablefuture.md)
- [Service框架](https://github.com/tiantiangao/guava-study/blob/master/doc/concurrency-service.md)

# [字符串工具](https://github.com/tiantiangao/guava-study/blob/master/doc/strings.md)

## com.google.common.base.Joiner

* public static Joiner on(String separator)
* public Joiner skipNulls()
* public final String join(Iterable<? extends @Nullable Object> parts) 

```
String str = Joiner.on("-")
        .skipNulls()
        .join(Arrays.asList(1, 2, 3, 4, 5, null, 6));
```
## com.google.common.base.Splitter

* public static Splitter on(final String separator)

* public Splitter trimResults()

  automatically removes leading and trailing whitespace from each returned substring

* public Splitter omitEmptyStrings()

* public Iterable<String> split(final CharSequence sequence)

  ```
  Iterable<String> splits = Splitter.on(",")
          .trimResults(CharMatcher.is(' '))
          .omitEmptyStrings()
          .split("the ,quick, , brown         , fox,              jumps, over, the, lazy, little dog.");
  System.out.println(Joiner.on("-").join(splits)); //the-quick-brown-fox-jumps-over-the-lazy-little dog.
  ```

## com.google.common.base.CharMatcher

```
System.out.println(CharMatcher.digit().retainFrom("mahesh123")); // 123 only the digits
System.out.println(CharMatcher.whitespace().trimAndCollapseFrom("     Mahesh       Parashar ", 'x')); //MaheshxParashar replace/collapse whitespace into single character
System.out.println(CharMatcher.javaDigit().replaceFrom("mahesh123", "*")); // mahesh***
System.out.println(CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom("mahesh123"));//mahesh123 eliminate(排除) all characters that aren't digits or lowercase
```
## com.google.common.base.CaseFormat

ASCII字符格式之间的转换

变量 LOWER_CAMEL/LOWER_HYPHEN(LOWER_HYPHEN)/LOWER_UNDERSCORE(lower_underscore)/类UPPER_CAMEL/常量UPPER_UNDERSCORE(UPPER_UNDERSCORE)

```
    public final String to(CaseFormat format, String str) {
        Preconditions.checkNotNull(format);
        Preconditions.checkNotNull(str);
        return format == this ? str : this.convert(format, str);
    }
    
     System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, "XXXX-YYYY"));// == -> origin str XXXX-YYYY
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "xxxxYyyy"));//xxxx_yyyy LOWER_CAMEL-> LOWER_UNDERSCORE
```

# [网络工具](https://github.com/tiantiangao/guava-study/blob/master/doc/networking.md)

# [原生类型](https://github.com/tiantiangao/guava-study/blob/master/doc/primitives.md)

Type+s 如Bytes,Ints

# [范围处理](https://github.com/tiantiangao/guava-study/blob/master/doc/ranges.md)

# [I/O工具](https://github.com/tiantiangao/guava-study/blob/master/doc/io.md)

# [哈希工具](https://github.com/tiantiangao/guava-study/blob/master/doc/hash.md)

# [事件总线](https://github.com/tiantiangao/guava-study/blob/master/doc/eventbus.md)

# [运算工具](https://github.com/tiantiangao/guava-study/blob/master/doc/math.md)

# 反射工具

# 数学工具

IntMath,LongMath,BigIntegerMath