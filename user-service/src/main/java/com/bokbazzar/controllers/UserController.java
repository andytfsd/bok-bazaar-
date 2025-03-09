package com.bokbazzar.controllers;

import com.bokbazzar.dto.CreateUserRequestModel;
import com.bokbazzar.dto.CreateUserResponseModel;
import com.bokbazzar.dto.UserDto;
import com.bokbazzar.dto.UserResponseModel;
import com.bokbazzar.service.UsersService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    UsersService usersService;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port") + ", with token = "
                + env.getProperty("token.secret");
    }

    @PostMapping()
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = usersService.createUser(userDto);

        CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(
            value = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    // @PreAuthorize("hasRole('ADMIN') or principal == #userId")
    // @PreAuthorize("principal == #userId")
    // @PostAuthorize("principal == returnObject.body.userId")
    public ResponseEntity<UserResponseModel> getUser(
            @PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization) {

        UserDto userDto = usersService.getUserByUserId(userId, authorization);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
