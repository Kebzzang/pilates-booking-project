package com.keb.club_pila.service;

import com.keb.club_pila.dto.teacher.TeacherDto;
import com.keb.club_pila.dto.user.UserDto;
import com.keb.club_pila.model.entity.course.Teacher;
import com.keb.club_pila.model.entity.user.User;
import com.keb.club_pila.repository.UserRepository;
import com.keb.club_pila.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
            System.out.println("getPassword::"+userSaveRequestDto.getPassword());
            User entity=userSaveRequestDto.toEntity(passwordEncoder.encode(userSaveRequestDto.getPassword()));
            userRepository.save(entity);
            return entity.getId();
        }
        return 0L;

    }
    @Transactional(readOnly=true)
    public Optional<User> getMyUser(){
        System.out.println("problem here23");
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findUserByUsername);
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
