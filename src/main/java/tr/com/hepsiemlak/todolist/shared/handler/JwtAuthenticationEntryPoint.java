package tr.com.hepsiemlak.todolist.shared.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tr.com.hepsiemlak.todolist.shared.exception.entity.ExceptionEntity;
import tr.com.hepsiemlak.todolist.shared.logging.LoggingBuilder;
import tr.com.hepsiemlak.todolist.shared.util.ObjectMapperUtil;
import tr.com.hepsiemlak.todolist.shared.wrapper.RequestWrapper;
import tr.com.hepsiemlak.todolist.shared.wrapper.ResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String responseBody = getResponseBody(authException);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(responseBody);

        RequestWrapper requestWrapper = new RequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                    " {} ", LoggingBuilder.getInstance()
                            .setResponseBody(responseBody)
                            .build(requestWrapper, responseWrapper)
            );
        }

    }

    private static String getResponseBody(AuthenticationException authException) throws JsonProcessingException {
        return ObjectMapperUtil.getMapper().writeValueAsString(
                ExceptionEntity.getExceptionEntity()
                        .setId(UUID.randomUUID().toString())
                        .setMessage(authException.getMessage())
                        .setStatus(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }
}
