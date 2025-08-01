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

package org.springframework.security.core.context;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class ReactiveSecurityContextHolderTests {

	@Test
	public void getContextWhenEmpty() {
		Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
		// @formatter:off
		StepVerifier.create(context)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void setContextAndGetContextThenEmitsContext() {
		SecurityContext expectedContext = new SecurityContextImpl(
				new TestingAuthenticationToken("user", "password", "ROLE_USER"));
		Mono<SecurityContext> context = Mono.deferContextual(Mono::just)
			.flatMap((c) -> ReactiveSecurityContextHolder.getContext())
			.contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(expectedContext)));
		// @formatter:off
		StepVerifier.create(context)
				.expectNext(expectedContext)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void demo() {
		Authentication authentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
		// @formatter:off
		Mono<String> messageByUsername = ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.map(Authentication::getName)
				.flatMap(this::findMessageByUsername)
				.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
		StepVerifier.create(messageByUsername)
				.expectNext("Hi user")
				.verifyComplete();
		// @formatter:on
	}

	private Mono<String> findMessageByUsername(String username) {
		return Mono.just("Hi " + username);
	}

	@Test
	public void setContextAndClearAndGetContextThenEmitsEmpty() {
		SecurityContext expectedContext = new SecurityContextImpl(
				new TestingAuthenticationToken("user", "password", "ROLE_USER"));
		// @formatter:off
		Mono<SecurityContext> context = Mono.deferContextual(Mono::just)
				.flatMap((c) -> ReactiveSecurityContextHolder.getContext())
				.contextWrite(ReactiveSecurityContextHolder.clearContext())
				.contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(expectedContext)));
		StepVerifier.create(context)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void setAuthenticationAndGetContextThenEmitsContext() {
		Authentication expectedAuthentication = new TestingAuthenticationToken("user", "password", "ROLE_USER");
		// @formatter:off
		Mono<Authentication> authentication = Mono.deferContextual(Mono::just)
				.flatMap((c) -> ReactiveSecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.contextWrite(ReactiveSecurityContextHolder.withAuthentication(expectedAuthentication));
		StepVerifier.create(authentication)
				.expectNext(expectedAuthentication)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void getContextWhenThreadFactoryIsPlatformThenPropagated() {
		verifySecurityContextIsPropagated(Executors.defaultThreadFactory());
	}

	@Test
	@DisabledOnJre(JRE.JAVA_17)
	public void getContextWhenThreadFactoryIsVirtualThenPropagated() {
		verifySecurityContextIsPropagated(new VirtualThreadTaskExecutor().getVirtualThreadFactory());
	}

	private static void verifySecurityContextIsPropagated(ThreadFactory threadFactory) {
		Authentication authentication = new TestingAuthenticationToken("user", null);

		// @formatter:off
		Mono<Authentication> publisher = ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.contextWrite((context) -> ReactiveSecurityContextHolder.withAuthentication(authentication))
				.subscribeOn(Schedulers.newSingle(threadFactory));
		// @formatter:on

		StepVerifier.create(publisher).expectNext(authentication).verifyComplete();
	}

	@Test
	public void clearContextWhenThreadFactoryIsPlatformThenCleared() {
		verifySecurityContextIsCleared(Executors.defaultThreadFactory());
	}

	@Test
	@DisabledOnJre(JRE.JAVA_17)
	public void clearContextWhenThreadFactoryIsVirtualThenCleared() {
		verifySecurityContextIsCleared(new VirtualThreadTaskExecutor().getVirtualThreadFactory());
	}

	private static void verifySecurityContextIsCleared(ThreadFactory threadFactory) {
		Authentication authentication = new TestingAuthenticationToken("user", null);

		// @formatter:off
		Mono<Authentication> publisher = ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.contextWrite(ReactiveSecurityContextHolder.clearContext())
				.contextWrite((context) -> ReactiveSecurityContextHolder.withAuthentication(authentication))
				.subscribeOn(Schedulers.newSingle(threadFactory));
		// @formatter:on

		StepVerifier.create(publisher).verifyComplete();
	}

}
