package com.cybersoft.cozastore.config;

import com.cybersoft.cozastore.filter.JwtFilter;
import com.cybersoft.cozastore.provider.CustomAuthenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //khai baos chuanar mã hõa bcrypt và lưu trữ trên ioc
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    @Autowired
    private CustomAuthenProvider authenticationProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                //khai báo s dụng custom user detail service
//                .userDetailsService(customUserDetailService)
//                //khai báo chuẩn mã hóa pass mà custom user detail service đangt sử dụng
//                .passwordEncoder(passwordEncoder())
                .authenticationProvider(authenticationProvider)
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService( PasswordEncoder passwordEncoder){
//        UserDetails admin= User.withUsername("admin")
//                .password(passwordEncoder().encode("123456"))
//                .roles("ADMIN","DELETE").build();
//
//        UserDetails user= User.withUsername("user")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER","SAVE").build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//
//    }



    //Filter dung` de custom rule lien quan den link
    //Cấu hình security

    //Java 8&11 :antMatcher
    //Java >=17: requestantMAtcher
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()// Tắt cấu hiình liên quan đến tấn công csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//khai bao ko su dung session trong project
                .and()
                .authorizeHttpRequests() // Quy định lại các rule liên quan tới chứng thực cho link dc gọi
                    .antMatchers("/signin","/signup").permitAll() //cho phép vào luôn mà ko cần chứng thực

                    .anyRequest().authenticated() // Tất cả link còn lại phải chứng thuwcj

                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();



    }

}
