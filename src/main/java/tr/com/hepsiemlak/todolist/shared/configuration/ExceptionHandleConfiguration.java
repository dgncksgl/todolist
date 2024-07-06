package tr.com.hepsiemlak.todolist.shared.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.hepsiemlak.todolist.shared.exception.handler.GeneralExceptionHandler;

@Configuration
public class ExceptionHandleConfiguration {

    @Bean(name = "general-exception")
    GeneralExceptionHandler generalExceptionHandler() {
        return new GeneralExceptionHandler();
    }

}
