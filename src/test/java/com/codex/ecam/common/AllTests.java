package com.codex.ecam.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.codex.ecam.security.UserDetailServiceTest;
import com.codex.ecam.service.admin.business.BusinessServiceTest;
import com.codex.ecam.service.admin.notification.NotificationServiceTest;
import com.codex.ecam.service.admin.user.UserServiceTest;
import com.codex.ecam.service.admin.usergroup.UserGroupServiceTest;
import com.codex.ecam.service.admin.usersite.UserSiteTest;

@RunWith(Suite.class)
@SuiteClasses({
        UserDetailServiceTest.class,
        UserGroupServiceTest.class,
        BusinessServiceTest.class,
        UserServiceTest.class,
        NotificationServiceTest.class,
        UserSiteTest.class
})
// add other test classes inside this annotation
public class AllTests {
	
} 
