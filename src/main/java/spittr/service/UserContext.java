package spittr.service;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserContext {
	private long userId;

	private static class UserContextHolder {
		private static UserContext instance = UserContext.builder().build();
	}

	public static UserContext getInstance() {
		return UserContextHolder.instance;
	}

	public static void reset(Long userId) {
		UserContextHolder.instance = UserContext.builder().userId(userId).build();
	}
}
