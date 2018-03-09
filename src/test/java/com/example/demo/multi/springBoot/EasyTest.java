package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.proxy.service.impl.BusynessInterfaceImpl;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author: LiHongxing
 * @date: Create in 2018-03-07 15:39
 * @modefied:
 */
public class EasyTest {

    @Test
    public void test() {
        String greetingExp = "Hello #{#user}, the bigest integer is #{T(Integer).MAX_VALUE}";
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", "Gangyou");

        Expression expression = parser.parseExpression(greetingExp,
                new TemplateParserContext());
        System.out.println(expression.getValue(context, String.class));
    }
}
