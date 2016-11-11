package com.boomerang.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulApi {
    private static final String PING = "OK";

    @Value("${boomerang.status.build}")
    String buildValue;

    // Check service.
    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "text/plain")
    public String greeting() {
        return PING;
    }
}
