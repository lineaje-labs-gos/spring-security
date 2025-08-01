/*
 * Copyright 2004-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain clients copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.kt.docs.features.integrations.rest.configurationwebclient

import okhttp3.mockwebserver.MockWebServer
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.kt.docs.features.integrations.rest.clientregistrationid.UserService
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.client.support.OAuth2RestClientHttpServiceGroupConfigurer
import org.springframework.security.oauth2.client.web.reactive.function.client.support.OAuth2WebClientHttpServiceGroupConfigurer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientHttpServiceGroupConfigurer
import org.springframework.web.service.registry.HttpServiceGroup
import org.springframework.web.service.registry.HttpServiceGroupConfigurer
import org.springframework.web.service.registry.HttpServiceGroupConfigurer.ClientCallback
import org.springframework.web.service.registry.ImportHttpServices

/**
 * Documentation for [OAuth2RestClientHttpServiceGroupConfigurer].
 * @author Rob Winch
 */
@Configuration(proxyBeanMethods = false)
@ImportHttpServices(types = [UserService::class], clientType = HttpServiceGroup.ClientType.WEB_CLIENT)
class ServerWebClientHttpInterfaceIntegrationConfiguration {
    // tag::config[]
    @Bean
    fun securityConfigurer(
        manager: ReactiveOAuth2AuthorizedClientManager?
    ): OAuth2WebClientHttpServiceGroupConfigurer {
        return OAuth2WebClientHttpServiceGroupConfigurer.from(manager)
    }

    // end::config[]
    @Bean
    fun authorizedClientManager(): ReactiveOAuth2AuthorizedClientManager? {
        return Mockito.mock<ReactiveOAuth2AuthorizedClientManager?>(ReactiveOAuth2AuthorizedClientManager::class.java)
    }

    @Bean
    fun groupConfigurer(server: MockWebServer): WebClientHttpServiceGroupConfigurer {
        return WebClientHttpServiceGroupConfigurer { groups: HttpServiceGroupConfigurer.Groups<WebClient.Builder> ->
            val baseUrl = server.url("").toString()
            groups!!
                .forEachClient(ClientCallback { group: HttpServiceGroup, builder: WebClient.Builder ->
                    builder
                        .baseUrl(baseUrl)
                        .defaultHeader("Accept", "application/vnd.github.v3+json")
                })
        }
    }

    @Bean
    fun mockServer(): MockWebServer {
        return MockWebServer()
    }
}
