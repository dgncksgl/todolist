package tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port;


import org.springframework.security.core.userdetails.UserDetailsService;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserCreateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserUpdateDto;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserGetDto> getAllUserList();

    UserGetDto getUserById(String userId);

    String addUser(UserCreateDto userCreateDto);

    UserGetDto updateUser(UserUpdateDto userUpdateDto);

    void delete(String userId);
}
