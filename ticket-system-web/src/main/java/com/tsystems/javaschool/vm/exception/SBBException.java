package com.tsystems.javaschool.vm.exception;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Wrapper;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Created by Vlad on 02.09.2014.
 */
public abstract class SBBException extends Exception {
    public SBBException() {
        super();
    }

    public SBBException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace(PrintWriter s) {

        s.println(this);
        StackTraceElement[] trace = this.getStackTrace();
        for (StackTraceElement traceElement : trace) {
            if (traceElement.toString().startsWith("com.tsystems.javaschool.vm")) {
                s.println("\tat " + traceElement);
            }
        }

    }
}
