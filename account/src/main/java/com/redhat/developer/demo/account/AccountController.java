package com.redhat.developer.demo.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    private static final String RESPONSE = "account from host '%s' => %s\n";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${HOSTNAME:unknown}")
    private String hostName;

    @Value("${preferences.api.url}")
    private String preferencesService;

    @GetMapping
    public ResponseEntity<String> getAccount() {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(preferencesService, String.class);
            String response = responseEntity.getBody();
            return ResponseEntity
                    .ok(String.format(RESPONSE, hostName, response.trim()));
        } catch (HttpStatusCodeException e) {
            LOG.warn("Exeption while calling preferences service", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format(RESPONSE, hostName, "Error " + e.getStatusCode()));
        } catch (RestClientException e) {
            LOG.error("Exeption while calling preferences service", e);
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format(RESPONSE, hostName, e.getMessage()));
        }
    }

}
