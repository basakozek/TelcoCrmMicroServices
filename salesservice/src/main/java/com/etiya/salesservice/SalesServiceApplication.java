package com.etiya.salesservice;

import com.etiya.common.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = {"com.etiya.salesservice","com.etiya.common"})
@EnableDiscoveryClient
@EnableSecurity
@EnableFeignClients
@EnableMongoAuditing // (CustomerProduct'taki @CreatedDate için bu da gerekliydi)
public class SalesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesServiceApplication.class, args);
	}

}
