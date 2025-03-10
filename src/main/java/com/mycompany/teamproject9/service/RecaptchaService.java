package com.mycompany.teamproject9.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secretkey}")
    private String secretKey;

    private final RestTemplate restTemplate;

    public RecaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyRecaptcha(String recaptchaResponse) {
        String url = "https://www.google.com/recaptcha/api/siteverify";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", recaptchaResponse);

        // Google reCAPTCHA 검증 API에 요청
        ResponseEntity<String> response = restTemplate.postForEntity(url, params, String.class);

        // 응답에서 success 값을 확인
        return response.getBody().contains("\"success\": true");
    }
}
