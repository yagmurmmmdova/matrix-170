package com.example.springdemo1705.service;

import com.example.springdemo1705.entity.RoleEntity;
import com.example.springdemo1705.entity.UserEntity;
import com.example.springdemo1705.exception.UserNotFoundException;
import com.example.springdemo1705.exception.UsernameAlreadyExistsException;
import com.example.springdemo1705.model.UserRequestDto;
import com.example.springdemo1705.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRequestDto userRequestDto) {
        if(userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException(userRequestDto.getUsername());
        }
        var role = RoleEntity.builder().name("ROLE_USER").build();

        var user = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .roles(List.of(role))
                .build();

        role.setUserEntity(user);
        userRepository.save(user);
    }

    @Transactional
    public void addRoleToUser(Integer userId, String roleName) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        String formattedRole = roleName.startsWith("ROLE_")
                ? roleName.toUpperCase() : "ROLE_" + roleName.toUpperCase();

        boolean alreadyHasRole = user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(formattedRole));

        if (!alreadyHasRole) {
            RoleEntity newRole = RoleEntity.builder()
                    .name(formattedRole)
                    .userEntity(user)
                    .build();
            user.getRoles().add(newRole);
            userRepository.save(user);
        }
    }
}
