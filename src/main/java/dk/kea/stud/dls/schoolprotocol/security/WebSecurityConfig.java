package dk.kea.stud.dls.schoolprotocol.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSourceStudent;
    @Autowired
    private DataSource dataSourceTeacher;

    @Autowired
    public void configAuthenticationStudent(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSourceStudent)
                .usersByUsernameQuery("select email, password, enabled from student where email=?")
                .authoritiesByUsernameQuery("select email, role from student where email=?")

        ;
    }

    @Autowired
    public void configAuthenticationTeacher(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSourceTeacher)
                .usersByUsernameQuery("select email, password, enabled from teacher where email=?")
                .authoritiesByUsernameQuery("select email, role from teacher where email=?")

        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }
}
