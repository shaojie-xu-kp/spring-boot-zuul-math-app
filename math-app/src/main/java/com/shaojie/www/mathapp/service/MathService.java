package com.shaojie.www.mathapp.service;

import com.shaojie.www.mathapp.model.MathDto;

public interface MathService {

    /**
     * calculate the resulte in math object
     * @param math
     * @return
     */
    void calculateResult(MathDto math);

}
