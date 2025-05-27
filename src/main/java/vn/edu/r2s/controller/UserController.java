package vn.edu.r2s.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.r2s.dto.request.UserRequestDto;
import vn.edu.r2s.dto.response.UserResponseDto;
import vn.edu.r2s.mapper.UserMapper;
import vn.edu.r2s.model.User;
import vn.edu.r2s.response.SuccessResponse;
import vn.edu.r2s.service.UserService;

@RestController
@RequestMapping(path = "/users") // path = host:port/users, resource
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
//	private final UserMapper userMapper;

	@Deprecated(forRemoval = true, since = "2025.05.27")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	@GetMapping(path = "/hello") // endpoint = .../users/hello
	public String hello() {
		System.err.println(SecurityContextHolder.getContext().getAuthentication().getName());
		return "hello, world!";
	}

	@Operation(description = "api nay dung de lay tat ca users ne")
	@GetMapping(path = "")
	public SuccessResponse<List<UserResponseDto>> getAll() {
//		return SuccessResponse.of(this.userService.getAll().stream().map(UserResponseDto::new).toList());
//		return SuccessResponse.of(this.userService.getAll().stream().map(this.userMapper::toDTO).toList());
		return SuccessResponse.of(this.userService.getAll().stream().map(UserMapper.INSTANCE::toDTO).toList());
	}
	
	@GetMapping(path = "/search")
	public SuccessResponse<List<User>> search(
			@RequestParam(value = "name") String name,
			Pageable pageable
	) {
		return SuccessResponse.of(this.userService.search(name, pageable), pageable.getPageNumber());
	}
	
	@GetMapping(path = "/searchManually")
	public SuccessResponse<List<User>> searchManually(
//			@RequestParam(value = "name") String name,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sorts", defaultValue = "") String sorts
	) {
		// tao page request
		PageRequest pageRequest = null;
		
		// tach chuoi sorts thanh tung thanh phan
		String[] sortArr = sorts.split(",");
		if (sorts.isBlank() || sortArr.length == 0) {
			pageRequest = PageRequest.of(page, size);
		} else {
			// xu ly field va direction
			List<Order> orders = new ArrayList<Sort.Order>();
			for (int i = 0; i < sortArr.length; i += 2) {
				orders.add(new Order(Direction.fromString(sortArr[i + 1]), sortArr[i]));
			}

			// tao object sort
			Sort sort = Sort.by(orders);
			pageRequest = PageRequest.of(page, size, sort);
		}
		
		return SuccessResponse.of(this.userService.search("", pageRequest), page);
	}

	@GetMapping(path = "/findById/{id}")
	public SuccessResponse<User> getById(@PathVariable(name = "id", required = false) Integer id) {
		return SuccessResponse.of(this.userService.getById(id).get());
	}

	@GetMapping(path = "/findByIdParams")
	public User getByIdParams(@RequestParam(name = "id", required = false, defaultValue = "1") Integer id) {
		return this.userService.getById(id).get();
	}

	@PostMapping(path = "")
	public SuccessResponse<UserResponseDto> save(@RequestBody @Valid UserRequestDto dto) {
		// b1: convert to user
		User newUser = UserMapper.INSTANCE.toModel(dto);

		// b2: save
		newUser = this.userService.save(newUser);

		// b3
		return SuccessResponse.of(UserMapper.INSTANCE.toDTO(newUser));
	}

	@PutMapping(path = "")
	public User update(@RequestBody User newUser) {
		return this.userService.update(newUser);
	}

	@DeleteMapping(path = "")
	public boolean delete(@RequestBody User newUser) {
		return this.userService.delete(newUser);
	}
	
	@DeleteMapping(path = "/hard/{id}")
	public boolean delete(@PathVariable(name = "id") Integer id) {
		return this.userService.delete(id);
	}
}
