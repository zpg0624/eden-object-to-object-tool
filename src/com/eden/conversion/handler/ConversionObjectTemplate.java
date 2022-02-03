package com.eden.conversion.handler;


import com.eden.annotation.TransferLabel;
import com.eden.conversion.dto.CustomFields;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换模板，子类可以重写该方法
 *
 * @created by eden
 * @since 2019-06-23 14:38:48
 */
public interface ConversionObjectTemplate {


    /**
     * 给目标对象属性赋值
     * @param source
     * @param target
     */
    void sourceToTargetObject(Object source, Object target);


    /**
     * 解析传对象的属性字段，并放入map中保存，由子类去实现。
     * @param clazzVal
     * @param fieldAndClassComposeMap
     */
    void determineFieldMapClazz(Object clazzVal, Map<String, FieldAndClassCompose> fieldAndClassComposeMap);

    /**
     * <p>
     * 自定义属性以及值，设置到指定对象属性值上。
     * 目标对象可以对层级，查看注解{@link TransferLabel}
     * </p>
     *
     * @param target
     * @param data
     */
    void customFieldsToTargetObject(Object target, CustomFields data);

    /**
     * 获取原始对象属性以及类信息
     * @param source
     * @return
     */
    default Map<String, FieldAndClassCompose> getSourceFieldMap(Object source) {
        Map<String, FieldAndClassCompose> sourceFieldMap = new HashMap<>();
        determineFieldMapClazz(source, sourceFieldMap);
        return sourceFieldMap;
    }

    /**
     * 获取目标对象属性以及类信息
     * @param target
     * @return
     */
    default Map<String, FieldAndClassCompose> getTargetFieldMap(Object target) {
        Map<String, FieldAndClassCompose> targetFieldMap = new HashMap<>();
        determineFieldMapClazz(target, targetFieldMap);
        return targetFieldMap;
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
