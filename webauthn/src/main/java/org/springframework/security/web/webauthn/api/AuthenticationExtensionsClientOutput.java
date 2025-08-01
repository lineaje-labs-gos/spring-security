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

package org.springframework.security.web.webauthn.api;

import java.io.Serializable;

/**
 * A <a href="https://www.w3.org/TR/webauthn-3/#client-extension-output">client extension
 * output</a> entry in {@link AuthenticationExtensionsClientOutputs}.
 *
 * @param <T>
 * @see AuthenticationExtensionsClientOutputs#getOutputs()
 * @see CredentialPropertiesOutput
 */
public interface AuthenticationExtensionsClientOutput<T> extends Serializable {

	/**
	 * Gets the <a href="https://www.w3.org/TR/webauthn-3/#extension-identifier">extension
	 * identifier</a>.
	 * @return the
	 * <a href="https://www.w3.org/TR/webauthn-3/#extension-identifier">extension
	 * identifier</a>.
	 */
	String getExtensionId();

	/**
	 * The <a href="https://www.w3.org/TR/webauthn-3/#client-extension-output">client
	 * extension output</a>.
	 * @return the
	 * <a href="https://www.w3.org/TR/webauthn-3/#client-extension-output">client
	 * extension output</a>.
	 */
	T getOutput();

}
