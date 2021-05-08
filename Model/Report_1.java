package Model;

import java.time.Month;

public class Report_1 {
    private Month month;
    private String type;
    private int count;

    public Report_1(Month month, String type, int count){
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public Month getMonth() {
        return month;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setType(String type) {
        this.type = type;
    }
}

