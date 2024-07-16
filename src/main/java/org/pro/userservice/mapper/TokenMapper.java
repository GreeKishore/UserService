package org.pro.userservice.mapper;

import org.pro.userservice.dto.LogInResonseDto;
import org.pro.userservice.entity.Token;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public LogInResonseDto toDto(Token token) {
        LogInResonseDto logInResonseDto = new LogInResonseDto();
        logInResonseDto.setToken(token.getValue());
        return logInResonseDto;
    }
}