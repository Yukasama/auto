/*
 * Copyright (C) 2023 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.acme.auto;

import com.acme.auto.repository.AutohausRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.client.RestClientSsl;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Beans für die REST-Schnittstelle zu "autohaus" (AutohausRepository) und für die GraphQL-Schnittstelle zu "autohaus"
 * (HttpGraphQlClient).
 */
@SuppressWarnings("java:S1075")
interface AutohausClientConfig {
    int AUTOHAUS_DEFAULT_PORT = 8081;
    Logger LOGGER = LoggerFactory.getLogger(AutohausClientConfig.class);

    @Bean
    @SuppressWarnings("CallToSystemGetenv")
    default UriComponentsBuilder uriComponentsBuilder() {
        final var autohausSchemaEnv = System.getenv("AUTOHAUS_SERVICE_SCHEMA");
        final var autohausHostEnv = System.getenv("AUTOHAUS_SERVICE_HOST");
        final var autohausPortEnv = System.getenv("AUTOHAUS_SERVICE_PORT");

        final var schema = autohausSchemaEnv == null ? "https" : "http";
        final var host = autohausHostEnv == null ? "localhost" : autohausHostEnv;
        final int port = autohausPortEnv == null ? AUTOHAUS_DEFAULT_PORT : Integer.parseInt(autohausPortEnv);

        LOGGER.debug("autohaus: host={}, port={}", host, port);
        return UriComponentsBuilder.newInstance()
            .scheme(schema)
            .host(host)
            .port(port);
    }

    @Bean
    default AutohausRepository autohausRepository(
        final UriComponentsBuilder uriComponentsBuilder,
        final RestClient.Builder restClientBuilder,
        final RestClientSsl ssl
    ) {
        final var baseUrl = uriComponentsBuilder.build().toUriString();
        LOGGER.info("REST-Client: baseUrl={}", baseUrl);

        final var restClient = restClientBuilder
            .baseUrl(baseUrl)
            // siehe Property "spring.ssl.bundle.jks.microservice" in src\main\resources\application-mac.yml
            // https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.ssl
            // org.springframework.boot.autoconfigure.web.client.AutoConfiguredRestClientSsl
            .apply(ssl.fromBundle("microservice"))
            .build();
        final var clientAdapter = RestClientAdapter.create(restClient);
        final var proxyFactory = HttpServiceProxyFactory.builderFor(clientAdapter).build();
        return proxyFactory.createClient(AutohausRepository.class);
    }
}
