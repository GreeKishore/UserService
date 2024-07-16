package org.pro.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.pro.userservice.dto.LogInResonseDto;
import org.pro.userservice.dto.LoginRequestDto;
import org.pro.userservice.dto.SignUpRequestDto;
import org.pro.userservice.dto.UserDto;
import org.pro.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return userService.signUp(signUpRequestDto);
    }

    @PostMapping("/login")
    public LogInResonseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    @PostMapping("/logout/{tokenValue}")
    public void logout(@PathVariable("tokenValue") String tokenValue) {
        userService.logout(tokenValue);
    }

    @PostMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) {
        return userService.validateToken(tokenValue);
    }
}