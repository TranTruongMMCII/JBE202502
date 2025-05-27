package vn.edu.r2s.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import vn.edu.r2s.dto.response.ClassRoomResponseDto;
import vn.edu.r2s.model.ClassRoom;

@Mapper
public interface ClassRoomMapper {

	ClassRoomMapper INSTANCE = Mappers.getMapper(ClassRoomMapper.class);

	ClassRoomResponseDto toDto(final ClassRoom classRoom);
}
