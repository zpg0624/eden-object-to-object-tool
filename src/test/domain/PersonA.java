package domain;

import com.eden.annotation.TransferLabel;

public class PersonA {

    @TransferLabel("name")
    private String nameA ;
    @TransferLabel(isObject = true)
    private AddressA addressA;

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public AddressA getAddressA() {
        return addressA;
    }

    public void setAddressA(AddressA addressA) {
        this.addressA = addressA;
    }
}
