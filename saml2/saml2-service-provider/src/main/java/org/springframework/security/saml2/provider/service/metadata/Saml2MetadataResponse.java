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

package org.springframework.security.saml2.provider.service.metadata;

public class Saml2MetadataResponse {

	private final String metadata;

	private final String fileName;

	public Saml2MetadataResponse(String metadata, String fileName) {
		this.metadata = metadata;
		this.fileName = fileName;
	}

	public String getMetadata() {
		return this.metadata;
	}

	public String getFileName() {
		return this.fileName;
	}

}
