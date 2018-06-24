package com.shaojie.www.mathapp.model;


import com.shaojie.www.mathapp.CalculationActionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class MathDto {

    private int id;
    private BigDecimal firstNumber;
    private BigDecimal secondNumber;
    private BigDecimal result;
    private CalculationActionType action;


}
