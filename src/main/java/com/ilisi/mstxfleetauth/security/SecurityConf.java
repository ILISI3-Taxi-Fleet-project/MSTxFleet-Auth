package com.ilisi.mstxfleetauth.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class SecurityConf {


    private final JwtProperties jwtProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// we won't make the auth on server side using jwt
                .csrf(AbstractHttpConfigurer::disable)// when we want to use the stateless auth we should disable the csrf service
                .authorizeHttpRequests(a-> a.requestMatchers("/auth/**").permitAll())
                .authorizeHttpRequests(a-> a.anyRequest().authenticated())
                .oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
                .build();
    }
    /// The InMemoryUserDetailsManager is used for testing and prototyping
//    @Bean(name = "inMemoryUserDetailsManager")
//    @Primary
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        PasswordEncoder bcrypt = this.passwordEncoder();
//        return new InMemoryUserDetailsManager(
//                User.withUsername("ana").password(bcrypt.encode("0123")).authorities("USER").build(),
//                User.withUsername("nta").password(bcrypt.encode("4567")).authorities("USER","ADMIN").build()
//        );
//    }
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManager(UserDetailsService uds)
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(uds);
        return new ProviderManager(daoAuthenticationProvider);
    }
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = "jwtEncoder")
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtProperties.key().getBytes()));
    }

    @Bean(name = "jwtDecoder")
    JwtDecoder jwtDecoder()
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(jwtProperties.key().getBytes(),"RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

}
