package com.shaojie.www.mathapp.service;


import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.qualfier.MathQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@MathQualifier(CalculationActionType.DIVIDE)
public class DivideMathService implements MathService{


    private static final Logger LOG = LoggerFactory.getLogger(DivideMathService.class);

    /**
     * calculate the resulte in math object
     *
     * @param math
     * @return
     */
    @Override
    public void calculateResult(MathDto math) {

        LOG.info(math.getFirstNumber() + " divide " + math.getSecondNumber());

        if (math.getSecondNumber().compareTo(new BigDecimal(0)) == 0)
            throw new IllegalArgumentException("division by zero");
        math.setResult(math.getFirstNumber().divide(math.getSecondNumber(), 2, RoundingMode.HALF_EVEN));
    }
}
