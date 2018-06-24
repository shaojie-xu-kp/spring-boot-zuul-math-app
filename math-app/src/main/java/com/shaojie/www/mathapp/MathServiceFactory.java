package com.shaojie.www.mathapp;

import com.shaojie.www.mathapp.qualfier.MathQualifier;
import com.shaojie.www.mathapp.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MathServiceFactory {

    @Autowired
    @MathQualifier(CalculationActionType.ADD)
    private MathService addService;

    @Autowired
    @MathQualifier(CalculationActionType.SUBTRACT)
    private MathService subService;


    @Autowired
    @MathQualifier(CalculationActionType.DIVIDE)
    private MathService divideService;


    @Autowired
    @MathQualifier(CalculationActionType.MULTIPLY)
    private MathService multiplyService;


    public MathService select(CalculationActionType type)  {
        switch(type) {
            case ADD : return addService;
            case SUBTRACT: return subService;
            case MULTIPLY : return multiplyService;
            case DIVIDE : return divideService;
            default: return addService;
        }
    }


}
