/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.auto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Objects;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * Banner als String-Konstante für den Start des Servers.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
@SuppressWarnings({
    "AccessOfSystemProperties",
    "CallToSystemGetenv",
    "UtilityClassCanBeEnum",
    "UtilityClass",
    "ClassUnconnectedToPackage"
})
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
final class Banner {
    private static final String FIGLET = """
                                                  tttt                          \s
                                               ttt:::t                          \s
                                               t:::::t                          \s
                                               t:::::t                          \s
          aaaaaaaaaaaaa  uuuuuu    uuuuuuttttttt:::::ttttttt       ooooooooooo  \s
          a::::::::::::a u::::u    u::::ut:::::::::::::::::t     oo:::::::::::oo\s
          aaaaaaaaa:::::au::::u    u::::ut:::::::::::::::::t    o:::::::::::::::o
                   a::::au::::u    u::::utttttt:::::::tttttt    o:::::ooooo:::::o
            aaaaaaa:::::au::::u    u::::u      t:::::t          o::::o     o::::o
          aa::::::::::::au::::u    u::::u      t:::::t          o::::o     o::::o
         a::::aaaa::::::au::::u    u::::u      t:::::t          o::::o     o::::o
        a::::a    a:::::au:::::uuuu:::::u      t:::::t    tttttto::::o     o::::o
        a::::a    a:::::au:::::::::::::::uu    t::::::tttt:::::to:::::ooooo:::::o
        a:::::aaaa::::::a u:::::::::::::::u    tt::::::::::::::to:::::::::::::::o
         a::::::::::aa:::a uu::::::::uu:::u      tt:::::::::::tt oo:::::::::::oo\s
          aaaaaaaaaa  aaaa   uuuuuuuu  uuuu        ttttttttttt     ooooooooooo  \s
        """;
    private static final String JAVA = STR."\{Runtime.version()} - \{System.getProperty("java.vendor")}";
    private static final String OS_VERSION = System.getProperty("os.name");
    private static final InetAddress LOCALHOST = getLocalhost();
    private static final long MEGABYTE = 1024L * 1024L;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final String SERVICE_HOST = System.getenv("KUNDE_SERVICE_HOST");
    private static final String SERVICE_PORT = System.getenv("KUNDE_SERVICE_PORT");
    private static final String KUBERNETES = SERVICE_HOST == null
        ? "N/A"
        : STR."KUNDE_SERVICE_HOST=\{SERVICE_HOST}, KUNDE_SERVICE_PORT=\{SERVICE_PORT}";
    private static final String USERNAME = System.getProperty("user.name");

    /**
     * Banner für den Server-Start.
     */
    static final String TEXT = """

        $figlet
        (C) Juergen Zimmermann, Hochschule Karlsruhe
        Version             2023.10.0
        Spring Boot         $springBoot
        Spring Security     $springSecurity
        Spring Framework    $spring
        Java                $java
        Betriebssystem      $os
        Rechnername         $rechnername
        IP-Adresse          $ip
        Heap: Size          $heapSize MiB
        Heap: Free          $heapFree MiB
        Kubernetes          $kubernetes
        Username            $username
        JVM Locale          $locale
        GraphiQL            /graphiql
        OpenAPI             /swagger-ui.html /v3/api-docs.yaml
        H2 Console          /h2-console (JDBC URL: "jdbc:h2:mem:testdb" mit User "sa" und Passwort "")
        """
        .replace("$figlet", FIGLET)
        .replace("$springBoot", SpringBootVersion.getVersion())
        .replace("$springSecurity", SpringSecurityCoreVersion.getVersion())
        .replace("$spring", Objects.requireNonNull(SpringVersion.getVersion()))
        .replace("$java", JAVA)
        .replace("$os", OS_VERSION)
        .replace("$rechnername", LOCALHOST.getHostName())
        .replace("$ip", LOCALHOST.getHostAddress())
        .replace("$heapSize", String.valueOf(RUNTIME.totalMemory() / MEGABYTE))
        .replace("$heapFree", String.valueOf(RUNTIME.freeMemory() / MEGABYTE))
        .replace("$kubernetes", KUBERNETES)
        .replace("$username", USERNAME)
        .replace("$locale", Locale.getDefault().toString());

    @SuppressWarnings("ImplicitCallToSuper")
    private Banner() {
    }

    private static InetAddress getLocalhost() {
        try {
            return InetAddress.getLocalHost();
        } catch (final UnknownHostException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
