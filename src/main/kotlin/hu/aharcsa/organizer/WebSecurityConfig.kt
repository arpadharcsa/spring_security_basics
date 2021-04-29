package hu.aharcsa.organizer

import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class WebSecurityConfig(disableDefaults: Boolean = false) : WebSecurityConfigurerAdapter(disableDefaults) {
    override fun configure(http: HttpSecurity?) {
        http?.csrf()
            ?.ignoringAntMatchers("/h2-console/**")
            ?.and()
            ?.formLogin{}
            ?.authorizeRequests()
            ?.antMatchers("/h2-console/**")
            ?.permitAll()
            ?.anyRequest()
            ?.authenticated()
            ?.and()
            ?.headers()
            ?.frameOptions()
            ?.disable()
    }
}