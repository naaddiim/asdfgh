package id.bangkit.facetrack.facetrack.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.bangkit.facetrack.facetrack.dto.UserDTO;
import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.mappers.Mapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements Mapper<User, UserDTO> {
     private final ModelMapper modelMapper;

    @Override
    public UserDTO mapTo(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    
}
