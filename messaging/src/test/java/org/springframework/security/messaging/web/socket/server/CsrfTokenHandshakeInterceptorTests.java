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

package org.springframework.security.messaging.web.socket.server;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.DeferredCsrfToken;
import org.springframework.web.socket.WebSocketHandler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
@ExtendWith(MockitoExtension.class)
public class CsrfTokenHandshakeInterceptorTests {

	@Mock
	WebSocketHandler wsHandler;

	@Mock
	ServerHttpResponse response;

	Map<String, Object> attributes;

	ServerHttpRequest request;

	MockHttpServletRequest httpRequest;

	CsrfTokenHandshakeInterceptor interceptor;

	@BeforeEach
	public void setup() {
		this.httpRequest = new MockHttpServletRequest();
		this.attributes = new HashMap<>();
		this.request = new ServletServerHttpRequest(this.httpRequest);
		this.interceptor = new CsrfTokenHandshakeInterceptor();
	}

	@Test
	public void beforeHandshakeNoAttribute() throws Exception {
		this.interceptor.beforeHandshake(this.request, this.response, this.wsHandler, this.attributes);
		assertThat(this.attributes).isEmpty();
	}

	@Test
	public void beforeHandshake() throws Exception {
		CsrfToken token = new DefaultCsrfToken("header", "param", "token");
		this.httpRequest.setAttribute(DeferredCsrfToken.class.getName(), new TestDeferredCsrfToken(token));
		this.interceptor.beforeHandshake(this.request, this.response, this.wsHandler, this.attributes);
		assertThat(this.attributes).containsOnlyKeys(CsrfToken.class.getName());
		CsrfToken csrfToken = (CsrfToken) this.attributes.get(CsrfToken.class.getName());
		assertThat(csrfToken.getHeaderName()).isEqualTo(token.getHeaderName());
		assertThat(csrfToken.getParameterName()).isEqualTo(token.getParameterName());
		assertThat(csrfToken.getToken()).isEqualTo(token.getToken());
		// Ensure the values of the CsrfToken are copied into a new token so the old token
		// is available for garbage collection.
		// This is required because the original token could hold a reference to the
		// HttpServletRequest/Response of the handshake request.
		assertThat(csrfToken).isNotSameAs(token);
	}

	private static final class TestDeferredCsrfToken implements DeferredCsrfToken {

		private final CsrfToken csrfToken;

		private TestDeferredCsrfToken(CsrfToken csrfToken) {
			this.csrfToken = csrfToken;
		}

		@Override
		public CsrfToken get() {
			return this.csrfToken;
		}

		@Override
		public boolean isGenerated() {
			return false;
		}

	}

}
