package com.example.Online.Book.Library.service.serviceImpl;

import com.example.Online.Book.Library.constants.AppConstants;
import com.example.Online.Book.Library.dto.UserDto;
import com.example.Online.Book.Library.entity.UserEntity;
import com.example.Online.Book.Library.repository.UserRepository;
import com.example.Online.Book.Library.service.UserService;
import com.example.Online.Book.Library.utils.JWTUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new Exception("User exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setAddress(userDto.getAddress());
        userEntity.setRole(userDto.getRole());
        UserEntity userEntityDetails = userRepository.save(userEntity);

        ModelMapper modelMapper = new ModelMapper();

        UserDto userDtoObj = modelMapper.map(userEntityDetails, UserDto.class);
        String token = JWTUtils.generateToken(userEntity.getEmail());
        userDtoObj.setAccessToken(AppConstants.TOKEN_PREFIX + token);
        return userDtoObj;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        //if (userEntity == null) throw new Exception("User email does not exist");
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(Long id) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(id);
        if (userEntity == null) throw new Exception("User id does not exist");
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        //if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true, new ArrayList<>());
    }
}
