package com.major.project.configuration;

import com.major.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired private LoginSuccessHandler loginSuccessHandler;


    @Configuration
    @Order(1)
    public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http .cors().and().csrf().disable();
            // @formatter:off
            http
                    .authorizeRequests()
                    .antMatchers("/misc/**","/tlead/login","/dev/login", "/h2/**").permitAll()
                    .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .antMatchers("/tlead/**")
                    .hasAuthority("TLEAD")
                    .antMatchers("/dev/**")
                    .hasAuthority("DEV")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login_process")
                    .failureUrl("/admin/login?error=true")
                    .successHandler(loginSuccessHandler)
                    .permitAll()
                    .and()
                    .logout().logoutSuccessUrl("/admin/login").permitAll()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");

            http.headers().frameOptions().disable();
            // @formatter:on
        }
    }

    @Configuration
    @Order(2)
    public class TeamLeadSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http .cors().and().csrf().disable();
            // @formatter:off
            http
                    .authorizeRequests()
                    .antMatchers("/misc/**","/admin/login","/dev/login","/h2/**").permitAll()
                    .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .antMatchers("/tlead/**")
                    .hasAuthority("TLEAD")
                    .antMatchers("/dev/**")
                    .hasAuthority("DEV")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/tlead/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login_process")
                    .failureUrl("/tlead/login?error=true")
                    .successHandler(loginSuccessHandler)
                    .permitAll()
                    .and()
                    .logout().logoutSuccessUrl("/tlead/login").permitAll()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");

            http.csrf().disable();
            http.headers().frameOptions().disable();
            // @formatter:on

        }


    }

    @Configuration
    @Order(3)
    public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http .cors().and().csrf().disable();
            // @formatter:off
            http
                    .authorizeRequests()
                    .antMatchers("/misc/**","/admin/login","/tlead/login","/h2/**").permitAll()
                    .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .antMatchers("/tlead/**")
                    .hasAuthority("TLEAD")
                    .antMatchers("/dev/**")
                    .hasAuthority("DEV")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/dev/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login_process")
                    .failureUrl("/dev/login?error=true")
                    .successHandler(loginSuccessHandler)
                    .permitAll()
                    .and()
                    .logout().logoutSuccessUrl("/dev/login").permitAll()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .exceptionHandling().accessDeniedPage("/403");


            http.csrf().disable();
            http.headers().frameOptions().disable();
            // @formatter:on

        }


    }

    @Configuration
    @Order(4)
    public class CommonSecurityConfiguration extends WebSecurityConfigurerAdapter{

        @Bean
        public UserDetailsService userDetailsService() {
            return new EmpService();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());

            return authProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http .cors().and().csrf().disable();
            // @formatter:off
            http
                    .authorizeRequests()
                    .antMatchers("/misc/**","/h2/**").permitAll()
                    .anyRequest().permitAll();

            http.csrf().disable();
            http.headers().frameOptions().disable();
            // @formatter:on

        }

        @Override
        public void configure(WebSecurity web) throws Exception{
            web
                    .ignoring()
                    .antMatchers("/resources/**","/static/**","/misc/**","/css/**","/images/**","/js/**","/signin-signup-resources/**","/vendor/**");
        }
    }


}
