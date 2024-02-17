package com.app.api.swaggertest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerParamTestController {
    //값이 하나만 있을 경우
    //@Parameter(name = "query", description = "query param", in = ParameterIn.QUERY, required = true)
    @Parameters({
            @Parameter(name = "query", description = "query param", in = ParameterIn.QUERY, required = false),
            @Parameter(name = "variable", description = "path variable", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/{variable}")
    public String swaggerTest(String query, @PathVariable String variable) {
        return "swagger test";
    }
}
