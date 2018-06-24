package com.shaojie.www.mathapp.service;


import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.qualfier.MathQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@MathQualifier(CalculationActionType.SUBTRACT)
public class SubMathService implements MathService {

    private static final Logger LOG = LoggerFactory.getLogger(SubMathService.class);

    @Override
    public void calculateResult(MathDto math) {
        LOG.info(math.getFirstNumber() + " subtract " + math.getSecondNumber());
        math.setResult(math.getFirstNumber().subtract(math.getSecondNumber()));
    }

}
