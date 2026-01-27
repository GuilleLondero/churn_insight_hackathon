package com.churnInsight.churnInsight.service;

public interface IDataConverter {
    
    public <T> T obtenerDatos(String json, Class<T> clase);

}
