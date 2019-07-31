package com.conference.servlet.taglib;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {
   private static final DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

   public static String stacktrace(Exception ex) {
      StringWriter errors = new StringWriter();
      ex.printStackTrace(new PrintWriter(errors));
      return errors.toString();
   }

   public static String formatDate(Date dateTime) {
      return formatDateTime(dateTime, format);
   }

   public static String formatDate(Date dateTime, String format) {
      return formatDateTime(dateTime, new SimpleDateFormat(format));
   }

   public static String formatDateTime(Date dateTime, DateFormat format) {
      if (dateTime == null) {
         return "";
      }
      return format.format(dateTime);
   }
}