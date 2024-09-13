package boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication(scanBasePackages = {"boot.test"}, exclude = {DataSourceAutoConfiguration.class})
@IntegrationComponentScan(basePackages = {"boot.test"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}