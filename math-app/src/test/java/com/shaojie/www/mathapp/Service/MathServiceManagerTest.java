package com.shaojie.www.mathapp.Service;

import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.exception.BadRequestException;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.service.MathServiceManager;
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
public class MathServiceManagerTest {

    @Autowired
    private MathServiceManager mathServiceManager;

    /**
     * it is an integration test, before running it, be sure you have kafka configured and up running
     * @throws BadRequestException
     */
    @Test
    public void test_create_math_1_add_2() throws BadRequestException {
        MathDto mathDto = new MathDto();
        mathDto.setFirstNumber(new BigDecimal(1));
        mathDto.setSecondNumber(new BigDecimal(2));
        mathDto.setAction(CalculationActionType.ADD);
        mathServiceManager.createMath(mathDto);
        assertThat(mathDto.getResult().compareTo(new BigDecimal(3)), equalTo(0));
        assertThat(mathDto.getId(), equalTo(0));
    }


}
