package com.apollogix.demo.web.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.text.MessageFormat;

public class PreconditionFailedException extends AbstractThrowableProblem {

    public PreconditionFailedException(final String message, final Object... args) {
        super(Problem.DEFAULT_TYPE, MessageFormat.format(message, args), Status.PRECONDITION_FAILED);
    }

}
