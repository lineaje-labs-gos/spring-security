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

package org.springframework.security.config.annotation.web.headers

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer

/**
 * A Kotlin DSL to configure the [HttpSecurity] cache control headers using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 */
@HeadersSecurityMarker
class CacheControlDsl {
    private var disabled = false

    /**
     * Disable cache control headers.
     */
    fun disable() {
        disabled = true
    }

    internal fun get(): (HeadersConfigurer<HttpSecurity>.CacheControlConfig) -> Unit {
        return { cacheControl ->
            if (disabled) {
                cacheControl.disable()
            }
        }
    }
}
