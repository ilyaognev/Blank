package app.config;

import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    //Доступ разрешен всем пользователей
                    .antMatchers("/", "/index").permitAll()
                    //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/signup").not().fullyAuthenticated()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/list", "/register").hasRole("ADMIN")
                    //Доступ только для пользователей с ролью User (показана разница между hasRole и hasAuthority)
                    .antMatchers("/news").hasAuthority("ROLE_USER")
                    //Все остальные страницы требуют аутентификации
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling().accessDeniedPage("/403")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    //Перенарпавление на главную страницу после успешного входа
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                .logout()
                //ЭТО ДЛЯ БЕЗОПАСНОГО ЛОГАУТА
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
//                    .invalidateHttpSession(true)
//                    .clearAuthentication(true)
//                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/");
        http.headers().frameOptions().disable();
    }

//-------------------IT'S EXAMPLE OF USERS IN MEMORY WITHOUT DB
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password(bCryptPasswordEncoder().encode("user"))
//                        .roles("USER")
//                        .roles("ADMIN")
//                        .build();
//        UserDetails admin =
//                User.builder()
//                        .username("admin")
//                        .password(bCryptPasswordEncoder().encode("admin"))
//                        .roles("ADMIN")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
