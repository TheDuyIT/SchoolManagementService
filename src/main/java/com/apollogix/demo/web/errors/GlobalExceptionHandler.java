package com.apollogix.demo.web.errors;

import com.apollogix.demo.constant.Constants;
import com.apollogix.demo.constant.FieldNameConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.util.Optional;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler implements ProblemHandling, SecurityAdviceTrait {

    @ExceptionHandler({RestClientResponseException.class})
    public ResponseEntity<Problem> handleRestClientResponseException(
            final RestClientResponseException e,
            final NativeWebRequest request) {
        var problem = Problem.builder()
                .withStatus(Status.valueOf(e.getStatusCode().value()))
                .withTitle(e.getStatusText())
                .with(FieldNameConstants.message, e.getMessage())
                .with(FieldNameConstants.path, extractContextPath(request))
                .build();
        return create(e, problem, request);
    }

    @ExceptionHandler({ConcurrencyFailureException.class})
    public ResponseEntity<Problem> handleConcurrencyFailure(
            final ConcurrencyFailureException e,
            final NativeWebRequest request) {
        var problem = Problem.builder()
                .withStatus(Status.CONFLICT)
                .with(FieldNameConstants.message, "Error concurrency failure")
                .with(FieldNameConstants.path, extractContextPath(request))
                .build();
        return create(e, problem, request);
    }

    private static String extractContextPath(final NativeWebRequest request) {
        return Optional.ofNullable(request.getNativeRequest(HttpServletRequest.class))
                .map(HttpServletRequest::getContextPath)
                .orElse(Constants.SLASH);
    }
}
