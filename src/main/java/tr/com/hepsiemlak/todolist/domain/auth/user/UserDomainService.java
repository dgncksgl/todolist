package tr.com.hepsiemlak.todolist.domain.auth.user;


import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserCreateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserUpdateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserRepository;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserService;
import tr.com.hepsiemlak.todolist.shared.exception.type.AlreadyExistException;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;

import java.util.List;

@Service
class UserDomainService implements UserService {

    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserGetDto> getAllUserList() {
        return userRepository.findAll().stream()
                .map(UserGetDto::convertToUserGetDtoFromUser)
                .toList();
    }

    @Override
    public UserGetDto getUserById(String userId) {
        return userRepository.findById(userId)
                .map(UserGetDto::convertToUserGetDtoFromUser)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));
    }

    @Override
    public UserGetDto getUserByName(String username) {
        return userRepository.findByUsername(username)
                .map(UserGetDto::convertToUserGetDtoFromUser)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));
    }

    @Override
    public void addAdminUser(UserCreateDto userCreateDto) {
        if (userRepository.findByUsername(userCreateDto.username()).isEmpty()) {
            userRepository.save(UserCreateDto.convertToUserFromUserCreateDto(userCreateDto));
        }
    }

    @Override
    public String addUser(UserCreateDto userCreateDto) {
        if (userRepository.findByUsername(userCreateDto.username()).isPresent()) {
            throw new AlreadyExistException(User.class.getSimpleName());
        }
        return userRepository.save(UserCreateDto.convertToUserFromUserCreateDto(userCreateDto)).getId();
    }

    @Override
    public UserGetDto updateUser(UserUpdateDto userUpdateDto) {

        if (userRepository.findById(userUpdateDto.id()).isEmpty()) {
            throw new NotFoundException(User.class.getSimpleName());
        }

        if (userRepository.findByUsernameAndExceptId(userUpdateDto.id(), userUpdateDto.username()).isPresent()) {
            throw new AlreadyExistException(User.class.getSimpleName());
        }

        return UserGetDto.convertToUserGetDtoFromUser(
                userRepository.save(UserUpdateDto.convertToUserFromUserUpdateDto(userUpdateDto))
        );
    }


    @Override
    public void delete(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserAuthModel::new)
                .orElseThrow(() ->
                        new InternalAuthenticationServiceException("User is not found")
                );
    }

}
