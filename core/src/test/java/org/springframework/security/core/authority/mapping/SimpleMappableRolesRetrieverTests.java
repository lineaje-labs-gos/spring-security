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

package org.springframework.security.core.authority.mapping;

import java.util.Set;

import org.junit.jupiter.api.Test;

import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author TSARDD
 * @since 18-okt-2007
 */
public class SimpleMappableRolesRetrieverTests {

	@Test
	public final void testGetSetMappableRoles() {
		Set<String> roles = StringUtils.commaDelimitedListToSet("Role1,Role2");
		SimpleMappableAttributesRetriever r = new SimpleMappableAttributesRetriever();
		r.setMappableAttributes(roles);
		Set<String> result = r.getMappableAttributes();
		assertThat(roles.containsAll(result) && result.containsAll(roles))
			.withFailMessage("Role collections do not match; result: " + result + ", expected: " + roles)
			.isTrue();
	}

}
