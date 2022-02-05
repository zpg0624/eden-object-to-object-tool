package domain;

import com.eden.annotation.TransferLabel;

/**
 * @created by eden
 * @since 2019-06-23 14:50:10
 */

public class EduInfo {

    @TransferLabel("学校名称")
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return "EduInfo{" +
                "schoolName='" + schoolName + '\'' +
                '}';
    }
}
