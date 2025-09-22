package com.message.Controller;

import com.message.Payload.APIResponse;
import com.message.Payload.Dto;
import com.message.Payload.LoginDto;
import com.message.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

    @RestController
    @RequestMapping("/api/v1/message/")
    @CrossOrigin("*")
    public class UsersController {

        @Autowired
        private UsersService usersService;


        @PostMapping("register")

        public ResponseEntity<APIResponse<String>> signUp(@RequestBody Dto dto){

            APIResponse<String> response = usersService.signUp(dto);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));


        }




        @PostMapping("login")

        public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto dto){
            APIResponse<String> response = usersService.login(dto);
            return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
        }


        @GetMapping("admin")
        @PreAuthorize("hasRole('Admin')")
        public String adminDashboard(){
            return " Welcome to Admin Dashboard";
        }


}
