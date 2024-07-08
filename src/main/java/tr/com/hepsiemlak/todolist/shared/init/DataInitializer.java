package tr.com.hepsiemlak.todolist.shared.init;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserCreateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserService;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    public static final String ADMIN = "admin";

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        UserCreateDto userCreateDto = new UserCreateDto(
                ADMIN,
                ADMIN,
                true,
                ADMIN,
                ADMIN,
                null,
                null
        );

        userService.addAdminUser(userCreateDto);
    }
}
