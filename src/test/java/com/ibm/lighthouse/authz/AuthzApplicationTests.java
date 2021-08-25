package com.ibm.lighthouse.authz;

import com.ibm.lighthouse.ContentAccess;
import org.casbin.jcasbin.main.Enforcer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AuthzApplicationTests {
	@Autowired
	private Enforcer enforcer;

	private static final Logger LOG = LoggerFactory.getLogger(AuthzApplicationTests.class);

	@Test
	void testPaths() {
		assertEquals(true, enforcer.enforce("_", "landre", "/sales/landre"));
	}

	@Test
	void testAbac() {
		ContentAccess test = new Something("hello", "hello");
		assertEquals(true, enforcer.enforce(test, "landre", "content"));
	}

}
