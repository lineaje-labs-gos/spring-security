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

/**
 * Strategies for mapping a list of attributes (such as roles or LDAP groups) to a list of
 * {@code GrantedAuthority}s.
 * <p>
 * Provides a layer of indirection between a security data repository and the logical
 * authorities required within an application.
 */
@NullMarked
package org.springframework.security.core.authority.mapping;

import org.jspecify.annotations.NullMarked;
