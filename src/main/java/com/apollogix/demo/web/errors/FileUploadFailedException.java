package com.apollogix.demo.web.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.text.MessageFormat;

public class FileUploadFailedException extends AbstractThrowableProblem {

    public FileUploadFailedException(final String message, final Object... args) {
        super(Problem.DEFAULT_TYPE, MessageFormat.format(message, args), Status.EXPECTATION_FAILED);
    }

}
