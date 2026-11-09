package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.ErrorResponse;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") String id) {
        User user = this.userService.findById(Long.valueOf(id));
        return ResponseEntity.ok(this.userMapper.toDto(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> save(@PathVariable("id") String id) {
        this.userService.delete(Long.valueOf(id));
        return ResponseEntity.ok(new MessageResponse("User successfully created"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ErrorResponse numberFormatExceptionHandler() {
        return new ErrorResponse("BAD_REQUEST", "User id should be a number");
    }
}
