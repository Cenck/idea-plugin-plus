package com;

import com.zhz.idea.plugin.plus.domain.aggregate.PrintTextAgg;
import com.zhz.idea.plugin.plus.domain.vo.MethodPrintVo;
import com.zhz.idea.plugin.plus.domain.vo.VariableVo;
import com.zhz.idea.plugin.plus.service.TestMethodNameResolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:zhaohuzhi@souche.com">zhz</a>
 * @version 1.0.0
 * @date 2019-04-30 09:50
 */
public class PrintAggTest {

    @Test
    public void TestName(){
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("a_01");
        String name = TestMethodNameResolver.getInstance().getTestName("a",set);
        System.out.println(name);
    }

    @Test
    public void testMethodsRender(){
            PrintTextAgg agg = new PrintTextAgg();
            List<MethodPrintVo> list = new ArrayList<>();
            VariableVo var = new VariableVo();
            var.setName("s");
            var.setType("t");
            MethodPrintVo vo = new MethodPrintVo();
            vo.setMethodName("get");
            vo.getParamList().add(var);
            list.add(vo);

            MethodPrintVo vo1 = new MethodPrintVo();
            vo1.setMethodName("get");
            vo1.getParamList().add(var);
            vo1.getParamList().add(var);
            list.add(vo1);

            MethodPrintVo vo2 = new MethodPrintVo();
            vo2.setMethodName("get");
            list.add(vo2);

            agg.setMethods(list);
            agg.renderMethods();

            System.out.println(1);

    }
}
