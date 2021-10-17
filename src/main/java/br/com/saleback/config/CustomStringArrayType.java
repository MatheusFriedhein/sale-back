package br.com.saleback.config;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import java.util.Properties;

public class CustomStringArrayType extends StringArrayType {

    @Override
    public void setParameterValues(Properties parameters) {

        if (parameters.containsKey(super.PARAMETER_TYPE)) {
            super.setParameterValues(parameters);

        }
    }
}