package com.example.gyeol_web1.web;

import com.example.gyeol_web1.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUsers";

    public static boolean isLoginUsers(HttpSession session) {
        Object sessionedUsers = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUsers == null) {
            return false;
        }
        return true;
    }

    public static User getUsersFromSession(HttpSession session) {
        if (!isLoginUsers(session)) {
            return null;
        }
        return (User)session.getAttribute(USER_SESSION_KEY);
    }

}
