package com.shaojie.www.mathapp.service;

import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.qualfier.MathQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@MathQualifier(CalculationActionType.ADD)
public class AddMathService implements MathService{

    private static final Logger LOG = LoggerFactory.getLogger(AddMathService.class);

    @Override
    public void calculateResult(MathDto math) {
        LOG.info(math.getFirstNumber() + " add " + math.getSecondNumber());
        math.setResult(math.getFirstNumber().add(math.getSecondNumber()));
    }
}
