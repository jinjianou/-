- 本地变量类型判断 var

  ```
  var o = new Object() {
      Calendar calendar = Calendar.getInstance();
  };
  
  System.out.println(o.calendar);
  ```

- 字符串加强 新增strip() 比strim()可以去掉unicode空格 如中文空格

- 集合增强

  of copyOf用来创建不可变集合

  ```php
  static <E> List<E> of(E... elements) {
    switch (elements.length) { // implicit null check of elements
      case 0:
          return ImmutableCollections.emptyList();
      case 1:
          return new ImmutableCollections.List12<>(elements[0]);
      case 2:
          return new ImmutableCollections.List12<>(elements[0], elements[1]);
      default:
          return new ImmutableCollections.ListN<>(elements);
    }
  }
  static <E> List<E> copyOf(Collection<? extends E> coll) {
      return ImmutableCollections.listCopy(coll);
  }
  static <E> List<E> listCopy(Collection<? extends E> coll) {
      if (coll instanceof AbstractImmutableList && coll.getClass() != SubList.class) {
          return (List<E>)coll;
      } else {
          return (List<E>)List.of(coll.toArray());
      }
  }
  ```

- Stream增强

  Stream增强

  - Stream.ofNullable()

  - takewhile 不满足条件停止

    dropwhile 满足条件开始（到结束）

  - iterate 方法的新重载方法，可以让你提供一个 Predicate (判断条件)来指定什么时候迭代

- optional增强

  - 转化为stream  Optional.ofNullable(xxx).stream()

  - Optional.ofNullable(xxx).or()

    or（supplier）: if value is present return the value else return  **supplier**

    orElse(other) : if value is present return the value else return other

- InputStream增强

  inputstream.transeferTo(outputstream)   inputstream->outputstream

  classLoader.getResourceAsStream  target>classes(java+resources)

- http client

  ```go
  var request = HttpRequest.newBuilder()
  .uri(URI.create("https://javastack2.cn"))
  .GET()
  .build();
  var client = HttpClient.newHttpClient();
  // 同步 blocking
  HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
  System.out.println(response.body());
  // 异步
  client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
  .thenApply(HttpResponse::body)
  .thenAccept(System.out::println);
  for (int i = 0; i < 10000; i++) {
              try {
                  Thread.sleep(100);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              System.out.println(i);
          }
  ```

- writeString readString

  ```dart
  path.toUri()
  
  Files.writeString(
      Path.of("./", "tmp.txt"), // 路径
      "hello, jdk11 files api", // 内容
      StandardCharsets.UTF_8); // 编码
  String s = Files.readString(
      Paths.get("./tmp.txt"), // 路径
      StandardCharsets.UTF_8); // 编码
  ```

- 单文件代码

  这个功能允许你直接使用java解析器运行java代码。java文件会在内存中执行编译并且直接执行。唯一的约束在于所有相关的类必须定义在东一个java文件中。

  1. jdk11中，通过 java xxx.java 命令，就可直接运行源码文件程序，而且不会产生.class 文件
  2. 一个java文件中包含多个类时，java xxx.java 执行排在最上面的一个类的main方法

