package org.pro.userservice.mapper;


import lombok.RequiredArgsConstructor;
import org.pro.userservice.dto.SignUpRequestDto;
import org.pro.userservice.dto.UserDto;
import org.pro.userservice.entity.BaseModel;
import org.pro.userservice.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper extends BaseModel {

    private final BCryptPasswordEncoder bCryptPasswordEncoder; //bean created in Configuration class because it is used in multiple classes and from third party library

    /**
     * This method is used to map SignUpRequestDto to User
     * @param signUpRequestDto
     * @return User
     */
    public User fromDto(SignUpRequestDto signUpRequestDto) {
        return new User()
                .setEmail(signUpRequestDto.getEmail())
                .setName(signUpRequestDto.getName())
                .setHashedPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
    }

    /**
     * This method is used to map User to UserDto
     * @param user
     * @return UserDto
     */
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRole());
        userDto.setEmailVerified(user.isEmailVerified());
        return userDto;
    }
}