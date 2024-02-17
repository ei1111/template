package com.app.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        //에러 발생시 log 출력
        log.error("{} 요청 실패, status : {}, reason: {} ", methodKey, response.status(), response.reason());
        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());
        Long retryafter = null;
        //500번대 서버 에러면 다시시도
        if (httpStatus.is5xxServerError()) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    retryafter,
                    response.request()
            );
        }
        return errorDecoder.decode(methodKey, response);
    }
}
