package com.ibm.lighthouse.config;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.ibm.lighthouse.ContentAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;


public class AbacMatcher extends AbstractFunction {
    private String[] matchedPolicies;
    private String[] checkedPolicies;
    private AbacMatcher abacMatcher;

    private static final Logger LOG = LoggerFactory.getLogger(AbacMatcher.class);

    public AbacMatcher() {}

    public boolean match(String[] policy, ContentAccess input) throws Exception {
        String obj = policy[0];
        String op = policy[1];
        String[] params = Arrays.copyOfRange(policy, 2, policy.length - 1);

        boolean matchResult = new Matcher(op, params, input).eval();
        return matchResult;
    }

    @Override
    public String getName() {
        return "abacMatcherWrapper";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject obj, AviatorObject input, AviatorObject op, AviatorObject p1, AviatorObject p2, AviatorObject p3) {
        LOG.info("AviatorObject call...");

        String[] policy = {FunctionUtils.getStringValue(obj, env), FunctionUtils.getStringValue(op, env), FunctionUtils.getStringValue(p1, env), FunctionUtils.getStringValue(p2, env), FunctionUtils.getStringValue(p3, env)};
        Object something = FunctionUtils.getJavaObject(input, env);
        boolean result = false;
        try {
            result = something.toString() == "_" ? false : this.match(policy, (ContentAccess) something);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result ? AviatorBoolean.TRUE : AviatorBoolean.FALSE;
    }
}

