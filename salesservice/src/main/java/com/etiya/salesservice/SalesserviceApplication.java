package com.etiya.salesservice;

import com.etiya.common.annotations.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.etiya.salesservice","com.etiya.common"})
@EnableDiscoveryClient
@EnableSecurity
public class SalesserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesserviceApplication.class, args);
	}

}
