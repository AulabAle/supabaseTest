package it.aulab.progettoblog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.ContentSecurityPolicyConfig;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager usersManager() {
        // nella versione 3.1.* di Spring Boot assegnare un ruolo non è necessario
        UserBuilder user = User
                .withUsername("user")
                .password(encoder().encode("12345"))
                .roles("USER");
        // ROLE_USER

        UserBuilder admin = User
                .withUsername("admin")
                .password(encoder().encode("admin"))
                .roles("ADMIN");
        // ROLE_ADMIN

        UserDetails adminUser = admin.build();
        for (GrantedAuthority ga : adminUser.getAuthorities()) {
            System.out.println(ga.getAuthority());
        }
        UserDetails userUser = user.build();
        for (GrantedAuthority ga : userUser.getAuthorities()) {
            System.out.println(ga.getAuthority());
        }
        
        return new InMemoryUserDetailsManager(userUser, adminUser);
    }

    private final static String cspDirectives = "default-src 'self'; script-src 'self' cdn.jsdelivr.net carosello.com; style-src 'self' cdn.jsdelivr.net cdnjs.cloudflare.com; font-src cdnjs.cloudflare.com; img-src *;";

    @Bean
    public SecurityFilterChain configurationSecurityFilterChain(HttpSecurity http) throws Exception {

        // Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> httpRequestCustomizer = new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>(){
        //     @Override
        //     public void customize(
        //         AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        //         authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated();
        //     }
        // };

        // Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer = new Customizer<FormLoginConfigurer<HttpSecurity>>() {
        //     @Override
        //     public void customize(FormLoginConfigurer<HttpSecurity> formLogin) {
        //     formLogin.loginPage("/login")
        //             .loginProcessingUrl("/login")
        //             .defaultSuccessUrl("/authors", false)
        //             .permitAll();
        //     }
        // };

        // Customizer<LogoutConfigurer<HttpSecurity>> logoutCustomizer = new Customizer<LogoutConfigurer<HttpSecurity>>() {
        //     @Override
        //     public void customize(LogoutConfigurer<HttpSecurity> logout) {
        //         logout.logoutUrl("/logout")
        //             .logoutSuccessUrl("/");
        //     }
        // };

        // Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer = new Customizer<CsrfConfigurer<HttpSecurity>>() {
        //     @Override
        //     public void customize(CsrfConfigurer<HttpSecurity> csrf) {
        //         csrf.ignoringRequestMatchers("/api/**");
        //     }
        // };

        // Customizer<HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig> cspCustomizer = new Customizer<HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig>() {
        //     @Override
        //     public void
        //     customize(HeadersConfigurer<HttpSecurity>.ContentSecurityPolicyConfig csp) {
        //         csp.policyDirectives(cspDirectives);
        //     }
        // };

        // Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer = new Customizer<HeadersConfigurer<HttpSecurity>>() {
        //     @Override
        //     public void customize(HeadersConfigurer<HttpSecurity> headers) {
        //         headers.xssProtection(Customizer.withDefaults()).contentSecurityPolicy(cspCustomizer);
        //     }
        // };

        // http.authorizeHttpRequests(httpRequestCustomizer)
        // .formLogin(formLoginCustomizer)
        // .logout(logoutCustomizer)
        // .csrf(csrfCustomizer)
        // .headers(headersCustomizer);


        // Lambda function o simili alle Arrow function di JavaScript
        // http.authorizeHttpRequests(
        //         (authorize) -> authorize.requestMatchers("/api/**").permitAll()
        //                 .anyRequest().authenticated())
        //         .formLogin(
        //                 (formLogin) -> formLogin.loginPage("/login")
        //                         .loginProcessingUrl("/login")
        //                         .defaultSuccessUrl("/authors", false)
        //                         .permitAll())
        //         .logout(
        //                 (logout) -> logout.logoutUrl("/logout")
        //                         .logoutSuccessUrl("/"))
        //         .csrf(
        //                 (csrf) -> csrf.ignoringRequestMatchers("/api/**"))
        //         .headers(
        //                 (headers) -> headers.xssProtection(Customizer.withDefaults())
        //                                     .contentSecurityPolicy(
        //                                         (csp) -> csp.policyDirectives(cspDirectives)
        //                                     ));

        //http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

        //Da decommentare
        http.authorizeHttpRequests(
                (authorize) -> authorize.requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(
                        (formLogin) -> formLogin.loginPage("/login")
                                // .loginProcessingUrl("/login")
                                // .defaultSuccessUrl("/authors", true)
                                .permitAll())
                .logout(
                        (logout) -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/"))
                .csrf(
                        (csrf) -> csrf.ignoringRequestMatchers("/api/**"))
                .headers(
                        (headers) -> headers.xssProtection(Customizer.withDefaults())
                                            .contentSecurityPolicy(
                                                (csp) -> csp.policyDirectives(cspDirectives)
                                            )); 

        // http
        //     // Aggiungi il filtro CORS prima del filtro di autenticazione di base
        //     .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
        //     .authorizeHttpRequests(
        //         (authorize) -> authorize.requestMatchers("/api/**").permitAll()
        //                 .anyRequest().authenticated())
        //         .formLogin(
        //                 (formLogin) -> formLogin.loginPage("/login")
        //                         .loginProcessingUrl("/login")
        //                         .defaultSuccessUrl("/authors", true)
        //                         .permitAll())
        //         .logout(
        //                 (logout) -> logout.logoutUrl("/logout")
        //                         .logoutSuccessUrl("/"))
        //         .csrf(
        //                 (csrf) -> csrf.ignoringRequestMatchers("/api/**"))
        //         .headers(
        //                 (headers) -> headers.xssProtection(Customizer.withDefaults())
        //                                     .contentSecurityPolicy(
        //                                         (csp) -> csp.policyDirectives(cspDirectives)
        //                                     )); 


        //cspDirectives
        //Interfaccia anonima
        // Runnable thread = new Runnable(){
        // @Override
        // public void run() {
        // System.out.println("Un thread");
        // }
        // };
        // thread.run();

        // Old 6.0.* version, deprecato dalla 7.*.*
        //comportamento di base
        //qualsiasi richiesta ritienila autenticata
        //deprecato controllare
        // http.authorizeHttpRequests()
        //     .requestMatchers("/api/**")
        //     .permitAll()
        //     .anyRequest()
        //     .authenticated()
        //     .and().formLogin()
        //         .loginPage("/login")
        //         .loginProcessingUrl("/login")
        //         .defaultSuccessUrl("/authors", true)
        //         .permitAll()
        //     .and().logout()
        //         .logoutUrl("/logout")
        //         .logoutSuccessUrl("/")
        //     .and().csrf().ignoringRequestMatchers("/api/**")
        //     .and().headers().xssProtection().and().contentSecurityPolicy("default-src 'self' ; img-src 'self'; script-src 'self' cdn.jsdelivr.net 'unsafe-inline'; style-src 'self' cdn.jsdelivr.net cdnjs.cloudflare.com ; font-src cdnjs.cloudflare.com");

        //default-src 'self' ; script-src 'self'; style-src 'self' cdn.jsdelivr.net cdnjs.cloudflare.com
        
        // http.authorizeHttpRequests()
        //     .anyRequest().authenticated()
        //     .and().httpBasic();

        // Cross Site Scripting (XSS)
        // default-src 'self';
        // script-src 'self' cdn.jsdelivr.net;
        // style-src 'self' cdn.jsdelivr.net cdnjs.cloudflare.com;
        // font-src cdnjs.cloudflare.com

        return http.build();
    }

    // @Bean
    // public CorsFilter corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.addAllowedOrigin("*"); // Consenti tutte le origini
    //     config.addAllowedMethod("*"); // Consenti tutti i metodi HTTP (GET, POST, PUT, DELETE, etc.)
    //     config.addAllowedHeader("*"); // Consenti tutti gli header
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsFilter(source);
    // }
}
