package com.dev.frontend.services;

import java.util.List;
import java.util.Map;

public class Utils {

    public static Double parseDouble(String value) {
        if (value == null || value.isEmpty())
            return 0D;
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0D;
        }
    }

    public static Boolean parseBoolean(String value) {
        if (value == null || value.isEmpty())
            return false;
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    public static String[][] convertListToArray(List<Object> list){
        if (list.isEmpty()) {
            return new String[][]{};
        }
        String[][] ret = new String[list.size()][((Map<String, Object>)list.get(0)).size()];
        for (int i=0; i<list.size(); i++) {
            Map<String, Object> item = (Map<String, Object>) list.get(i);
            int j = 0;
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                ret[i][j++] = entry.getValue().toString();
            }
        };
        return ret;
    }
}
