package com.edenrump.ui;

import java.util.List;

public interface Parameterisable {

    /* ****************************************************************************************************************
     *   Parameters
     * ***************************************************************************************************************/

    void storeParameter(String key, String value);

    String getParameterValue(String key);

    List<String> getParameterKeys();

}
