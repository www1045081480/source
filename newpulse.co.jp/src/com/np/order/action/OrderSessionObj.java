package com.np.order.action;

import com.np.order.models.Users;

public class OrderSessionObj {
	private Long userId;
	private Users user;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
		this.userId = user.getUserId();
	}
}
