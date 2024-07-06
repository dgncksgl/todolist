package tr.com.hepsiemlak.todolist.shared.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import tr.com.hepsiemlak.todolist.shared.util.ObjectMapperUtil;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestWrapper {

    private final ContentCachingRequestWrapper contentWrapper;

    public RequestWrapper(HttpServletRequest request) {
        this.contentWrapper = new ContentCachingRequestWrapper(request);
    }

    public ContentCachingRequestWrapper getContentWrapper() {
        return contentWrapper;
    }

    public String getServletPath() {
        return this.contentWrapper.getServletPath();
    }

    public String getRequestURL() {
        return this.contentWrapper.getRequestURL().toString();
    }

    public String getRequestHeaders() throws JsonProcessingException {
        Map<String, String> requestHeaders = new HashMap<>();
        Enumeration<String> headerNames = this.contentWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            requestHeaders.put(headerName, this.contentWrapper.getHeader(headerName));
        }
        return ObjectMapperUtil.getMapper().writeValueAsString(requestHeaders);
    }

    public String getRequestParameters() throws JsonProcessingException {
        return ObjectMapperUtil.getMapper().writeValueAsString(this.contentWrapper.getParameterMap());
    }

    public String getRequestBody() {
        return new String(this.contentWrapper.getContentAsByteArray());
    }
}
