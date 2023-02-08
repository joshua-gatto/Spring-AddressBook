package org.lab1;

import org.springframework.ui.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class BuddyInfoModel implements Model {

    private HashMap<Object, Object> map = new HashMap<>();
    @Override
    public Model addAttribute(String attributeName, Object attributeValue) {
        if(!map.containsKey(attributeName)) {
            map.put(attributeName, attributeValue);
            return this;
        }
        else{
            return null;
        }
    }

    @Override
    public Model addAttribute(Object attributeValue) {
        map.put(null, attributeValue);
        return this;
    }

    @Override
    public Model addAllAttributes(Collection<?> attributeValues) {
        for(Object o : attributeValues){

        }
        return this;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {
        return false;
    }

    @Override
    public Object getAttribute(String attributeName) {
        return null;
    }

    @Override
    public Map<String, Object> asMap() {
        return null;
    }
}
