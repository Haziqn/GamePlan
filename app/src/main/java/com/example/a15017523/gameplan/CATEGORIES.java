package com.example.a15017523.gameplan;

import java.io.Serializable;

/**
 * Created by 15017523 on 1/8/2017.
 */

public class CATEGORIES  implements Serializable {

    String CAT_ID ;
    String CATEGORIES;

    public CATEGORIES() {}

    public CATEGORIES(String CAT_ID, String CATEGORIES) {
        this.CAT_ID = CAT_ID;
        this.CATEGORIES = CATEGORIES;
    }

    public String getCAT_ID() {
        return CAT_ID;
    }

    public void setCAT_ID(String CAT_ID) {
        this.CAT_ID = CAT_ID;
    }

    public String getCATEGORIES() {
        return CATEGORIES;
    }

    public void setCATEGORIES(String CATEGORIES) {
        this.CATEGORIES = CATEGORIES;
    }
}
