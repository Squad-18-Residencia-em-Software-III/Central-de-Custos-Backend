package com.example.demo.domain.services.email;

import com.example.demo.domain.dto.email.EmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service", url = "${email.service.url}")
public interface EmailServiceSender {

    @PostMapping(value = "/send", consumes = "application/json")
    void sendEmail(@RequestBody EmailDto emailRequest);
}
