package com.codex.ecam.service.admin.passwordResetToken;

import com.codex.ecam.config.SpringConfiguration;
import com.codex.ecam.config.SpringSecurityConfiguration;
import com.codex.ecam.config.TestSpringConfiguration;
import com.codex.ecam.dao.admin.UserDao;
import com.codex.ecam.dto.admin.UserCredentialDTO;
import com.codex.ecam.security.CurrentUser;
import com.codex.ecam.service.admin.api.UserCredentialService;

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
public class UserTokenServiceTest {

	@Autowired
	private UserTokenServiceFindbyIdTestCase tokenServiceFindbyIdTestCase;

	@Autowired
	private UserTokenServiceCreateTestCase tokenServiceCreateTestCase;
	
	@Autowired
	private UserTokenServiceDeleteByTestCase tokenServiceDeleteByTestCase;
	
/*	@Autowired
	 private UserTokenSendingMail mailSend;*/

	@Autowired
	private UserCredentialService userCredentialService;

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setup() {
		addMockAunthentication();
		tokenServiceCreateTestCase.initialize();
		tokenServiceFindbyIdTestCase.initialize();
		tokenServiceDeleteByTestCase.initialize();
	//	mailSend.initialize();
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
	public void testCreateDefault() throws Exception {
		tokenServiceCreateTestCase.setTestName("Validate Password reset Token Creation");
		tokenServiceCreateTestCase.setTestCondition(tokenServiceCreateTestCase.CONDITION_SAVE_ENTITY,UserResetTokenDummyData.getInstance().getDummyDTOUserToken());
		tokenServiceCreateTestCase.setExpectedResult(tokenServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(tokenServiceCreateTestCase.isTestPass());
	}
	
	@Test
	public void testFindById() throws Exception {
		tokenServiceFindbyIdTestCase.setTestName("Validate Password reset Token Find by id");
		tokenServiceFindbyIdTestCase.setTestCondition(tokenServiceFindbyIdTestCase.CONDITION_EXPECTED_NAME, "Test Password reset Token");
		tokenServiceFindbyIdTestCase.setExpectedResult(tokenServiceFindbyIdTestCase.RESULT_IS_ERROR, false);
		assertTrue(tokenServiceFindbyIdTestCase.isTestPass());
	}
	

	
	@Test
	public void testDeleteById() throws Exception {
		tokenServiceDeleteByTestCase.setTestName("Validate  Password reset Token  Delete by id");
		tokenServiceDeleteByTestCase.setExpectedResult(tokenServiceDeleteByTestCase.RESULT_IS_ERROR, false);
		assertTrue(tokenServiceDeleteByTestCase.isTestPass());
	}
/*	@Test
	public void testMailSend() throws Exception {
		tokenServiceDeleteByTestCase.setTestName("Validate  Password reset Token  Delete by id");
		tokenServiceDeleteByTestCase.setExpectedResult(mailSend.RESULT_IS_ERROR, false);
		assertTrue(mailSend.isTestPass());
	}*/
}
