package com.jpmc.midascore.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.foundation.Balance;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RestController
public class WebController {
    @Autowired
    DatabaseConduit databaseConduit;

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam long userId) {
        return databaseConduit.queryBalance(userId);
    }

}
