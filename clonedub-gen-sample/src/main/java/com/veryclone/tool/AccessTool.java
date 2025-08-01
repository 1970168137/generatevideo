package com.veryclone.tool;

import javax.servlet.http.HttpServletRequest;

public class AccessTool {

    /**
     * 获取手机操作系统
     * @param request
     * @return
     */
    public static String getOperatingSystem(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        if (userAgent.contains("android")) {
            return "Android";
        } else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
            return "iOS";
        } else if (userAgent.contains("windows phone")) {
            return "Windows Phone";
        }
        return "Unknown";
    }

    /**
     * 获取ip 地址
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取手机型号
     * @param request
     * @return
     */
    public static String getPhoneModel(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        int startIndex = userAgent.indexOf("(");
        int endIndex = userAgent.indexOf(";");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return userAgent.substring(startIndex + 1, endIndex).trim();
        }
        return "Unknown";
    }
}
