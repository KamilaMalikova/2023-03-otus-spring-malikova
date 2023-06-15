package ru.otus.library.db;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Entry point to the application. Creates Application context
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableMongoRepositories
@EnableMongock
public class Spring08Application {
  public static void main(String[] args) {
    SpringApplication.run(Spring08Application.class, args);
  }

}
