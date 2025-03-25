package com.vortexbird.reto.security.jwt;

import com.vortexbird.reto.service.imp.MyUserDetailsService;
import com.vortexbird.reto.service.imp.UsuarioService;
import com.vortexbird.reto.util.constants.AutConstant;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final MyUserDetailsService userDetailsService;
    private final UsuarioService usuarioService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable()) // se deshabilita para que tome la configuracion de CorsWebFilter
                .csrf(csrf -> csrf.disable()) // se deshabilita debido a que usamos JWT
                // (https://www.baeldung.com/spring-security-csrf#stateless-spring-api)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/api/**"))
                                .permitAll()
                                // .requestMatchers(new
                                // AntPathRequestMatcher("/api/v1/security/authenticate")).permitAll()
                                .requestMatchers(HttpMethod.POST, "/proyecto/**").hasAuthority(AutConstant.COORDINADOR_ROLE)
//                                .requestMatchers(HttpMethod.POST, "/api/solicitud/v1/asignarEstadoDelPermiso").hasAuthority("SCOPE_ROLE_APROBADOR")
//                                .requestMatchers(HttpMethod.POST, "/api/solicitud/v1/porAprobar/**").hasAuthority("SCOPE_ROLE_APROBADOR")
//                                .requestMatchers(HttpMethod.POST, "/api/solicitud/v1/escalar").hasAuthority("SCOPE_ROLE_APROBADOR")
//                                .requestMatchers(HttpMethod.POST, "/api/solicitud/v1/jefeInmediato/**").hasAuthority("SCOPE_ROLE_APROBADOR")
//                                .requestMatchers(HttpMethod.POST, "/api/v1/soporte").hasAuthority("SCOPE_ROLE_USER")
                                .requestMatchers(new AntPathRequestMatcher("/swagger/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll().anyRequest().authenticated()

                ).oauth2ResourceServer(configure -> configure.jwt(Customizer.withDefaults()))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        /**
         * .headers(headers -> headers .httpStrictTransportSecurity(hsts -> hsts
         * .disable() // Disable HSTS ) )
         **/

        ;
        return http.build();
    }

    // =============================================================================

    // =============================================================================
    @Bean
    PasswordEncoder passwordEncoder() {

        PasswordEncoder pE = new BCryptPasswordEncoder(14); // El valor '14' da cerca de 1 Seg. 13 da 600ms
        return pE;
    }

    // =============================================================================

    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins())
                || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            source.registerCorsConfiguration("/api/**", config);
        }
        return new CorsFilter(source);
    }

    // =============================================================================
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) // (AuthenticationConfiguration config)
            throws Exception {
        // return config.getAuthenticationManager();
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        byte[] signingKey = Decoders.BASE64.decode(AutConstant.LLAVE_CIFRADO);

        return NimbusJwtDecoder.withSecretKey(Keys.hmacShaKeyFor(signingKey)).macAlgorithm(MacAlgorithm.HS512).build();
    }
}
