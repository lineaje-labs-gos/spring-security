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

package org.springframework.security.saml2.provider.service.authentication.logout;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.security.saml2.core.Saml2ParameterNames;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.Saml2MessageBinding;
import org.springframework.security.saml2.provider.service.web.authentication.logout.Saml2LogoutRequestResolver;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

/**
 * A class that represents a signed and serialized SAML 2.0 Logout Request
 *
 * @author Josh Cummings
 * @since 5.6
 */
public final class Saml2LogoutRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = -3588981995674761337L;

	private static final Function<Map<String, String>, String> DEFAULT_ENCODER = (params) -> {
		if (params.isEmpty()) {
			return null;
		}
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		for (Map.Entry<String, String> component : params.entrySet()) {
			builder.queryParam(component.getKey(), UriUtils.encode(component.getValue(), StandardCharsets.ISO_8859_1));
		}
		return builder.build(true).toString().substring(1);
	};

	private final String location;

	private final Saml2MessageBinding binding;

	private final Map<String, String> parameters;

	private final String id;

	private final String relyingPartyRegistrationId;

	private transient Function<Map<String, String>, String> encoder;

	private Saml2LogoutRequest(String location, Saml2MessageBinding binding, Map<String, String> parameters, String id,
			String relyingPartyRegistrationId) {
		this(location, binding, parameters, id, relyingPartyRegistrationId, DEFAULT_ENCODER);
	}

	private Saml2LogoutRequest(String location, Saml2MessageBinding binding, Map<String, String> parameters, String id,
			String relyingPartyRegistrationId, Function<Map<String, String>, String> encoder) {
		this.location = location;
		this.binding = binding;
		this.parameters = Collections.unmodifiableMap(new LinkedHashMap<>(parameters));
		this.id = id;
		this.relyingPartyRegistrationId = relyingPartyRegistrationId;
		this.encoder = encoder;
	}

	/**
	 * The unique identifier for this Logout Request
	 * @return the Logout Request identifier
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Get the location of the asserting party's <a href=
	 * "https://docs.oasis-open.org/security/saml/v2.0/saml-metadata-2.0-os.pdf#page=7">SingleLogoutService</a>
	 * @return the SingleLogoutService location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Get the binding for the asserting party's <a href=
	 * "https://docs.oasis-open.org/security/saml/v2.0/saml-metadata-2.0-os.pdf#page=7">SingleLogoutService</a>
	 * @return the SingleLogoutService binding
	 */
	public Saml2MessageBinding getBinding() {
		return this.binding;
	}

	/**
	 * Get the signed and serialized &lt;saml2:LogoutRequest&gt; payload
	 * @return the signed and serialized &lt;saml2:LogoutRequest&gt; payload
	 */
	public String getSamlRequest() {
		return this.parameters.get(Saml2ParameterNames.SAML_REQUEST);
	}

	/**
	 * The relay state associated with this Logout Request
	 * @return the relay state
	 */
	public String getRelayState() {
		return this.parameters.get(Saml2ParameterNames.RELAY_STATE);
	}

	/**
	 * Get the {@code name} parameters, a short-hand for <code>
	 * getParameters().get(name)
	 * </code>
	 *
	 * Useful when specifying additional query parameters for the Logout Request
	 * @param name the parameter's name
	 * @return the parameter's value
	 */
	public String getParameter(String name) {
		return this.parameters.get(name);
	}

	/**
	 * Get all parameters
	 *
	 * Useful when specifying additional query parameters for the Logout Request
	 * @return the Logout Request query parameters
	 */
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	/**
	 * Get an encoded query string of all parameters. Resulting query does not contain a
	 * leading question mark.
	 * @return an encoded string of all parameters
	 * @since 5.8
	 */
	public String getParametersQuery() {
		return this.encoder.apply(this.parameters);
	}

	/**
	 * The identifier for the {@link RelyingPartyRegistration} associated with this Logout
	 * Request
	 * @return the {@link RelyingPartyRegistration} id
	 */
	public String getRelyingPartyRegistrationId() {
		return this.relyingPartyRegistrationId;
	}

	/**
	 * Create a {@link Builder} instance from this {@link RelyingPartyRegistration}
	 *
	 * Specifically, this will pull the <a href=
	 * "https://docs.oasis-open.org/security/saml/v2.0/saml-metadata-2.0-os.pdf#page=7">SingleLogoutService</a>
	 * location and binding from the {@link RelyingPartyRegistration}
	 * @param registration the {@link RelyingPartyRegistration} to use
	 * @return the {@link Builder} for further configurations
	 */
	public static Builder withRelyingPartyRegistration(RelyingPartyRegistration registration) {
		return new Builder(registration);
	}

	public static final class Builder {

		private final RelyingPartyRegistration registration;

		private String location;

		private Saml2MessageBinding binding;

		private Map<String, String> parameters = new LinkedHashMap<>();

		private Function<Map<String, String>, String> encoder = DEFAULT_ENCODER;

		private String id;

		private Builder(RelyingPartyRegistration registration) {
			this.registration = registration;
			this.location = registration.getAssertingPartyMetadata().getSingleLogoutServiceLocation();
			this.binding = registration.getAssertingPartyMetadata().getSingleLogoutServiceBinding();
		}

		/**
		 * Use this signed and serialized and Base64-encoded &lt;saml2:LogoutRequest&gt;
		 *
		 * Note that if using the Redirect binding, the value should be
		 * {@link java.util.zip.DeflaterOutputStream deflated} and then Base64-encoded.
		 *
		 * It should not be URL-encoded as this will be done when the request is sent
		 * @param samlRequest the &lt;saml2:LogoutRequest&gt; to use
		 * @return the {@link Builder} for further configurations
		 * @see Saml2LogoutRequestResolver
		 */
		public Builder samlRequest(String samlRequest) {
			this.parameters.put(Saml2ParameterNames.SAML_REQUEST, samlRequest);
			return this;
		}

		/**
		 * Use this SAML 2.0 Message Binding
		 *
		 * By default, the asserting party's configured binding is used
		 * @param binding the SAML 2.0 Message Binding to use
		 * @return the {@link Builder} for further configurations
		 */
		public Builder binding(Saml2MessageBinding binding) {
			this.binding = binding;
			return this;
		}

		/**
		 * Use this location for the SAML 2.0 logout endpoint
		 *
		 * By default, the asserting party's endpoint is used
		 * @param location the SAML 2.0 location to use
		 * @return the {@link Builder} for further configurations
		 */
		public Builder location(String location) {
			this.location = location;
			return this;
		}

		/**
		 * Use this value for the relay state when sending the Logout Request to the
		 * asserting party
		 *
		 * It should not be URL-encoded as this will be done when the request is sent
		 * @param relayState the relay state
		 * @return the {@link Builder} for further configurations
		 */
		public Builder relayState(String relayState) {
			this.parameters.put(Saml2ParameterNames.RELAY_STATE, relayState);
			return this;
		}

		/**
		 * This is the unique id used in the {@link #samlRequest}
		 * @param id the Logout Request id
		 * @return the {@link Builder} for further configurations
		 */
		public Builder id(String id) {
			this.id = id;
			return this;
		}

		/**
		 * Use this {@link Consumer} to modify the set of query parameters
		 *
		 * No parameter should be URL-encoded as this will be done when the request is
		 * sent
		 * @param parametersConsumer the {@link Consumer}
		 * @return the {@link Builder} for further configurations
		 */
		public Builder parameters(Consumer<Map<String, String>> parametersConsumer) {
			parametersConsumer.accept(this.parameters);
			return this;
		}

		/**
		 * Use this strategy for converting parameters into an encoded query string. The
		 * resulting query does not contain a leading question mark.
		 *
		 * In the event that you already have an encoded version that you want to use, you
		 * can call this by doing {@code parameterEncoder((params) -> encodedValue)}.
		 * @param encoder the strategy to use
		 * @return the {@link Builder} for further configurations
		 * @since 5.8
		 */
		public Builder parametersQuery(Function<Map<String, String>, String> encoder) {
			this.encoder = encoder;
			return this;
		}

		/**
		 * Build the {@link Saml2LogoutRequest}
		 * @return a constructed {@link Saml2LogoutRequest}
		 */
		public Saml2LogoutRequest build() {
			return new Saml2LogoutRequest(this.location, this.binding, this.parameters, this.id,
					this.registration.getRegistrationId(), this.encoder);
		}

	}

}
