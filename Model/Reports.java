package Model;

import java.time.Month;

public class Reports {
    private Month month;
    private String type;
    private int count;

    public Reports(Month month, String type, int count){
      setMonth(month);
      setType(type);
      setCount(count);
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

