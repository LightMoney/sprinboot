package com.example.elk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
 
    @GetMapping("/index")
    public Object index() {
        for (int i = 0; i < 100; i++) {
            logger.debug("debug" + i);
            logger.info("info" + i);
            logger.warn("warn" + i);
            logger.error("error" + i);
        }
 
        return "success";
    }
}