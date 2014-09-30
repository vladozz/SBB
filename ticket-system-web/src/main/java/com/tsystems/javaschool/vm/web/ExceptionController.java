package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.exception.OutdateException;
import com.tsystems.javaschool.vm.exception.SBBException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionController {
    private static final Logger LOG = Logger.getLogger(ExceptionController.class);

    @ResponseStatus(value = HttpStatus.UPGRADE_REQUIRED, reason = "Your page is irrelevant! Reload the page and repeat the operation.")
    @ExceptionHandler(value =  {OutdateException.class, OptimisticLockException.class})
    public void OutdateHandler(Exception e, HttpServletResponse response) throws IOException {
        LOG.info(prepareMessage(e));
    }

    @ExceptionHandler(value = SBBException.class)
    public ModelAndView SBBHandler(SBBException e, HttpServletResponse response) throws IOException {
        LOG.info(prepareMessage(e));
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
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
        e.printStackTrace();
        e.printStackTrace(new PrintWriter(errors));
        return "Username: " + SecurityContextHolder.getContext().getAuthentication().getName() + "\n" + errors.toString();
    }
}
