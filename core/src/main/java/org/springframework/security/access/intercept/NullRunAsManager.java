/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
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

package org.springframework.security.access.intercept;

import java.util.Collection;

import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

/**
 * Implementation of a {@link RunAsManager} that does nothing.
 * <p>
 * This class should be used if you do not require run-as authentication replacement
 * functionality.
 *
 * @author Ben Alex
 * @deprecated please see {@link RunAsManager} deprecation notice
 */
@NullUnmarked
@Deprecated
final class NullRunAsManager implements RunAsManager {

	@Override
	public @Nullable Authentication buildRunAs(Authentication authentication, Object object,
			Collection<ConfigAttribute> config) {
		return null;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
