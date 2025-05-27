package vn.edu.r2s.response;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessResponse<T> {

	private final OperationType operationType = OperationType.SUCCESS;
	private final String message = "success";
	private ErrorCode code;
	private T data;
	private final OffsetDateTime timestamp = OffsetDateTime.now();
	private int size;
	private int page;

	public static <T> SuccessResponse<T> of(final T data) {
		return SuccessResponse.<T>builder()
				.data(data)
				.code(ErrorCode.OK)
				.size(getSize(data))
				.build();
	}
	
	public static <T> SuccessResponse<T> of(final T data, final int page) {
		return SuccessResponse.<T>builder()
				.data(data)
				.code(ErrorCode.OK)
				.size(getSize(data))
				.page(page)
				.build();
	}
	
	private static <T> int getSize(final T data) {
		if(Objects.nonNull(data) && data instanceof Collection<?>) {
			return ((Collection<?>)data).size();
		}
		
		return 0;
	}
}
