package com.android_proj1;

import java.util.HashMap;
import java.util.Map;

public class UserInput {

    private Map<String, Object> map;

    public UserInput(String string, Object object) {
        this.map = new HashMap<String, Object>();
        this.map.put(string, object);
    }

    public Map<String, Object> getMap() {
        return this.map;
    }
}
