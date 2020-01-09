package book.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import book.login.service.SecurityMemberService;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Resource(name = "securityMemberService")
	private SecurityMemberService securityMemberService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**", "/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		
		http.authorizeRequests()
		.antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/webjars/**").permitAll();

		http.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/login").anonymous()
            .antMatchers("/user/signup").anonymous()
            .antMatchers("/member").anonymous()
			//.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
			.and() // 로그인 설정
                  .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/user/login/result" , true)
            .and() // 로그아웃 설정
                  .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/user/logout/result")
                    .invalidateHttpSession(true)
            .and()
                   // 403 예외처리 핸들링
                  .exceptionHandling().accessDeniedPage("/user/denied")    
			.and() 
			.csrf()
			     .ignoringAntMatchers("/**")
			.and() 
            .headers()
             .addHeaderWriter(
                 new XFrameOptionsHeaderWriter(
                     new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))    
                 )
            );
		
		http.sessionManagement().invalidSessionUrl("/user/login");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityMemberService).passwordEncoder(passwordEncoder());
	}

}
