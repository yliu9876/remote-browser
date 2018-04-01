package com.yliu.remotebrowser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class RemoteBrowserApplication {
//	@Autowired
//	Browser browser;

	public static void main(String[] args) {
		SpringApplication.run(RemoteBrowserApplication.class, args);
	}

	@PostMapping("/play")
	public void play(@RequestParam("reqUrl") String url) {
		Browser browser = new Browser();
		browser.browse(url);
	}

}
