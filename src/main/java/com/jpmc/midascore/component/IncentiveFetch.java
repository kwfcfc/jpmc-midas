package com.jpmc.midascore.component;

import static org.junit.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.foundation.Transaction;

@Component
public class IncentiveFetch {
    private final String incentiveAPI;

    private static class IncentiveResponse {
        public float amount;
    }

    public IncentiveFetch(@Value("${general.incentive-url}") String url) {
        this.incentiveAPI = url;
    }

    public float fetch(Transaction transaction) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Transaction> request = new HttpEntity<>(transaction);     
        IncentiveResponse response = restTemplate.postForObject(incentiveAPI, request, IncentiveResponse.class);
        assertNotNull(response);
        return response.amount;
    }
}