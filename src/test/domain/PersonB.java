package domain;

import com.eden.annotation.TransferLabel;

public class PersonB {
    @TransferLabel("name")
    private  String _nameB;

    @TransferLabel(isObject = true)
    private AddressB addressB;

    public String get_nameB() {
        return _nameB;
    }

    public void set_nameB(String _nameB) {
        this._nameB = _nameB;
    }

    public AddressB getAddressB() {
        return addressB;
    }

    public void setAddressB(AddressB addressB) {
        this.addressB = addressB;
    }

    @Override
    public String toString() {
        return "PersonB{" +
                "_nameB='" + _nameB + '\'' +
                ", addressB=" + addressB +
                '}';
    }
}
