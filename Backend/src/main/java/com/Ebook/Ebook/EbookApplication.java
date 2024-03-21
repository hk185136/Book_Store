package com.Ebook.Ebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class EbookApplication {
	public static void main(String[] args) {
		SpringApplication.run(EbookApplication.class, args);
	}

}
