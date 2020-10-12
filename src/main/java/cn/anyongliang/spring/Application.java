package cn.anyongliang.spring;

import cn.anyongliang.cache.Caches;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 主启动程序🐖
 *
 * @SpringBootApplication(exclude = MongoAutoConfiguration.class):禁用自带的MongoDB
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        //注册缓存
        Caches.init();
        //启动
        SpringApplication.run(Application.class, args);
    }

}
