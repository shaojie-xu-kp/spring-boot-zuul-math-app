package com.shaojie.www.mathapp.resource;

import com.shaojie.www.mathapp.exception.BadRequestException;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.service.MathServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculationResource {


    @Autowired
    MathServiceManager mathServiceManager;

    @RequestMapping(value = "/calculations", method = RequestMethod.POST)
    public ResponseEntity<MathDto> createMath(@RequestBody MathDto math) {
        MathDto mathCreated = null;
        try {
            mathCreated = mathServiceManager.createMath(math);
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mathCreated, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/calculations/{mathId}", method = RequestMethod.GET)
    public ResponseEntity<MathDto> retrieveMath(@PathVariable("mathId") int id) {
        MathDto mathCreated = mathServiceManager.getMathBy(id);
        return new ResponseEntity<>(mathCreated, HttpStatus.OK);

    }





}