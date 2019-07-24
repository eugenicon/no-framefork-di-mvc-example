package com.conference.servlet.taglib;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Functions {
   public static String stacktrace(Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      return errors.toString();
   }
}