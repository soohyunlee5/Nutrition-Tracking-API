package com.soohyun.nutrition_api.service;

import com.soohyun.nutrition_api.model.User;
import com.soohyun.nutrition_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers() {
        User user1 = new User("Soohyun", "soohyun@gmail.com", "soohyun123");
        User user2 = new User("Cosby", "cosby@gmail.com", "cosby123");
        given(userRepository.findAll()).willReturn(List.of(user1, user2));
        List<User> userList = userService.getAllUsers();
        assertThat(userList).isNotNull()
                .hasSize(2)
                .extracting(User::getName)
                .containsExactly("Soohyun", "Cosby");
        verify(userRepository).findAll();
    }

    @Test
    void getUserById() {
        User user = new User("Soohyun", "soohyun@gmail.com", "soohyun123");
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        Optional<User> userId = userService.getUserById(user.getId());
        assertThat(userId).isPresent().contains(user);
        verify(userRepository).findById(user.getId());
    }

    @Test
    void getUserByEmail() {
        User user = new User("Soohyun", "soohyun@gmail.com", "soohyun123");
        given(userRepository.findByEmail(user.getUsername())).willReturn(Optional.of(user));
        Optional<User> userEmail = userService.getUserByEmail(user.getUsername());
        assertThat(userEmail).isPresent().contains(user);
        verify(userRepository).findByEmail(user.getUsername());
    }

    @Test
    void createUser() {
        User user = new User("Soohyun", "soohyun@gmail.com", "soohyun123");
        given(userRepository.save(user)).willReturn(user);
        User newUser = userService.createUser(user);
        assertThat(newUser).isNotNull().isSameAs(user);
        assertThat(newUser.getName()).isEqualTo("Soohyun");
        verify(userRepository).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser() {
        User user = new User("Soohyun", "soohyun@gmail.com", "soohyun123");
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepository).findById(user.getId());
        verify(userRepository).delete(user);
        verify(userRepository).flush();
        verifyNoMoreInteractions(userRepository);
    }
}
