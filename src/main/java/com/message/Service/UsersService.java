package com.message.Service;

import com.message.Entity.Users;
import com.message.Payload.APIResponse;
import com.message.Payload.Dto;
import com.message.Payload.LoginDto;
import com.message.Repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;

    //signup
    public APIResponse<String> signUp(Dto dto) {
        APIResponse response = new APIResponse();

        Optional<Users> checkUserName = usersRepository.findByUsername(dto.getUsername());
        if(checkUserName.isPresent()){
            response.setMessage("Signup Failed");
            response.setData("Username is already Available");
            response.setStatus(409);
            response.setField("username");
            return response;
        }
        Optional<Users> checkEmail = usersRepository.findByEmail(dto.getEmail());
        if(checkEmail.isPresent()){
            response.setMessage("Signup Failed");
            response.setData("Email is already Available");
            response.setStatus(410);
            response.setField("email");
            return response;
        }
        Optional<Users> checkMobile = usersRepository.findByMobile(dto.getMobile());
        if(checkMobile.isPresent()){
            response.setMessage("Signup Failed");
            response.setData("Mobile is already Registered");
            response.setStatus(411);
            response.setField("mobile");
            return response;
        }

    String hashpassword =passwordEncoder.encode(dto.getPassword());
        Users users = new Users();
        BeanUtils.copyProperties(dto,users);
//        Users signUp_Data = mapper.map(dto, Users.class);
        users.setPassword(hashpassword);
        users.setRole("ROLE_ADMIN");
        Users saved_data = usersRepository.save(users);
        Dto dtodata = mapper.map(saved_data, Dto.class);
        response.setMessage("signUp done");
        response.setData("User is Registered");
        response.setStatus(201);
        return response;

    }

    //login
    public APIResponse<String> login(LoginDto dto) {
        APIResponse<String> response = new APIResponse<>();

        Optional<Users> findData = usersRepository.findByEmail(dto.getEmail());
        if (findData.isEmpty()) {
            response.setMessage("Login failed");
            response.setData("invalid email");
            response.setStatus(401);
            return response;
        }
        Users dBData = findData.get();
//        if (dto.getEmail().equals(dBData.getEmail()) && dto.getPassword().trim().equals(dBData.getPassword().trim())) {
        if (!passwordEncoder.matches(dto.getPassword(), dBData.getPassword())) {
            response.setMessage("Login failed");
            response.setData("invalid password");
            response.setStatus(401);
            return response;
        }

        //generate JWT token after successful login
        String role = "ROLE_ADMIN";
        String token =jwtService.generateToken(dBData.getEmail(),role);

        response.setMessage("Successfully Login");
        response.setData(token); //return token
        response.setStatus(200);
        return response;
    }

}