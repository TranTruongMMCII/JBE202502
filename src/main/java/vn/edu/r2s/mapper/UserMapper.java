package vn.edu.r2s.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import vn.edu.r2s.dto.request.SignUpRequest;
import vn.edu.r2s.dto.request.UserRequestDto;
import vn.edu.r2s.dto.response.UserResponseDto;
import vn.edu.r2s.model.User;

@Mapper // (componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(source = "user.profile.cccd", target = "cccd")
	UserResponseDto toDTO(final User user);

	@Mapping(source = "cccd", target = "profile.cccd")
	User toModel(final UserRequestDto dto);
	
	@Mapping(source = "displayName", target = "name")
	@Mapping(source = "cccd", target = "profile.cccd") 
	User toModel(final SignUpRequest dto);
}
