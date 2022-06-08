package com.pskjeong.psk.utils;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

    private static ModelMapper modelMapper = null;
    private ModelMapperUtils(){}
    public static ModelMapper getInstance() {
        if (modelMapper == null) modelMapper = new ModelMapper();
        return modelMapper;
    }
}
