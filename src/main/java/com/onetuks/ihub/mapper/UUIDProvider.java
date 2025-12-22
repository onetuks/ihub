package com.onetuks.ihub.mapper;

import java.util.UUID;

public class UUIDProvider {

  public static String provideUUID(String tableName) {
    return tableName + "_" + UUID.randomUUID();
  }
}
