package org.pro.userservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.pro.userservice.dto.LogInResonseDto;
import org.pro.userservice.dto.LoginRequestDto;
import org.pro.userservice.dto.SignUpRequestDto;
import org.pro.userservice.dto.UserDto;
import org.pro.userservice.entity.Token;
import org.pro.userservice.entity.User;
import org.pro.userservice.exception.InvalidPasswordException;
import org.pro.userservice.mapper.TokenMapper;
import org.pro.userservice.mapper.UserMapper;
import org.pro.userservice.repository.TokenRepository;
import org.pro.userservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    /**
     * This method is used to sign up a user
     *
     * @param signUpRequestDto
     * @return UserDto
     */
    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        var user = userMapper.fromDto(signUpRequestDto);

        // Check if user already exists. If yes, throw an exception.
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new RuntimeException("User already exists");
        });

        // Proceed with user creation if the user does not exist.
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * This method is used to log in a user
     *
     * @param loginRequestDto
     * @return Token
     */
    public LogInResonseDto login(LoginRequestDto loginRequestDto) {
        /*
        Steps to be implemented
            1. Check if user exists
            2. Check if email is verified
            3. Check if password is correct
        */

        userRepository.findByEmail(loginRequestDto.getEmail()).ifPresentOrElse(user -> {
            if (!user.isEmailVerified()) {
                throw new RuntimeException("Email not verified");
            } else {
                if (!bCryptPasswordEncoder.matches(loginRequestDto.getPassword(), user.getHashedPassword())) {
                    throw new InvalidPasswordException("Invalid credentials");
                }
            }
        }, () -> {
            throw new RuntimeException("User not found");
        });
        var token = generateToken(userRepository.findByEmail(loginRequestDto.getEmail()).get());
        return tokenMapper.toDto(tokenRepository.save(token));
    }

    /**
     * This method is used to generate token
     *
     * @return Token
     */
    private Token generateToken(User user) {
        LocalDate currentTime = LocalDate.now(); // current time.
        LocalDate thirtyDaysFromCurrentTime = currentTime.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysFromCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryDate(expiryDate);

        //Token value is a randomly generated String of 128 characters.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        return token;
    }

    /**
     * This method is used to log out a user
     *
     * @param tokenValue
     * @return ResponseEntity<Void>
     */
    public void logout(String tokenValue) {
        //check if token exists in database and is_deleted is false
        //if yes set is_deleted to true
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(tokenValue, false);
        if (!tokenOptional.isPresent()) {
            throw new RuntimeException("Token not found");
        }
        Token token = tokenOptional.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }

    /**
     * This method is used to validate token
     *
     * @param tokenValue
     * @return
     */
    public UserDto validateToken(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeleted(tokenValue, false);
        if (!tokenOptional.isPresent()) {
            throw new RuntimeException("Invalid token passed");
        }
        return userMapper.toDto(tokenOptional.get().getUser());
    }
}