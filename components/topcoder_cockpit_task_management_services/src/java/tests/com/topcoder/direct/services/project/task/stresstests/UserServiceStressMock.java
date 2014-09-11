/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.stresstests;

import java.util.Date;
import java.util.List;

import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;

/**
 * <p>
 * Stress mock of UserService.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class UserServiceStressMock implements UserService {


	public void activateUser(String arg0) throws UserServiceException {

	}


	public void addUserTerm(String arg0, long arg1, Date arg2)
			throws UserServiceException {
		// TODO Auto-generated method stub

	}


	public void addUserTerm(long arg0, long arg1, Date arg2)
			throws UserServiceException {
		// TODO Auto-generated method stub

	}


	public void addUserToGroups(String arg0, long[] arg1)
			throws UserServiceException {
		// TODO Auto-generated method stub

	}


	public void addUserToGroups(long arg0, long[] arg1)
			throws UserServiceException {
		// TODO Auto-generated method stub

	}


	public boolean checkUserTerm(long arg0, long arg1)
			throws UserServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public String getEmailAddress(long arg0) throws UserServiceException {
		return "email"+arg0+"@sohu.com";
	}


	public String getEmailAddress(String arg0) throws UserServiceException {
		return "email"+arg0+"@sohu.com";
	}


	public User getUser(long arg0) throws UserServiceException {
		User user = new User();
		user.setUserId(arg0);
		user.setHandle("user"+arg0);
		user.setEmailAddress("email"+arg0+"@sohu.com");
		return user ;
	}


	public User getUserByEmail(String arg0) throws UserServiceException {
		return null;
	}


	public String getUserHandle(long arg0) throws UserServiceException {
		return "user"+arg0;
	}


	public long getUserId(String arg0) throws UserServiceException {
		return 0;
	}


	public UserInfo getUserInfo(String arg0) throws UserServiceException {
		return null;
	}


	public boolean isAdmin(String arg0) throws UserServiceException {
		// TODO Auto-generated method stub
		return false;
	}


	public long registerUser(User arg0) throws UserServiceException {
		// TODO Auto-generated method stub
		return 0;
	}


	public void removeUserFromGroups(String arg0, long[] arg1)
			throws UserServiceException {
		// TODO Auto-generated method stub

	}


	public void removeUserTerm(String arg0, long arg1)
			throws UserServiceException {
		// TODO Auto-generated method stub

	}


	public List<User> searchUser(String arg0) throws UserServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
