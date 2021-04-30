package hu.aharcsa.auth.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource


@Configuration
@EnableWebSecurity
class WebSecurityConfig(disableDefaults: Boolean = false) : WebSecurityConfigurerAdapter(disableDefaults) {

    @Bean
    @Profile(Profiles.JDBC)
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var env: Environment

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .ignoringAntMatchers("/h2-console/**")
            .and()
            .loginBasedOnActiveProfile()
            .authorizeRequests()
            .antMatchers("/h2-console/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .headers()
            .frameOptions()
            .disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        if (!env.activeProfiles.contains(Profiles.JDBC)) {
            super.configure(auth)
            return
        }
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
    }

    fun HttpSecurity.loginBasedOnActiveProfile(): HttpSecurity {
        return when (Login.from(env)) {
            Login.FORM -> this.formLogin {}
            Login.BASIC -> this.httpBasic {}
        }
    }
}