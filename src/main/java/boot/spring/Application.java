package boot.spring;

import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.prometheus.metrics.core.metrics.Counter;

import io.prometheus.metrics.exporter.httpserver.HTTPServer;

import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "boot.spring.*")
public class Application {

	public static Counter counter ;
	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(Application.class, args);

//		JvmMetrics.builder().register(); // initialize the out-of-the-box JVM metrics
//		 counter = Counter.builder()
//				.name("my_count_total")
//				.help("example counter")
//				.labelNames("status")
//				.register();
//		counter.labelValues("ok").inc();
//		counter.labelValues("ok").inc();
//		counter.labelValues("ok").inc();
//		counter.labelValues("ok").inc();
//		counter.labelValues("ok").inc();
//		counter.labelValues("ok").inc();
//		counter.labelValues("error").inc();
//		HTTPServer server = HTTPServer.builder()
//				.port(9400)
//				.buildAndStart();
//		System.out.println("HTTPServer listening on port http://localhost:" + server.getPort() + "/metrics");
//		Thread.currentThread().join(); // sleep forever
	}

}
