package com.michalswistowski.redis_cache_demo;

import org.springframework.boot.SpringApplication;

public class TestRedisCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(RedisCacheDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
