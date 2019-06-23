package com.eden.domain;

import com.eden.Main;
import com.eden.annotation.TransferLabel;
import com.sun.deploy.panel.AdvancedNetworkSettingsDialog;

import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @created by eden
 * @since 2019-06-23 14:53:22
 */

public class ClientObj {

    @Override
    public String toString() {
        return "ClientObj{" +
                "data=" + data +
                '}';
    }

    private List<CustomFields> data = new ArrayList<CustomFields>();

    public List<CustomFields> getData() {
        return data;
    }

    public void setData(List<CustomFields> data) {
        this.data = data;
    }

    public static void main(String[] args) {

        ClientObj clientObj = new ClientObj();
        List<CustomFields> datas = clientObj.getData();


        CustomFields  customFields1 = new CustomFields();

        customFields1.setName("岗位类型");
        customFields1.setValue("type");

         datas.add(customFields1);

        CustomFields  customFields2 = new CustomFields();

        customFields2.setName("年龄");
        customFields2.setValue(25);
        datas.add(customFields2);

        CustomFields  customFields3 = new CustomFields();

        customFields3.setName("学校名称");
        customFields3.setValue("江西");
        datas.add(customFields3);



        TargetUserInfo targetUserInfo = new TargetUserInfo();


        System.out.println(datas);

        datas.stream().forEach(data->resloveTransfer(targetUserInfo, data));



        System.out.println(targetUserInfo);

    }

    private static void resloveTransfer(Object target, CustomFields data) {
        Field[] fields = target.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            TransferLabel lebel = field.getAnnotation(TransferLabel.class);
            field.setAccessible(true);
            if(!lebel.isObject()){
                if(lebel.value().equals(data.getName())){
                    try {

                        field.set(target,data.getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                try {
                    resloveTransfer(field.get(target),data);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
