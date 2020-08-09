package com.uriallab.haat.haat.DataModels;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 28/07/2019.
 */

public class GoogleAddressModel {
    public ArrayList<GoogleAddressModelInfo> results;
    public String status;

    public class GoogleAddressModelInfo{
        public String formatted_address;
    }
}