package com.eden.domain;

/**
 * @created by eden
 * @since 2019-06-23 14:37:44
 */

public class CustomFields {

    private String name;

    private Object value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CustomFields{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
