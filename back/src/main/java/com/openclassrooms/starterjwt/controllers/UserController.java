package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.services.UserService;
import lombok.RequiredArgsConstructor;
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
        User user;
        try {
            Long idValue = this.pathIdToLong(id);
            user = this.userService.findById(idValue);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id should be a number");
        }
        return ResponseEntity.ok().body(this.userMapper.toDto(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> save(@PathVariable("id") String id) {
        try {
            Long idValue = this.pathIdToLong(id);
            this.userService.delete(idValue);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id should be a number");
        }
        return ResponseEntity.ok().body(new MessageResponse("User successfully created"));
    }

    private Long pathIdToLong(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id should be a number");
        }
    }
}
