package com.skt.autopay.boot.config;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로컬 DB 조회 — @Profile local
 */
@Profile("local")
@RestController
public class DevDbConsoleController {
}
