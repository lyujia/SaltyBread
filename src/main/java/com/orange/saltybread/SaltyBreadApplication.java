package com.orange.saltybread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SaltyBreadApplication {

  @Autowired
  static ApplicationContext context;

  public static void main(String[] args) throws Exception {

    SpringApplication.run(SaltyBreadApplication.class, args);

  }


}
