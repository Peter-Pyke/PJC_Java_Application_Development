package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * This is the Countries class and contains the attributes of the country model. It also contains the constructor and
 * getters methods.
 * */
public class Countries {
    private int id;
    private String name;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String lastUpdatedBy;

    public Countries( int id, String name){
        this.id = id;
        this.name = name;
    }
    /**
     * This is the getId method and is used to return the Country ID.
     * @return id. The Id of the selected Country.
     * */
    public int getId() {return id;}
    /**
     * This is the getName method and is used to return the Country Name.
     * @return name. The Name of the selected Country.
     * */
    public String getName(){return name;}
}
