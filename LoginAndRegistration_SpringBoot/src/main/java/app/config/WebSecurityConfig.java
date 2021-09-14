package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
                    .logoutSuccessUrl("/");
        http.headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}