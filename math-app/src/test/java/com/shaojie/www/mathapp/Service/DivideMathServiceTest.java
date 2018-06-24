package com.shaojie.www.mathapp.Service;


import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.qualfier.MathQualifier;
import com.shaojie.www.mathapp.service.MathService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DivideMathServiceTest {

    @Autowired
    @MathQualifier(CalculationActionType.DIVIDE)
    private MathService divideService;

    @Test
    public void test_divide_2_by_1_equals_2() {
        MathDto mathDto = new MathDto();
        mathDto.setFirstNumber(new BigDecimal(2));
        mathDto.setSecondNumber(new BigDecimal(1));
        divideService.calculateResult(mathDto);
        assertThat(mathDto.getResult().compareTo(new BigDecimal(2)), equalTo(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_divide_by_0_IllegalArgumentException() {
        MathDto mathDto = new MathDto();
        mathDto.setFirstNumber(new BigDecimal(2));
        mathDto.setSecondNumber(new BigDecimal(0));
        divideService.calculateResult(mathDto);
    }

}
