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

package org.springframework.security.rsocket.authentication;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.rsocket.api.PayloadExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 */
@ExtendWith(MockitoExtension.class)
public class AnonymousPayloadInterceptorTests {

	@Mock
	private PayloadExchange exchange;

	private AnonymousPayloadInterceptor interceptor;

	@BeforeEach
	public void setup() {
		this.interceptor = new AnonymousPayloadInterceptor("key");
	}

	@Test
	public void constructorKeyWhenKeyNullThenException() {
		String key = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousPayloadInterceptor(key));
	}

	@Test
	public void constructorKeyPrincipalAuthoritiesWhenKeyNullThenException() {
		String key = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousPayloadInterceptor(key, "principal",
				AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
	}

	@Test
	public void constructorKeyPrincipalAuthoritiesWhenPrincipalNullThenException() {
		Object principal = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousPayloadInterceptor("key", principal,
				AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
	}

	@Test
	public void constructorKeyPrincipalAuthoritiesWhenAuthoritiesNullThenException() {
		List<GrantedAuthority> authorities = null;
		assertThatIllegalArgumentException()
			.isThrownBy(() -> new AnonymousPayloadInterceptor("key", "principal", authorities));
	}

	@Test
	public void interceptWhenNoAuthenticationThenAnonymousAuthentication() {
		AuthenticationPayloadInterceptorChain chain = new AuthenticationPayloadInterceptorChain();
		this.interceptor.intercept(this.exchange, chain).block();
		Authentication authentication = chain.getAuthentication();
		assertThat(authentication).isInstanceOf(AnonymousAuthenticationToken.class);
	}

	@Test
	public void interceptWhenAuthenticationThenOriginalAuthentication() {
		AuthenticationPayloadInterceptorChain chain = new AuthenticationPayloadInterceptorChain();
		TestingAuthenticationToken expected = new TestingAuthenticationToken("test", "password");
		this.interceptor.intercept(this.exchange, chain)
			.contextWrite(ReactiveSecurityContextHolder.withAuthentication(expected))
			.block();
		Authentication authentication = chain.getAuthentication();
		assertThat(authentication).isEqualTo(expected);
	}

}
