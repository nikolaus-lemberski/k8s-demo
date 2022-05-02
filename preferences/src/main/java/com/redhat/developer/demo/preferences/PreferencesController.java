package com.redhat.developer.demo.preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreferencesController {

    private static final Logger LOG = LoggerFactory.getLogger(PreferencesController.class);
    private static final String RESPONSE = "preferences v1 from '%s': %d\n";

    @Value("${HOSTNAME:unknown}")
    private String hostName;

    private int count = 0;
    private boolean fail = false;

    @GetMapping
    public ResponseEntity<String> getPreferences() {
        count++;

        if (fail) {
            LOG.error("The preferences service is supposed to fail. Call endpoint 'fixme' to fix it.");
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format("preferences from '%s' is supposed to fail\n", hostName));
        }

        return ResponseEntity.ok(String.format(RESPONSE, hostName, count));
    }

    @GetMapping("/fail")
    @ResponseStatus(HttpStatus.OK)
    public String fail() {
        fail = true;
        return "The preferences service is now failing";
    }

    @GetMapping("/fixme")
    @ResponseStatus(HttpStatus.OK)
    public String fixPreferences() {
        fail = false;
        return "The preferences service is now fixed.";
    }

}
