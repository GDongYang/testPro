package com.fline.form.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalOnRedis implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean flag = conditionContext.getRegistry().containsBeanDefinition("redisMgmtService");
        System.out.println("ConditionalOnRedis:" + flag);
        return flag;
    }

}