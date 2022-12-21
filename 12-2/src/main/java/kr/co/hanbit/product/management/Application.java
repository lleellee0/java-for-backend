package kr.co.hanbit.product.management;

//import org.modelmapper.ModelMapper;
//import org.modelmapper.config.Configuration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	public ModelMapper modelMapper() {
//		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration()
//				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
//				.setFieldMatchingEnabled(true);
//		return modelMapper;
//	}

	@Bean
	@Profile("prod")
	public ApplicationRunner runner(DataSource dataSource) {
		return args -> {
			// 이 부분에 실행할 코드를 넣으면 된다.
			Connection connection = dataSource.getConnection();
		};
	}

}
