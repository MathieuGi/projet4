package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SessionMapper implements EntityMapper<SessionDto, Session> {


    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;


    @Mapping(source = "teacher_id", target = "teacher", qualifiedByName = "idToTeacher")
    @Mapping(source = "users", target = "users", qualifiedByName = "mapUsers")
    public abstract Session toEntity(SessionDto sessionDto);

    @Named("mapUsers")
    protected List<User> mapUsers(List<Long> userIds) {
        if (userIds == null) {
            return Collections.emptyList();
        }
        return userIds.stream().map(userService::findById).toList();
    }

    @Named("idToTeacher")
    protected Teacher idToTeacher(Long id) {
        return teacherService.findById(id);
    }

    @Mapping(source = "teacher.id", target = "teacher_id")
    @Mapping(source = "users", target = "users", qualifiedByName = "userIds")
    public abstract SessionDto toDto(Session session);

    @Named("userIds")
    protected List<Long> mapUsersIds(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream().map(User::getId).toList();
    }
}
