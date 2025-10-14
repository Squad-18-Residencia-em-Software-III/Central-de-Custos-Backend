package com.example.demo.domain.dto.email;

public record EmailDto(
        String receiver,
        String subject,
        String text
) {
}
