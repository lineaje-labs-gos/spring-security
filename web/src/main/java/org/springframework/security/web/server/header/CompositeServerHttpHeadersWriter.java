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

package org.springframework.security.web.server.header;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.server.ServerWebExchange;

/**
 * Combines multiple {@link ServerHttpHeadersWriter} instances into a single instance.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class CompositeServerHttpHeadersWriter implements ServerHttpHeadersWriter {

	private final List<ServerHttpHeadersWriter> writers;

	public CompositeServerHttpHeadersWriter(ServerHttpHeadersWriter... writers) {
		this(Arrays.asList(writers));
	}

	public CompositeServerHttpHeadersWriter(List<ServerHttpHeadersWriter> writers) {
		this.writers = writers;
	}

	@Override
	public Mono<Void> writeHttpHeaders(ServerWebExchange exchange) {
		return Flux.fromIterable(this.writers).concatMap((w) -> w.writeHttpHeaders(exchange)).then();
	}

}
