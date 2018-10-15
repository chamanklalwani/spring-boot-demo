package com.test.springbootdemo.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.springbootdemo.core.model.Employee;
import com.test.springbootdemo.core.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = { "com.test.springbootdemo" })
public class Main extends SpringBootServletInitializer {

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
    CommandLineRunner runner(EmployeeService employeeService) {
        return args -> {
            // read json and save it to in memory map
            ObjectMapper mapper = new ObjectMapper();
            //String datasourceDirectory;
            TypeReference<List<Employee>> typeReference = new TypeReference<List<Employee>>(){};
            // String filepath = "/" + datasourceDirectory + "/" + datasourceFilename;
            String filepath = "/datasource/db.json";
            InputStream inputStream = TypeReference.class.getResourceAsStream(filepath);
            try {
                if (inputStream != null) {
                    List<Employee> employees = mapper.readValue(inputStream,typeReference);
                    if (employees != null && !employees.isEmpty()) {
                        employeeService.saveEmployees(employees);
                        System.out.println("Employees Saved!");
                    }
                }
            } catch (IOException e){
                System.out.println("Unable to save Employees: " + e.getMessage());
            }
        };
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

    private void initData() {
        // create directory in not exist
        File directory = new File("/datasource");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
