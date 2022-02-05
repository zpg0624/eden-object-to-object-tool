package domain;


import com.eden.annotation.TransferLabel;

public class AddressA {

    @TransferLabel("location")
    private String locationA;

    public String getLocationA() {
        return locationA;
    }

    public void setLocationA(String locationA) {
        this.locationA = locationA;
    }
}
