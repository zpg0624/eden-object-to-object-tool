package com.eden.conversion.handler;

import com.eden.annotation.TransferLabel;
import com.eden.conversion.dto.CustomFields;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class ConversionSimpleObjectHandler implements ConversionObjectTemplate {

    @Override
    public void sourceToTargetObject(Object source, Object target) {
        final Map<String, FieldAndClassCompose> sourceFieldMap = getSourceFieldMap(source);
        final Map<String, FieldAndClassCompose> targetFieldMap = getTargetFieldMap(target);
        sourceFieldMap.entrySet().stream()
                .forEach(sourceX -> {
                    final FieldAndClassCompose sourceFieldAndClassCompose = sourceX.getValue();
                    final FieldAndClassCompose targetFieldAndClassCompose = targetFieldMap.get(sourceX.getKey());
                    if (Objects.nonNull(targetFieldAndClassCompose)) {
                        try {
                            final Field sourceField = sourceFieldAndClassCompose.getField();
                            sourceField.setAccessible(true);
                            final Object fieldVal = sourceField.get(sourceFieldAndClassCompose.getObject());
                            final Field targetField = targetFieldAndClassCompose.getField();
                            targetField.setAccessible(true);
                            targetField.set(targetFieldAndClassCompose.getObject(), fieldVal);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * <p>
     * 自定义属性以及值，设置到指定对象属性值上。
     * 目标对象可以对层级，查看注解{@link TransferLabel}
     * </p>
     *
     * @param target
     * @param data
     */
    @Override
    public void customFieldsToTargetObject(Object target, CustomFields data) {
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

    @Override
    public void determineFieldMapClazz(Object clazzVal, Map<String, FieldAndClassCompose> fieldAndClassComposeMap) {
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

}
