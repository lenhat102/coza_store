package com.cybersoft.cozastore.filter;

import com.cybersoft.cozastore.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

//Tất cả request đều phải chạyn vào filter
@Component
public class JwtFilter extends OncePerRequestFilter {
    //Bước 1: nhận token , truyền lên header
    //B2:Giải mã token,Nếu giải m thành công thì hp lệ
    //B3: tạo chuwngs thực và cho đi vào link người dùng gọi


    @Autowired
    private JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Lấy gi trị của header  có key là authoriation
        try {
            String header = request.getHeader("Authorization");
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
//            System.out.println(token);
                //Giải mã token
                Claims claims = jwtHelper.decodeToken(token);

                if (claims != null) {
                    //Tạo chứng thực cho Security
                    SecurityContext context = SecurityContextHolder.getContext();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());
                    context.setAuthentication(usernamePasswordAuthenticationToken);

                }

            }
        }catch(Exception e){


        }





        filterChain.doFilter(request,response);

    }
}
