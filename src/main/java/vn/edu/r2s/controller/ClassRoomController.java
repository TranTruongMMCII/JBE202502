package vn.edu.r2s.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.r2s.dto.request.EnrollmentRequestDto;
import vn.edu.r2s.dto.response.ClassRoomResponseDto;
import vn.edu.r2s.dto.response.EnrollmentResponseDto;
import vn.edu.r2s.exception.UserNotFoundException;
import vn.edu.r2s.mapper.ClassRoomMapper;
import vn.edu.r2s.mapper.UserMapper;
import vn.edu.r2s.model.ClassRoom;
import vn.edu.r2s.model.User;
import vn.edu.r2s.repository.ClassRoomRepository;
import vn.edu.r2s.response.SuccessResponse;
import vn.edu.r2s.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/class-rooms")
@RequiredArgsConstructor
public class ClassRoomController {

	private final UserService userService;
	private final ClassRoomRepository classRoomRepository;

	@PostMapping(path = "/enroll")
	public SuccessResponse<EnrollmentResponseDto> enroll(@RequestBody EnrollmentRequestDto dto) {
		// check valid 2 ids
		User foundUser = this.userService.getById(dto.getUserId())
				.orElseThrow(() -> new UserNotFoundException("user id: " + dto.getUserId()));
		ClassRoom foundClassRoom = this.classRoomRepository.findById(dto.getClassId())
				.orElseThrow(() -> new UserNotFoundException("clas id: " + dto.getClassId())); // nho tao them exception cho class room
//		System.out.println("chu y dong tiep theo: ");
//		List<User> users = foundClassRoom.getUsers();
//		users.forEach(System.out::println);
		
		// insert ben 1
//		foundUser.setClassRoom(foundClassRoom);
//		foundClassRoom.getUsers().add(foundUser);
//		foundClassRoom = this.classRoomRepository.save(foundClassRoom);
		
		// insert ben nhieu
		foundUser.setClassRoom(foundClassRoom);
		foundUser = this.userService.save(foundUser);
		
		return SuccessResponse.of(EnrollmentResponseDto.builder()
				.userResponse(UserMapper.INSTANCE.toDTO(foundUser))
				.clasRoomResponse(ClassRoomMapper.INSTANCE.toDto(foundClassRoom))
				.build());
	}

}
