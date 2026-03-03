package com.myblog.core.context;

import com.myblog.core.processor.ActionType;
import lombok.Getter;

import java.util.HashMap;


@Getter
public class ActionContext {

    private ActionType actionType;
    HashMap<String, Object> data = new HashMap<>();

    public ActionContext(ActionType actionType) {
        this.actionType = actionType;
    }

    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);
        if (value == null) {
            return null;
        }

        if (!type.isInstance(value)) {
            throw new ClassCastException(
                    "Value for key '" + key + "' is not of type " + type.getName() + ". Actual type: " + value.getClass().getName()
            );
        }
        return type.cast(value);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }
}
