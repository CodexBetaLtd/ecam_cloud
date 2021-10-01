package com.codex.ecam.cache;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

@Component("customRequestCache")
public class CustomRequestCache extends HttpSessionRequestCache {
    
	public CustomRequestCache() {
        super();
    }
}
