package com.shaojie.www.mathapp;

import com.shaojie.www.mathapp.service.AddMathService;
import com.shaojie.www.mathapp.service.MathService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MathServiceFactoryTest {

    @Autowired
    MathServiceFactory mathServiceFactory;

    @Test
    public void test_select_add_math_service () {
        MathService addMathService = mathServiceFactory.select(CalculationActionType.ADD);
        assertThat(addMathService, instanceOf(AddMathService.class));
    }

}
