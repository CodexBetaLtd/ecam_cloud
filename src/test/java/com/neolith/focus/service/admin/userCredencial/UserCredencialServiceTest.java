package com.neolith.focus.service.admin.userCredencial;

import com.neolith.focus.config.SpringConfiguration;
import com.neolith.focus.config.SpringSecurityConfiguration;
import com.neolith.focus.config.TestSpringConfiguration;
import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.security.CurrentUser;
import com.neolith.focus.service.admin.api.UserCredentialService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringConfiguration.class, SpringConfiguration.class, SpringSecurityConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class UserCredencialServiceTest {

	@Autowired
	private UserCredencialServiceFindbyUserIdTestCase userCredencialServiceFindbyUserIdTestCase;

	@Autowired
	private UserCredencialServiceUpdateTestCase userCredencialServiceUpdateTestCase;
	
	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		userCredencialServiceFindbyUserIdTestCase.initialize();
		userCredencialServiceUpdateTestCase.initialize();
	
	}
	@After
	public void teardown() {
	}
	private void addMockAunthentication() {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("USER_ROLE");
		UserCredentialDTO userDto = userCredentialService.findByUserName("admin");
		CurrentUser usr = new CurrentUser(userDto, authorities, userDao.findById(userDto.getUserId()));
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usr, "", authorities);
		authentication.setDetails(usr);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}


	@Test
	public void testFindByUserId() throws Exception {
		userCredencialServiceFindbyUserIdTestCase.setTestName("Validate Password reset Token Find by id");
		userCredencialServiceFindbyUserIdTestCase.setTestCondition(userCredencialServiceFindbyUserIdTestCase.CONDITION_EXPECTED_NAME, "Test Password");
		userCredencialServiceFindbyUserIdTestCase.setExpectedResult(userCredencialServiceFindbyUserIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(userCredencialServiceFindbyUserIdTestCase.isTestPass());
	}
	

	@Test
	public void testUpdateDefault() throws Exception {
		userCredencialServiceUpdateTestCase.setTestName("Validate Priority Update");
		userCredencialServiceUpdateTestCase.setTestCondition(userCredencialServiceUpdateTestCase.UPDATE_ENTITY_NAME,UserCredencialServiceUpdateTestCase.FieldName.PASSWORD.name() );
		userCredencialServiceUpdateTestCase.setTestCondition(userCredencialServiceUpdateTestCase.UPDATE_ENTITY_VALUE, "UpdatedPassword" );
		userCredencialServiceUpdateTestCase.setExpectedResult(userCredencialServiceUpdateTestCase.RESULT_IS_ERROR, false);
		assertTrue(userCredencialServiceUpdateTestCase.isTestPass());
	}
}
