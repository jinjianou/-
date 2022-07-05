## Apache POI

常用场景：

1. 将用户信息导出为excel
2. 将excel表中信息录入到数据库

基本功能：

1. HSSF excel 03版 最多65536条数据
2. XSSF excel 07版 最多1048576条数据
3. HWPF word
4. HSLF ppt
5. HDGF visio

依赖：

```xml
<!--        poi common contains excel03-->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>5.1.0</version>
        </dependency>

<!--        based on opc and ooxml e.g. excel07-->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.1.0</version>
        </dependency>

<!--        Date and time library to replace JDK date handling-->
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
           <version>2.9.2</version>
        </dependency>

		<dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.32</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
            <version>2.12.1</version>
        </dependency>
```

### excel写

HSSF 速度快 但表记录数限制低:

```
long start = System.currentTimeMillis();
        //1.创建工作簿
        //HSSF 03
        // XSSF 07
        /* SXSSF  Streaming version of XSSFWorkbook implementing the "BigGridDemo" strategy
            This allows to write very large files without running out of memory
         as only a configurable portion(部分) of the rows are kept in memory at any one time(任何时候)
         */
        Workbook workbook = new HSSFWorkbook();
        //2.创建工作表
        Sheet sheet = workbook.createSheet("附件表1");
        Row row;
        Cell cell;
        for (int i = 0; i < 65536; i++) {
            //3.创建行
            row = sheet.createRow(i);
            //4.创建单元格并写入数据
            for (int col = 0; col < 10; col++) {
                cell = row.createCell(col);
                cell.setCellValue(col);
            }
        }

        //5.保存
             try(OutputStream fos=new FileOutputStream(path)) {
            	workbook.write(fos);
        	}
        	System.out.println(" cost time: "
                +(System.currentTimeMillis()-start));  // cost time: 2197
```

XSSF 速度慢 但能写更多的数据:  

​	65536 cost time: 5284

​       1000000 oom

SXSSF 写速度快，占用更少内存

​	默认有100条记录保存在内存中，如果超过这个数量，则最前面的数据会被写入到临时文件中。new SXSSF(数量)  **清空临时文件SXSSFWorkBook.dispose()** 

​      1000000  cost time: 12526

​	流数据（或数据流）是指在时间分布和数量上无限的一系列动态数据集合体，数据的价值随着时间的流逝而降低，因此必须实时计算给出秒级响应。流式计算，顾名思义，就是对数据流进行处理，是实时计算。批量计算则统一收集数据，存储到数据库中，然后对数据进行批量处理的数据计算方式 

​	1.事件流： 事件流采用的是查询保持静态，语句是固定的，数据不断变化的方式 

​	2.持续计算：  网站的访问PV/UV、用户访问了什么内容、搜索了什么内容等，实时的数据计算和分析可以动态实时地刷新用户访问数据，展示网站实时流量的变化情况，分析每天各小时的流量和用户分布情况 



### excel读

poi 5.0以前

```
 File file = Paths.get("./", "明细表.xls").toFile();
        //0.创建流
        try(InputStream fis= new FileInputStream(file)){
            //1.创建工作簿对象
            Workbook workbook=new HSSFWorkbook(fis);
            //2.读取工作表
            Sheet sheet = workbook.getSheetAt(0);
            //获取表头
            Row headRow = sheet.getRow(0);
            int colCount = headRow.getPhysicalNumberOfCells();
            StringBuilder sb=new StringBuilder();
            for (int i = 0; i < colCount; i++) {
                Cell cell = headRow.getCell(i);
                sb.append(cell.getStringCellValue()).append("|");
            }
            System.out.println(sb);
            //获取数据行
            int rowCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator evaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                int colCountPerRow=row.getPhysicalNumberOfCells();
                for (int j = 0; j < colCountPerRow; j++) {
                    Cell cell = row.getCell(j);
                    if(cell!=null){
                        System.out.print("["+i+" - "+j+"]   ");
                        switch (cell.getCellType()){
                            case BOOLEAN:
                                System.out.print("[Boolean] "+cell.getBooleanCellValue());
                                break;
                            case BLANK:
                                System.out.print("[BLANK]");
                                break;
                            case STRING:
                                System.out.print("[String]"+cell.getStringCellValue());
                                break;
                            //数据类型 得分别考虑日期类型/数字(科学计数)/长数字类型（字符串形式）
                            case NUMERIC:
                                if(DateUtil.isCellDateFormatted(cell)){
                                    String dateStr = new DateTime(cell.getDateCellValue())
                                            .toString("yyyy-MM-dd HH:mm:ss");
                                    System.out.print("【日期】"+dateStr);
                                }else {
                                    System.out.print(cell.getNumericCellValue());
//                                cell.setCellType(CellType.STRING);
//                                System.out.print("【转化为字符串输出】"+cell.toString());
                                }
                                break;
                            case FORMULA:
                                //1.获取公式计算器
                                //2.获取公式 || 计算
                                String formula = cell.getCellFormula();
                                System.out.print(formula);
                                CellValue value = evaluator.evaluate(cell);
                                System.out.print("  [公式计算结果]"+value.formatAsString());
                                break;
                            case ERROR:
                                System.out.print("【类型错误】");
                        }
                    }
                    System.out.println();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
```

poi 5.0+

```
//emulateCsv default false
DataFormatter formatter = new DataFormatter(true);
System.out.print(formatter.formatCellValue(cell));
```

想要evaluateFormula需要单独判断

