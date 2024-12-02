  package com.example.javaee.shared.utils;

  import java.lang.reflect.Field;

  public class UpdateUtil {
    public static <T> void copyNonNullProperties(T source, T target) {
      try {
        for (Field field : source.getClass().getDeclaredFields()) {
          field.setAccessible(true);
          Object value = field.get(source);
          if (value != null) {
            field.set(target, value);
          }
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Error copying properties", e);
      }
    }
  }
