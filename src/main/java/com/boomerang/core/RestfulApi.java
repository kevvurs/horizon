package com.boomerang.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.boomerang.evote.DataLink;

@RestController
public class RestfulApi {
    private static final String PING = "OK";

    @Value("${boomerang.status.build}")
    String buildValue;
    
    @Autowired
    DataLink dataLink;
    
    // Check service.
    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "text/plain")
    public String ping() {
        return PING;
    }
    
    @RequestMapping(value = "/touch", method = RequestMethod.GET, produces = "text/plain")
    public String touch() {
        return dataLink.touch();
    }
}
