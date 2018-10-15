package com.test.springbootdemo.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class will have all the application related properties
 */
@Component
public class AppUtil {
    @Value("${app.datasource.filedir}")
    public static String datasourceDirectory;

    @Value("${app.datasource.filename}")
    public static String datasourceFilename;
}
