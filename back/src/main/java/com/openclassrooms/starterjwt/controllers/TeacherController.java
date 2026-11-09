package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.ErrorResponse;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherMapper teacherMapper;
    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> findById(@PathVariable("id") String id) {
        Teacher teacher = this.teacherService.findById(Long.valueOf(id));
        return ResponseEntity.ok(this.teacherMapper.toDto(teacher));
    }

    @GetMapping()
    public ResponseEntity<List<TeacherDto>> findAll() {
        List<Teacher> teachers = this.teacherService.findAll();
        return ResponseEntity.ok(this.teacherMapper.toDto(teachers));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ErrorResponse NumberFormatException() {
        return new ErrorResponse("BAD_REQUEST", "Teacher id should be a number");
    }
}
