package tr.com.hepsiemlak.todolist.shared.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;
import tr.com.hepsiemlak.todolist.shared.util.ObjectMapperUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper {

    private final ContentCachingResponseWrapper contentWrapper;

    public ResponseWrapper(HttpServletResponse response) {
        this.contentWrapper = new ContentCachingResponseWrapper(response);
    }

    public ContentCachingResponseWrapper getContentWrapper() {
        return contentWrapper;
    }

    public int getStatus() {
        return this.contentWrapper.getStatus();
    }

    public String getResponseHeaders() throws JsonProcessingException {
        Map<String, String> responseHeaders = new HashMap<>();
        this.contentWrapper.getHeaderNames().forEach(s -> responseHeaders.put(s, this.contentWrapper.getHeader(s)));
        return ObjectMapperUtil.getMapper().writeValueAsString(responseHeaders);
    }

    public String getResponseBody() throws IOException {
        String responseBody = new String(this.contentWrapper.getContentAsByteArray());
        this.contentWrapper.copyBodyToResponse();
        return responseBody;
    }

}
