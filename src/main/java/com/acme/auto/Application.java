package com.acme.auto;

import com.acme.auto.dev.DevConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import static com.acme.auto.Banner.TEXT;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.support.WebStack.WEBMVC;

/**
 * Klasse mit der main-Methode für die Anwendung auf Basis von Spring Boot.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
@SpringBootApplication(proxyBeanMethods = false)
@Import({ApplicationConfig.class, DevConfig.class})
@ImportRuntimeHints(ApplicationConfig.CertificateResourcesRegistrar.class)
@EnableHypermediaSupport(type = HAL, stacks = WEBMVC)
@EnableWebSecurity
@EnableMethodSecurity
@SuppressWarnings({"ImplicitSubclassInspection", "ClassUnconnectedToPackage"})
public final class Application {
    private Application() {
    }

    /**
     * Hauptprogramm, um den Microservice zu starten.
     *
     * @param args Evtl. zusätzliche Argumente für den Start des Microservice
     */
    public static void main(final String... args) {
        final var app = new SpringApplication(Application.class);
        app.setBanner((environment, sourceClass, out) -> out.println(TEXT));
        app.run(args);
    }
}
