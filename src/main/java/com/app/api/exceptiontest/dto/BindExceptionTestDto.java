package com.app.api.exceptiontest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BindExceptionTestDto {
    //빈값 확인
    @NotBlank(message = "해당 값은 필수 입력값입니다.")
    private String value1;
    @Max(value = 10,message = "최대 입력 값은 10입니다.")
    private Integer value2;
}
