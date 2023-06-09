package geektime.spring.data.mybatis;

import geektime.spring.data.mybatis.mapper.CoffeeMapper;
import geektime.spring.data.mybatis.model.Coffee;
import geektime.spring.data.mybatis.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("geektime.spring.data.mybatis.mapper")
public class MybatisGeneratorDemoApplication implements ApplicationRunner {
	@Autowired
	private CoffeeMapper coffeeMapper;

	public static void main(String[] args) {
		//1:这里需要在运行前现在resources里创建一下mapper目录，也就是目标目录需要先存在
		//2:如果你把所有项目都在idea里打开了，xml配置里的targetProject路径要设置为形如“Chapter3/mybatis-generator-demo/src/main/resources/mapper”这个样子。
		//否则代码不会生成到想要的地方的，因为项目的基本路径是MySpring5Family
		SpringApplication.run(MybatisGeneratorDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//generateArtifacts();
		playWithArtifacts();
	}

	private void generateArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}

	private void playWithArtifacts() {
		Coffee espresso = new Coffee()
				.withName("espresso")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());
		coffeeMapper.insert(espresso);

		Coffee latte = new Coffee()
				.withName("latte")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());
		coffeeMapper.insert(latte);

		Coffee s = coffeeMapper.selectByPrimaryKey(1L);
		log.info("Coffee {}", s);

		CoffeeExample example = new CoffeeExample();
		example.createCriteria().andNameEqualTo("latte");
		List<Coffee> list = coffeeMapper.selectByExample(example);
		list.forEach(e -> log.info("selectByExample: {}", e));
	}
}

