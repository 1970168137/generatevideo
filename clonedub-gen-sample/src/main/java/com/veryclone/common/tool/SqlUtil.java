package com.veryclone.common.tool;

import java.util.Collections;
import java.util.List;

public class SqlUtil {

    public static String buildInSql(List<?> params) {
        if (params == null || params.isEmpty()) {
            return "()"; // 或者根据业务需求抛出异常
        }
        return "(" + String.join(",", Collections.nCopies(params.size(), "?")) + ")";
    }
}