package org.jinjianou.project.wiki_knowledge_hub;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan(basePackages = {"org.jinjianou.project.wiki_knowledge_hub.dao"})
public class WikiKnowledgeHubApplication {
    public static final  Logger LOG=LoggerFactory.getLogger(WikiKnowledgeHubApplication.class);
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WikiKnowledgeHubApplication.class, args);
        Environment env = context.getEnvironment();
        LOG.info("启动成功");
        //配置文件没配server.port为null而非默认值
        LOG.info("端口： {}",env.getProperty("server.port"));
    }

}
