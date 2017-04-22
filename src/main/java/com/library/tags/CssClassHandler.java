package com.library.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.library.errors.ValidationError;

import java.io.IOException;
import java.util.ArrayList;

public class CssClassHandler extends TagSupport {

    private String defaultClass;
    private String fieldName;
    private ArrayList<ValidationError> errorList;
    private String errorClass;
    private static String DEFAULT_ERROR_CLASS = " has-error";

    public void setDefaultClass(String defaultClass) {
        this.defaultClass = defaultClass;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setErrorClass(String errorClass) {
        this.errorClass = errorClass;
    }

    public ArrayList<ValidationError> getErrorList() {
        return errorList;
    }

    public void setErrorList(ArrayList<ValidationError> errorList) {
        this.errorList = errorList;
    }

    public int doStartTag() throws JspException {
        JspWriter out=pageContext.getOut();
        try {
            String error = "";
            if(errorList != null){
                error += getErrorClass();
            }

            out.print( defaultClass + error );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    private String getErrorClass(){
        for(ValidationError error : errorList){
            if(error.getField().equals(fieldName)){
                if(errorClass != null){
                    return " " + errorClass;
                }
                return DEFAULT_ERROR_CLASS;
            }
        }
        return "";
    }
}
