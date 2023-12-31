package ru.practicum.ewm.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.core.exception.NotFoundException;
import ru.practicum.ewm.user.dto.CreateUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public List<UserDto> getAllUsers(List<Long> userIds, Pageable pageable) {
        boolean isUserIdsEmptyOrNull = userIds == null || userIds.isEmpty();

        List<User> users = isUserIdsEmptyOrNull ?
                userRepository.findAll(pageable).toList() :
                userRepository.findAllByIdIn(userIds, pageable);

        return users
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        User user = userMapper.toUser(createUserDto);

        return userMapper.toUserDto(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(long userId) {
        checkUser(userId);
        userRepository.deleteById(userId);
    }

    private void checkUser(long userId) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("user", userId));
    }
}
