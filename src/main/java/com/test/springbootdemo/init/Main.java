package com.test.springbootdemo.init;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@ComponentScan(basePackages = {"com.test.springbootdemo"})
@Configuration
public class Main extends SpringBootServletInitializer {

    @Value("${app.datasource.filedir}")
    public String datasourceDirectory;

    @Value("${app.datasource.filename}")
    public String datasourceFilename;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(getClass());
    }

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setInterceptors(Collections.singletonList(new SpringClientHttpRequestInterceptor()));
        restTemplate.setErrorHandler(new SpringClientHttpResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        factory.setConnectTimeout(20000);
        return factory;
    }

    class SpringClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
        private final Logger logger = LoggerFactory.getLogger(SpringClientHttpRequestInterceptor.class);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {

            logRequest(request, body);
            ClientHttpResponse response = execution.execute(request, body);
            logResponse(response);
            return response;
        }

        private void logRequest(HttpRequest request, byte[] body) throws IOException {
            logger.info("=========================== Request Begin ===========================");
            logger.info("URI         : {}", request.getURI());
            logger.info("Method      : {}", request.getMethod());
            logger.info("Headers     : {}", request.getHeaders());
            logger.info("Request body: {}", new String(body, "UTF-8"));
            logger.info("============================ Request End ============================");
        }

        private void logResponse(ClientHttpResponse response) throws IOException {
            logger.info("=========================== Response Begin ===========================");
            logger.info("Status code  : {}", response.getStatusCode());
            logger.info("Status text  : {}", response.getStatusText());
            logger.info("Headers      : {}", response.getHeaders());
            logger.info("============================ Response End ============================");
        }
    }

    class SpringClientHttpResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            // handle error
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            // handle error
            return false;
        }
    }

    @PostConstruct
    private void initData() {
        try {
            String directoryPath = getDatasourceDirectory(); // "/datasource";
            String filepath = getDatasourceFilePath(); // directoryPath + "/db.json";

            File directory = new File(directoryPath);
            File file = new File(filepath);
            if (file.delete())
                System.out.println("File deleted successfully");
            else
                System.out.println("Failed to delete the file");

            // create directory if not exist
            if (!directory.exists()) {
                directory.mkdir();
            }


            if (!file.exists()) {
                try {
                    file.createNewFile();
                    OutputStream output = new FileOutputStream(file);
                    ObjectMapper mapper = new ObjectMapper();
                    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
                    writer.writeValue(output, new ArrayList<>());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unable to save Employee: " + e);
                    System.out.println("Unable to save Employee: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    String getDatasourceDirectory() {
        return "/" + datasourceDirectory;
    }

    String getDatasourceFilePath() {
        return "/" + datasourceDirectory + "/" + datasourceFilename;
    }

}
