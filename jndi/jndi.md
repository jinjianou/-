***JNDI*** stands for ***Java Naming and Directory interface***. It is a specification （规范）and API that can be used to access any type directory service.

It doesn’t make sense to store the data in any of the Modules, because the data is a common data and it is not particularly restricted to any of the Modules

The Application Server Container will provide an implementation for the **JNDI Repository** for storing the global data and the clients can use the ***JNDI API*** to query the information available in the JNDI Repository

 

## Servlet HelloWorld

1. The Servlet class makes use of the `InitialContext` for accessing the JNDI information.
2. The method `lookup()` queries for the value of the string that is passed as a key.
3. it(The Servlet class) makes use of the normal `PrintWriter` class to send the information back to the Browser client.

```
public class JndiAccessServlet extends HttpServlet {
    public static final String JNDI_PREFIX="java:comp/env/";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InitialContext context = new InitialContext();
            
            String v1  =(String) context.lookup(JNDI_PREFIX.concat("v1"));
            Integer v2  =(Integer) context.lookup(JNDI_PREFIX.concat("v2"));
            
            PrintWriter writer = resp.getWriter();
            writer.write("Data read from jndi repository: ");
            writer.write("v1 - &gt;"+v1);
            writer.write("v2 - &gt;"+v2);
            writer.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
```



configuration:

```
<servlet>
  <servlet-name>jndiAccessServlet</servlet-name>
  <servlet-class>org.example.jndi.JndiAccessServlet</servlet-class>
</servlet>

<servlet-mapping>
  <servlet-name>jndiAccessServlet</servlet-name>
  <url-pattern>/jndiAccess</url-pattern>
</servlet-mapping>

<env-entry>
  <env-entry-name>v1</env-entry-name>
  <env-entry-value>v1_value</env-entry-value>
  <env-entry-type>java.lang.String</env-entry-type>
</env-entry>

<env-entry>
  <env-entry-name>v2</env-entry-name>
  <env-entry-value>10</env-entry-value>
  <env-entry-type>java.lang.Integer</env-entry-type>
</env-entry>
```



## spring jndi

```
<groupId>org.springframework</groupId>
<artifactId>spring-context</artifactId>
```

* jndiTemplate

  it provides a method for executing custom lookup logic through ***call-back mechanisms*** 

* jndiCallback

* jndiObjectFactoryBean





## tomcat 全局配置

conf/context.xml 加入

xml中有5个特殊字符

&  \&amp;
<  \&lt;
\> \&gt;
" \&quot;
'  \&apos;

	<Resource name="jndi/activiti" 
				auth="Container" 
				type="javax.sql.DataSource" 
				driverClassName="com.mysql.cj.jdbc.Driver" 
				url="jdbc:mysql://localhost:3306/activiti?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true" 
				username="root" 
				password="123456" 
				maxActive="20" 
				maxIdle="10" 
				maxWait="10000"/>
web.xml 加入

```
<resource-ref>
  <description>JNDI DataSource</description>
  <res-ref-name>jndi/activiti</res-ref-name>
  <res-type>javax.sql.DataSource</res-type>
  <res-auth>Container</res-auth>
</resource-ref>
```

测试

在servlet container中使用