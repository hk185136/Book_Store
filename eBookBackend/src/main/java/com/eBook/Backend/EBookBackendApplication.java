package com.eBook.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class EBookBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBookBackendApplication.class, args);
	}

}
