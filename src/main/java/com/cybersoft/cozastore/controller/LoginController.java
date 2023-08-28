package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.exception.CustomException;
import com.cybersoft.cozastore.payload.request.SignupRequest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.imp.UserServiceImp;
import com.cybersoft.cozastore.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin("*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserServiceImp userServiceImp;

    //tra? ra 'status code: 200,300,..
    //message:""
    //data:

    @RequestMapping(value="/signin",method= RequestMethod.POST)
    public ResponseEntity<?> signin(@RequestParam String email,@RequestParam String password){

        UsernamePasswordAuthenticationToken token=
                new UsernamePasswordAuthenticationToken(email,password);

        authenticationManager.authenticate(token);


        //Neu chung thuc thanh cong> chay code tiep theo
        //Neu fail thi se vang loi~ chua chung thuc
        String jwt=jwtHelper.generateToken(email);//token ko dc luu tru password
        BaseResponse response=new BaseResponse();
        response.setStatusCode(200);
        response.setData(jwt);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @RequestMapping(value="/signup",method= RequestMethod.POST)
    public ResponseEntity<?> signup(@Valid SignupRequest request, BindingResult bindingResult){

        List<FieldError> list =bindingResult.getFieldErrors();

        for (FieldError data:list) {
            throw new CustomException(data.getDefaultMessage());
//            System.out.println("Kiem tra: "+data.getField()+" - "+ data.getDefaultMessage());
        }

        boolean isSuccess= userServiceImp.addUser(request);

        BaseResponse response=new BaseResponse();
        response.setStatusCode(200);
        response.setData(isSuccess);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
