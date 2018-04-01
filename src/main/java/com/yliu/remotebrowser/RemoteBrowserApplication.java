package com.yliu.remotebrowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class RemoteBrowserApplication {


	static {
		System.setProperty("java.awt.headless", "false");
	}

	public static void main(String[] args) {
		SpringApplication.run(RemoteBrowserApplication.class, args);
	}


}