## easyexcel

Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，一个3M的excel用POI sax解析依然需要100M左右内存，改用easyexcel可以降低到几M，**并且再大的excel也不会出现内存溢出**；03版依赖POI的sax模式，在上层做了模型转换的封装，让使用者更加简单方便 ![001](C:\Users\Administrator\Desktop\复习\素材\pic\java\001.png)





### 简单写

0. 导入依赖

   ```
   <!-- https://mvnrepository.com/artifact/com.alibaba/easyexcel -->
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>easyexcel</artifactId>
       <version>3.0.5</version>
   </dependency>
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.22</version>
   </dependency>
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>fastjson</artifactId>
       <version>1.2.78</version>
   </dependency>
   ```

1. 创建一个与excel对应的实体类

   ```
   @Data
   public class DemoData {
       @ExcelProperty("字符串标题")
       private String string;
       @ExcelProperty("日期标题")
       private Date date;
       @ExcelProperty("数字标题")
       private Double doubleData;
       //忽略这个字段
       @ExcelIgnore
       private String ignore;
   }
   ```

2. 生成要写入的数据并写入

   ```
   private static List<DemoData> data() {
           List<DemoData> list = ListUtils.newArrayList();
           for (int i = 0; i < 10; i++) {
               DemoData data = new DemoData();
               data.setString("字符串" + i);
               data.setDate(new Date());
               data.setDoubleData(0.56);
               list.add(data);
           }
           return list;
       }
   
       public static void main(String[] args) {
           // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入
           
           // 写法1 JDK8+
           // since: 3.0.0-beta1
           File file = Paths.get("easy_test.xlsx").toFile();
           // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
           // 如果这里想使用03/csv 则 传入excelType参数即可
           EasyExcel.write(file, DemoData.class)
                   .sheet("模板")
   /*                .doWrite(() -> {
                       // 分页查询数据
                       return data();
                   });*/
                   .doWrite(data());
   
   
           // 写法2
           file = Paths.get("easy_test.xlsx").toFile();
           // 这里 需要指定写用哪个class去写
           ExcelWriter excelWriter = null;
           try {
               excelWriter = EasyExcel.write(file, DemoData.class).build();
               WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
               excelWriter.write(data(), writeSheet);
           } finally {
               // 千万别忘记帮忙关闭流
               if (excelWriter != null) {
                   excelWriter.finish();
               }
           }
       }
   ```

问题： com.alibaba.excel.exception.ExcelGenerateException: java.lang.NoSuchFieldError: Factory

解决： 注释掉apache poi依赖



### 简单读

1. 创建一个与excel对应的实体类

2. 创建监听器 可选

   ```
   // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
   @Slf4j
   public class DemoDataListener implements ReadListener<DemoData> {
   
       /**
        * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
        */
       private static final int BATCH_COUNT = 4;
       /**
        * 缓存的数据
        */
       private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
       /**
        * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
        */
       private DemoDAO demoDAO;
   
       public DemoDataListener() {
           // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
           demoDAO = new DemoDAO();
       }
   
       /**
        * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
        *
        * @param demoDAO
        */
       public DemoDataListener(DemoDAO demoDAO) {
           this.demoDAO = demoDAO;
       }
   
       /**
        * 这个每一条数据解析都会来调用
        *
        * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
        * @param context
        */
       @Override
       public void invoke(DemoData data, AnalysisContext context) {
           log.info("解析到一条数据:{}", JSON.toJSONString(data));
           cachedDataList.add(data);
           // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
           if (cachedDataList.size() >= BATCH_COUNT) {
               saveData();
               // 存储完成清理 list
               cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
           }
       }
   
       /**
        * 所有数据解析完成了 都会来调用
        *
        * @param context
        */
       @Override
       public void doAfterAllAnalysed(AnalysisContext context) {
           // 这里也要保存数据，确保最后遗留的数据也存储到数据库
           saveData();
           log.info("所有数据解析完成！");
       }
   
       /**
        * 加上存储数据库
        */
       private void saveData() {
           log.info("{}条数据，开始存储数据库！", cachedDataList.size());
           demoDAO.save(cachedDataList);
           log.info("存储数据库成功！");
       }
   }
   ```

3. 创建dao 可选

   ```
   /**
    * 假设这个是你的DAO存储。当然还要这个类让spring管理，当然你不用需要存储，也不需要这个类。
    **/
   public class DemoDAO {
       public void save(List<DemoData> list) {
           // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
       }
   }
   ```

4. 读取

   ```
       // 写法1：JDK8+ ,不用额外写一个DemoDataListener
       // since: 3.0.0-beta1
       File file = Paths.get("easy_test.xlsx").toFile();
       // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
       // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行
       EasyExcel.read(file, DemoData.class, new PageReadListener<DemoData>(dataList -> {
           for (DemoData demoData : dataList) {
               log.info("读取到一条数据{}", JSON.toJSONString(demoData));
           }
       })).sheet().doRead();
   
       // 写法3：
       // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
       EasyExcel.read(file, DemoData.class, new DemoDataListener()).sheet().doRead();
   
       // 写法4：
       // 一个文件一个reader
       ExcelReader excelReader = null;
       try {
           excelReader = EasyExcel.read(file, DemoData.class, new DemoDataListener()).build();
           // 构建一个sheet 这里可以指定名字或者no
           ReadSheet readSheet = EasyExcel.readSheet(0).build();
           // 读取一个sheet
           excelReader.read(readSheet);
       } finally {
           if (excelReader != null) {
               // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
               excelReader.finish();
           }
       }
   ```