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

package org.springframework.security.access.expression;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.log.LogMessage;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

/**
 * A null PermissionEvaluator which denies all access. Used by default for situations when
 * permission evaluation should not be required.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public class DenyAllPermissionEvaluator implements PermissionEvaluator {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * @return false always
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Object target, Object permission) {
		this.logger.warn(LogMessage.format("Denying user %s permission '%s' on object %s", authentication.getName(),
				permission, target));
		return false;
	}

	/**
	 * @return false always
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		this.logger.warn(LogMessage.format("Denying user %s permission '%s' on object with Id %s",
				authentication.getName(), permission, targetId));
		return false;
	}

}
