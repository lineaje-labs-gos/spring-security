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

package org.springframework.security.taglibs.csrf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nick Williams
 */
public class CsrfMetaTagsTagTests {

	public CsrfMetaTagsTag tag;

	@BeforeEach
	public void setUp() {
		this.tag = new CsrfMetaTagsTag();
	}

	@Test
	public void handleTokenRendersTags() {
		CsrfToken token = new DefaultCsrfToken("X-Csrf-Token", "_csrf", "abc123def456ghi789");
		String value = this.tag.handleToken(token);
		assertThat(value).as("The returned value should not be null.").isNotNull();
		assertThat(value).withFailMessage("The output is not correct.")
			.isEqualTo("<meta name=\"_csrf_parameter\" content=\"_csrf\" />"
					+ "<meta name=\"_csrf_header\" content=\"X-Csrf-Token\" />"
					+ "<meta name=\"_csrf\" content=\"abc123def456ghi789\" />");
	}

	@Test
	public void handleTokenRendersTagsDifferentToken() {
		CsrfToken token = new DefaultCsrfToken("csrfHeader", "csrfParameter", "fooBarBazQux");
		String value = this.tag.handleToken(token);
		assertThat(value).as("The returned value should not be null.").isNotNull();
		assertThat(value).withFailMessage("The output is not correct.")
			.isEqualTo("<meta name=\"_csrf_parameter\" content=\"csrfParameter\" />"
					+ "<meta name=\"_csrf_header\" content=\"csrfHeader\" />"
					+ "<meta name=\"_csrf\" content=\"fooBarBazQux\" />");
	}

}
