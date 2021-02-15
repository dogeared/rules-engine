package com.afitnerd.rulesengine.model;

import java.util.List;
import java.util.stream.Collectors;

public class KeyValueFieldsRequest {

    private List<KeyValuePair> fields;

    public List<KeyValuePair> getFields() {
        return fields;
    }

    public void setFields(List<KeyValuePair> fields) {
        this.fields = fields;
    }

    public static class KeyValuePair {
        private String name;
        private String value;

        public KeyValuePair(){}

        public KeyValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getByName(String name) {
        KeyValuePair keyValuePair =  fields.stream()
                .filter(kvp -> kvp.getName().equals(name))
                .findAny()
                .orElse(null);
        return (keyValuePair != null) ? keyValuePair.getValue() : null;
    }

    public KeyValueFieldsRequest subsetByNames(List<String> names) {
        KeyValueFieldsRequest newRequest = new KeyValueFieldsRequest();
        List<KeyValuePair> kvps = this.getFields().stream()
                .filter(kvp -> names.contains(kvp.getName())).collect(Collectors.toList());
        newRequest.setFields(kvps);
        return newRequest;
    }
}