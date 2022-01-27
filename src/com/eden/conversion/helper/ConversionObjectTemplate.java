package com.eden.conversion.helper;


import com.eden.annotation.TransferLabel;
import com.eden.conversion.dto.CustomFields;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 转换模板，子类可以重写该方法
 * @created by eden
 * @since 2019-06-23 14:38:48
 */
public interface ConversionObjectTemplate {


    void sourceToTargetObject(Object source, Object target);

    /**
     * <p>
     * 自定义属性以及值，设置到指定对象属性值上。
     * 目标对象可以对层级，查看注解{@link TransferLabel}
     * </p>
     *
     * @param target
     * @param data
     */
    default void customFieldsToTargetObject(Object target, CustomFields data) {
        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            TransferLabel label = field.getAnnotation(TransferLabel.class);
            field.setAccessible(true);
            if (!label.isObject()) {
                if (label.value().equals(data.getName())) {
                    try {
                        field.set(target, data.getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    customFieldsToTargetObject(field.get(target), data);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    default Map<String, FieldAndClassCompose> getSourceFieldMap(Object source) {
        Map<String, FieldAndClassCompose> sourceFieldMap = new HashMap<>();
        determineFieldMapClazz(source, sourceFieldMap);
        return sourceFieldMap;
    }

    default Map<String, FieldAndClassCompose> getTargetFieldMap(Object target) {
        Map<String, FieldAndClassCompose> targetFieldMap = new HashMap<>();
        determineFieldMapClazz(target, targetFieldMap);
        return targetFieldMap;
    }

    default void determineFieldMapClazz(Object clazzVal, Map<String, FieldAndClassCompose> fieldAndClassComposeMap) {
        Arrays.stream(clazzVal.getClass().getDeclaredFields())
                .filter(x -> Objects.nonNull(x.getAnnotation(TransferLabel.class)))
                .forEach(x -> {
                    TransferLabel label = x.getAnnotation(TransferLabel.class);
                    if (fieldAndClassComposeMap.containsKey(label.value())) {
                        //TODO
                        return;
                    }
                    //处理嵌套对象
                    if (label.isObject()) {
                        try {
                            x.setAccessible(true);
                            Object subTargetClass = x.get(clazzVal);
                            if (Objects.isNull(subTargetClass)) {
                                subTargetClass = x.getType().newInstance();
                                x.set(clazzVal, subTargetClass);
                            }
                            determineFieldMapClazz(subTargetClass, fieldAndClassComposeMap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        fieldAndClassComposeMap.put(label.value(), FieldAndClassCompose.of(x, clazzVal));
                    }
                });
    }


  class FieldAndClassCompose {
        private Field field;
        private Class<?> clazz;
        private Object object;

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public static FieldAndClassCompose of(Field field, Object object) {
            final FieldAndClassCompose fieldAndClassCompose = new FieldAndClassCompose();
            fieldAndClassCompose.setField(field);
            fieldAndClassCompose.setClazz(object.getClass());
            fieldAndClassCompose.setObject(object);
            return fieldAndClassCompose;
        }

        @Override
        public String toString() {
            return "FieldAndClassCompose{" +
                    "field=" + field.getName() +
                    ", clazz=" + clazz +
                    ", object=" + object +
                    '}';
        }
    }
}
