/*
 * Copyright 2004-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.authentication;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Josh Cummings
 */
public class TestingAuthenticationTokenTests {

	@Test
	public void constructorWhenNoAuthoritiesThenUnauthenticated() {
		TestingAuthenticationToken unauthenticated = new TestingAuthenticationToken("principal", "credentials");
		assertThat(unauthenticated.isAuthenticated()).isFalse();
	}

	@Test
	public void constructorWhenArityAuthoritiesThenAuthenticated() {
		TestingAuthenticationToken authenticated = new TestingAuthenticationToken("principal", "credentials",
				"authority");
		assertThat(authenticated.isAuthenticated()).isTrue();
	}

	@Test
	public void constructorWhenCollectionAuthoritiesThenAuthenticated() {
		TestingAuthenticationToken authenticated = new TestingAuthenticationToken("principal", "credentials",
				Arrays.asList(new SimpleGrantedAuthority("authority")));
		assertThat(authenticated.isAuthenticated()).isTrue();
	}

}
