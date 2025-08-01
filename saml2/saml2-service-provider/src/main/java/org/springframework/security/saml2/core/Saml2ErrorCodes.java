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

package org.springframework.security.saml2.core;

/**
 * A list of SAML known 2 error codes used during SAML authentication.
 *
 * @since 5.2
 */
public final class Saml2ErrorCodes {

	/**
	 * SAML Data does not represent a SAML 2 Response object. A valid XML object was
	 * received, but that object was not a SAML 2 Response object of type
	 * {@code ResponseType} per specification
	 * https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf#page=46
	 */
	public static final String UNKNOWN_RESPONSE_CLASS = "unknown_response_class";

	/**
	 * The serialized AuthNRequest could not be deserialized correctly.
	 *
	 * @since 5.7
	 */
	public static final String MALFORMED_REQUEST_DATA = "malformed_request_data";

	/**
	 * The response data is malformed or incomplete. An invalid XML object was received,
	 * and XML unmarshalling failed.
	 */
	public static final String MALFORMED_RESPONSE_DATA = "malformed_response_data";

	/**
	 * Request is invalid in a general way.
	 *
	 * @since 5.6
	 */
	public static final String INVALID_REQUEST = "invalid_request";

	/**
	 * Response is invalid in a general way.
	 *
	 * @since 5.5
	 */
	public static final String INVALID_RESPONSE = "invalid_response";

	/**
	 * Response destination does not match the request URL. A SAML 2 response object was
	 * received at a URL that did not match the URL stored in the {code Destination}
	 * attribute in the Response object.
	 * https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf#page=38
	 */
	public static final String INVALID_DESTINATION = "invalid_destination";

	/**
	 * The assertion was not valid. The assertion used for authentication failed
	 * validation. Details around the failure will be present in the error description.
	 */
	public static final String INVALID_ASSERTION = "invalid_assertion";

	/**
	 * The signature of response or assertion was invalid. Either the response or the
	 * assertion was missing a signature or the signature could not be verified using the
	 * system's configured credentials. Most commonly the IDP's X509 certificate.
	 */
	public static final String INVALID_SIGNATURE = "invalid_signature";

	/**
	 * The assertion did not contain a subject element. The subject element, type
	 * SubjectType, contains a {@code NameID} or an {@code EncryptedID} that is used to
	 * assign the authenticated principal an identifier, typically a username.
	 *
	 * https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf#page=18
	 */
	public static final String SUBJECT_NOT_FOUND = "subject_not_found";

	/**
	 * The subject did not contain a user identifier The assertion contained a subject
	 * element, but the subject element did not have a {@code NameID} or
	 * {@code EncryptedID} element
	 *
	 * https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf#page=18
	 */
	public static final String USERNAME_NOT_FOUND = "username_not_found";

	/**
	 * The system failed to decrypt an assertion or a name identifier. This error code
	 * will be thrown if the decryption of either a {@code EncryptedAssertion} or
	 * {@code EncryptedID} fails.
	 * https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf#page=17
	 */
	public static final String DECRYPTION_ERROR = "decryption_error";

	/**
	 * An Issuer element contained a value that didn't
	 * https://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf#page=15
	 */
	public static final String INVALID_ISSUER = "invalid_issuer";

	/**
	 * An error happened during validation. Used when internal, non classified, errors are
	 * caught during the authentication process.
	 */
	public static final String INTERNAL_VALIDATION_ERROR = "internal_validation_error";

	/**
	 * The relying party registration was not found. The registration ID did not
	 * correspond to any relying party registration.
	 */
	public static final String RELYING_PARTY_REGISTRATION_NOT_FOUND = "relying_party_registration_not_found";

	/**
	 * The InResponseTo content of the response does not match the ID of the AuthNRequest.
	 *
	 * @since 5.7
	 */
	public static final String INVALID_IN_RESPONSE_TO = "invalid_in_response_to";

	private Saml2ErrorCodes() {
	}

}
