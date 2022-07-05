[Tesseract documentation | Tesseract OCR (tesseract-ocr.github.io)](https://tesseract-ocr.github.io/)

[GitHub - tesseract-ocr/tesseract: Tesseract Open Source OCR Engine (main repository)](https://github.com/tesseract-ocr/tesseract)

tess4j [GitHub - nguyenq/tess4j: Java JNA wrapper for Tesseract OCR API](https://github.com/nguyenq/tess4j)

## Features

The library provides optical character recognition (OCR) support for:

- TIFF, JPEG, GIF, PNG, and BMP image formats
- Multi-page TIFF images
- PDF document format

## Dependencies

On Windows: Microsoft Visual C++ 2022 x86 and x64 Runtimes





## integrate with spring

0. 依赖

   ```
   <dependency>
       <groupId>net.sourceforge.tess4j</groupId>
       <artifactId>tess4j</artifactId>
       <version>5.0.0</version>
   </dependency>
   ```

1. 代码

   ```
   // 创建实例
   ITesseract instance = new Tesseract();
   // 设置识别语言 follows ISO 639-3 standard
   //训练库 chi_sim简体中文
   instance.setLanguage("chi_sim");
   // 设置识别引擎 TessOcrEngineMode default 3 fastest 0 just the LSTM line recognizer 1
   instance.setOcrEngineMode(1);
   // 读取文件
   BufferedImage image = ImageIO.read(Paths.get("001.png").toFile());
   try {
       // 识别
       String result = instance.doOCR(image);
       System.out.println(result);
   } catch (
           TesseractException e) {
       System.err.println(e.getMessage());
   }
   ```

2. 配置并运行

   environment variables: TESSDATA_PREFIX=E:\tessdata

   或者在代码里设置  instance.setDatapath("E:\\tessdata");
   
   
   
   去[GitHub - tesseract-ocr/tessdata: Trained models with support for legacy and LSTM OCR engine](https://github.com/tesseract-ocr/tessdata)下载对应language的**traineddata**
   
   问题：raw.githubusercontent.com无法访问
   
   解决：在https://www.ipaddress.com查询ipv4地址，在hosts添加相应映射
   
3. 识别率不高

   * 图片先做处理

     ```
      // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
             image = ImageHelper.convertImageToGrayscale(image);
             // 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
     //        image = ImageHelper.convertImageToBinary(image);
             // 图片放大5倍,增强识别率(很多图片本身无法识别,放大7倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大7倍)
             image = ImageHelper.getScaledInstance(image, image.getWidth() * 7, image.getHeight() * 7);
     ```

   * 生成自己的训练库 

     tesseract-ocr-w64-setup和tesseract-ocr-w64-setup