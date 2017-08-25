package au.com.mfapps.pollsapi;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication()
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource.model")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}
}