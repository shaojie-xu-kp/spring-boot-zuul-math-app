package com.shaojie.www.mathapp.service;


import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.qualfier.MathQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@MathQualifier(CalculationActionType.MULTIPLY)
public class MultiplyMathService implements MathService{

    private static final Logger LOG = LoggerFactory.getLogger(MultiplyMathService.class);

    @Override
    public void calculateResult(MathDto math) {
        LOG.info(math.getFirstNumber() + " multiply " + math.getSecondNumber());
        math.setResult(math.getFirstNumber().multiply(math.getSecondNumber()));
    }

}
