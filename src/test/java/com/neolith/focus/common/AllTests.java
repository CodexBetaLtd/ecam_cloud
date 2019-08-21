package com.neolith.focus.common;

import com.neolith.focus.security.UserDetailServiceTest;
import com.neolith.focus.service.admin.business.BusinessServiceTest;
import com.neolith.focus.service.admin.notification.NotificationServiceTest;
import com.neolith.focus.service.admin.user.UserServiceTest;
import com.neolith.focus.service.admin.usergroup.UserGroupServiceTest;
import com.neolith.focus.service.admin.usersite.UserSiteTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

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
