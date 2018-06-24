package com.shaojie.www.mathapp.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaojie.www.mathapp.CalculationActionType;
import com.shaojie.www.mathapp.model.MathDto;
import com.shaojie.www.mathapp.service.MathServiceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CalculationResource.class, secure = false)
public class CalculationResourceTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MathServiceManager mathServiceManager;



    @Test
    public void testCreateMath() throws Exception {

        MathDto mathDto = new MathDto();
        mathDto.setAction(CalculationActionType.ADD);
        mathDto.setFirstNumber(new BigDecimal(12));
        mathDto.setSecondNumber(new BigDecimal(24));
        mathDto.setResult(new BigDecimal(36));
        mathDto.setId(1);

        String inputJsonString = this.mapToJsonString(mathDto);

        Mockito.when(mathServiceManager.createMath(Mockito.any(MathDto.class))).thenReturn(mathDto);

        String postURI = "/calculations";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                        .post(postURI)
                                        .accept(MediaType.APPLICATION_JSON).content(inputJsonString)
                                        .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        String outputInJsonString = response.getContentAsString();

        assertThat(inputJsonString, equalTo(outputInJsonString));
        assertThat(HttpStatus.CREATED.value(), equalTo(response.getStatus()));

    }

    @Test
    public void testRetrieveMath() throws Exception {

        MathDto mathDto = new MathDto();
        mathDto.setAction(CalculationActionType.ADD);
        mathDto.setFirstNumber(new BigDecimal(12));
        mathDto.setSecondNumber(new BigDecimal(24));
        mathDto.setResult(new BigDecimal(36));
        mathDto.setId(1);

        String inputJsonString = this.mapToJsonString(mathDto);

        Mockito.when(mathServiceManager.getMathBy(Mockito.anyInt())).thenReturn(mathDto);

        String getURI = "/calculations/1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(getURI)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        String outputInJsonString = response.getContentAsString();

        assertThat(inputJsonString, equalTo(outputInJsonString));
        assertThat(HttpStatus.OK.value(), equalTo(response.getStatus()));

    }



    private String mapToJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


}
