**复制网页表格到typora**

1. 复制到np++

   头部加\n

   尾部去掉

   > \n(.+)\n\n(.+)\n
   >
   > 替换成
   >
   > \1|\2\n

2. 复制到  https://tableconvert.com/csv-to-markdown  转化成 目标格式

注意： 如果cell内容过多换行了-> parse error 需要手动调整

