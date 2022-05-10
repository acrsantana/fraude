package br.com.acrtech.fraude.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter(){
    override fun configure(http: HttpSecurity?) {
        http
            ?.authorizeRequests()?.anyRequest()?.authenticated()
            ?.and()
            ?.formLogin()
                ?.loginPage("/login")
                ?.loginProcessingUrl("/login")
                ?.defaultSuccessUrl("/", true)
                ?.permitAll()
            ?.and()
            ?.httpBasic()
            ?.and()
            ?.logout()
                ?.logoutUrl("/appLogout")
                ?.logoutSuccessUrl("/login")
            ?.and()
            ?.csrf()?.disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
            ?.userDetailsService(userDetailsService)
            ?.passwordEncoder(BCryptPasswordEncoder())
    }
}