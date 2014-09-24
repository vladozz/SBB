package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.SBBException;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionController {
    private static final Logger LOG = Logger.getLogger(ExceptionController.class);

    @ExceptionHandler(value = OutdateException.class)
    public ModelAndView OutdateHandler(SBBException e) {
        LOG.info(prepareMessage(e));
        ModelAndView modelAndView = new ModelAndView("msg");
        modelAndView.addObject("status", "outdate");
        modelAndView.addObject("messages", "Your page is irrelevant! Reload the page and repeat the operation.");
        return modelAndView;
    }
    @ExceptionHandler(value = SBBException.class)
    public ModelAndView SBBHandler(SBBException e) {
        LOG.info(prepareMessage(e));
        ModelAndView modelAndView = new ModelAndView("msg");
        modelAndView.addObject("status", "error");
        modelAndView.addObject("messages", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView AllExceptionHandler(Exception e) {
        LOG.error(prepareMessage(e));
        ModelAndView modelAndView = new ModelAndView("msg");
        modelAndView.addObject("status", "error");
        modelAndView.addObject("messages", "Internal Server Error");
        return modelAndView;
    }

    @ExceptionHandler(value = Error.class)
    public ModelAndView AllErrorHandler(Error e) {
        LOG.fatal(prepareMessage(e));
        ModelAndView modelAndView = new ModelAndView("msg");
        modelAndView.addObject("status", "error");
        modelAndView.addObject("messages", "Internal Server Error");
        return modelAndView;
    }

    public String prepareMessage(Throwable e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return "Username: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\n" + errors.toString();
    }
}
