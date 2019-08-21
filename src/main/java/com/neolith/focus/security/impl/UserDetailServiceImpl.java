
package com.neolith.focus.security.impl;

import com.neolith.focus.constants.UserLevel;
import com.neolith.focus.dao.admin.UserDao;
import com.neolith.focus.dto.admin.UserCredentialDTO;
import com.neolith.focus.model.admin.User;
import com.neolith.focus.security.CurrentUser;
import com.neolith.focus.service.admin.api.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserCredentialService userCredentialService;
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserCredentialDTO userCredentialDto = userCredentialService.findByUserName(username);
		
        if (userCredentialDto == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        final User userDetail  = userDao.findById(userCredentialDto.getUserId());
        		
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();        
        GrantedAuthority grantedLevelAuthority;
        if(userDetail.getUserLevel().equals(UserLevel.ADMIN_LEVEL)){
        	grantedLevelAuthority = new SimpleGrantedAuthority("ADMIN_LEVEL");
        } else if(userDetail.getUserLevel().equals(UserLevel.SYSTEM_LEVEL)){
        	grantedLevelAuthority = new SimpleGrantedAuthority("SYSTEM_LEVEL");
        } else {
        	grantedLevelAuthority = new SimpleGrantedAuthority("NORMAL_LEVEL");
        }
        
        authorities.add(grantedLevelAuthority);
       
        return new CurrentUser(userCredentialDto, authorities, userDetail);
	}


}

