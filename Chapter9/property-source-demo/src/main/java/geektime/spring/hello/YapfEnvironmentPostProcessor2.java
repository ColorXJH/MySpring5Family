package geektime.spring.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName: YapfEnvironmentPostProcessor2
 * @Package: geektime.spring.hello
 * @Description: 这种切入方法也可以
 * @Datetime: 2023/7/3 21:53
 * @author: ColorXJH
 */
public class YapfEnvironmentPostProcessor2 implements BeanFactoryPostProcessor {
    private static final String PROPERTY_FILE_NAME = "yapf.properties";
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        ConfigurableEnvironment environment = configurableListableBeanFactory.getBean(ConfigurableEnvironment.class);
        MutablePropertySources propertySources = environment.getPropertySources();
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE_NAME);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("customPropertySource", properties);
            propertySources.addFirst(propertySource);
        } catch (IOException e) {
            // 处理异常
        }

    }

    //接下来，你需要在Spring Boot应用程序的配置类中注册这个BeanFactoryPostProcessor。可以使用@Import注解或者在META-INF/spring.factories文件中配置。
    //    @Configuration
    //    @Import(YapfEnvironmentPostProcessor2.class)
    //    public class AppConfig {
    //        // 配置类的其他内容
    //    }
}
