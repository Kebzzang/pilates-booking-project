package com.keb.club_pila.service;

import com.keb.club_pila.dto.user.LoginDto;
import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.entity.user.User;
import com.keb.club_pila.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Long userSave(UserDto.UserSaveRequestDto userSaveRequestDto){
        Optional<User> user=userRepository.findByUsername(userSaveRequestDto.getUsername());
        if(!user.isPresent()){
            User entity=userSaveRequestDto.toEntity(passwordEncoder.encode(userSaveRequestDto.getPassword()));
            userRepository.save(entity);
            return entity.getId();
        }
        return 0L;

    }

    public String userLogin(LoginDto loginDto){
        Optional<User> user=userRepository.findUserByUsername(loginDto.getUsername());
        if(user.isPresent()&& passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())){
            return loginDto.getUsername();
        }
        return null;
    }


    @Transactional
    public boolean deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            return false;
        userRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<UserDto.UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user ->
                new UserDto.UserResponseDto(user)
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public UserDto.UserResponseDto findById(Long id){
        Optional<User> users=userRepository.findById(id);
        if (users.isPresent()) {
            return new UserDto.UserResponseDto(users.get());
        } else return new UserDto.UserResponseDto();
    }

    @Transactional //이건 뭐...음...ㅎㅎ...
    public Long updateById(Long id, UserDto.UserUpdateDto userUpdateDto){
        Optional<User> user=userRepository.findById(id);
        if(!user.isPresent())
            return 0L;
        else{
            user.get().update(userUpdateDto.getRole());
            return id;
        }
    }


}
