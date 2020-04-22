package com.diomeda.credential.controller;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

public class BaseController {

    @Autowired
    ResourceBundleMessageSource message;


    protected String getMessage(String code) {
        return message.getMessage(code, null, "default", new Locale("es") );
    }
    
    protected String getMessage(String code, Object arg) {
        return MessageFormat.format(message.getMessage(code, null, "default", new Locale("es")), arg);
    }

}
