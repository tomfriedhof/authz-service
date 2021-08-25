package com.ibm.lighthouse.config;

import com.ibm.lighthouse.ContentAccess;

import java.lang.reflect.Field;

public class Matcher {
    private String op;
    private String[] params;
    private ContentAccess input;

    public Matcher(String op, String[] params, ContentAccess input) {
        this.op = op;
        this.params = params;
        this.input = input;
    }

    public boolean eval() throws Exception {
        String p1 = this.params[0];
        String p2 = this.params[1];
//        String p3 = this.params[2];
//        String p4 = this.params[3];
        switch (this.op) {
            case "_":
                return false;
            case "=":
                return this.eq(p1, p2);
            default:
                throw new Exception("unsupported operator");
        }

    }

    public boolean eq(String lhs, String rhs) throws NoSuchFieldException, IllegalAccessException {
        Field lf = this.input.getClass().getDeclaredField(lhs);
        Field rf = this.input.getClass().getDeclaredField(rhs);
        String l = (String) lf.get(this.input);
        String r = (String) rf.get(this.input);
        return l == r;
    }
}
