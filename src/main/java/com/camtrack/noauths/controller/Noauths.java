// Decompiled by Procyon v0.5.30
//

package com.camtrack.noauths.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.entities.ServiceStatus;

@RestController
@RefreshScope
@CrossOrigin
@RequestMapping({ "/noauths" })
public class Noauths {
	@Value("${linkdnsforexternallogin.dns}")
	private String linkdnsforexternallogin;
	@Value("${server.port}")
	private int localServerPort;
	
	@GetMapping({ "/status" })
	@Cacheable("status")
	public ResponseEntity<?> status() {
		return ResponseEntity.status(HttpStatus.OK)
				.body((Object) new ServiceStatus("VideoYmaneProduction", Instant.now().toString()));
	}

}
