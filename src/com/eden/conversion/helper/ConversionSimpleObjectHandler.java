package com.eden.conversion.helper;

import java.lang.reflect.Field;
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

}
