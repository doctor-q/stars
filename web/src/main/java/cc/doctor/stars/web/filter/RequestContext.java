package cc.doctor.stars.web.filter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestContext {

    public static final String REQUEST_USER_ID = "REQUEST_USER_ID";

    private ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public String getString(String name) {
        Object value = threadLocal.get().get(name);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public Integer getInteger(String name) {
        Object value = threadLocal.get().get(name);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    public void setValue(String name, Object value) {
        threadLocal.get().put(name, value);
    }

    public Integer getUserId() {
        return getInteger(REQUEST_USER_ID);
    }
}
