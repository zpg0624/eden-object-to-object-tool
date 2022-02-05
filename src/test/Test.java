import com.eden.conversion.dto.CustomFields;
import com.eden.conversion.handler.ConversionSimpleObjectHandler;
import domain.AddressA;
import domain.PersonA;
import domain.PersonB;
import domain.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @created by eden
 * @since 2019-06-23 14:53:22
 */

public class Test {


    public static void main(String[] args) {
        List<CustomFields> datas = new ArrayList<>();
        CustomFields customFields1 = new CustomFields();
        customFields1.setName("岗位类型");
        customFields1.setValue("技术岗位");

        datas.add(customFields1);

        CustomFields customFields2 = new CustomFields();
        customFields2.setName("年龄");
        customFields2.setValue(25);

        datas.add(customFields2);
        CustomFields customFields3 = new CustomFields();
        customFields3.setName("学校名称");
        customFields3.setValue("清华大学");
        datas.add(customFields3);
        UserInfo targetUserInfo = new UserInfo();

        System.out.println("原始数据：" + datas);
        datas.stream().forEach(data -> new ConversionSimpleObjectHandler().customFieldsToTargetObject(targetUserInfo, data));
        System.out.println("转成后对象值：" + targetUserInfo);

        final PersonA personA = new PersonA();
        personA.setNameA("a-val");
        final AddressA addressA = new AddressA();
        addressA.setLocationA("locationA-value");
        personA.setAddressA(addressA);

        PersonB personB = new PersonB();

        new ConversionSimpleObjectHandler().sourceToTargetObject(personA, personB);

        System.out.println(personB);
    }


}
