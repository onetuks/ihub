package com.onetuks.iflow;

import org.springframework.boot.SpringApplication;

public class TestIflowApplication {

  public static void main(String[] args) {
    SpringApplication.from(IflowApplication::main).with(TestcontainersConfiguration.class)
        .run(args);
  }

}
