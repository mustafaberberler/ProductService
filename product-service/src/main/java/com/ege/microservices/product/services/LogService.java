package com.ege.microservices.product.services;

public interface LogService {

    void sendLog(String level, String message, String serviceName);

}
