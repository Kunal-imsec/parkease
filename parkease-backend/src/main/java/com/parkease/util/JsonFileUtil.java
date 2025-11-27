package com.parkease.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileUtil {
    
    private final ObjectMapper objectMapper;
    private final String dataPath = "src/main/resources/data/";
    
    public JsonFileUtil() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ensureDataDirectoryExists();
    }
    
    private void ensureDataDirectoryExists() {
        File directory = new File(dataPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public synchronized <T> List<T> readList(String filename, TypeReference<List<T>> typeReference) {
        try {
            File file = new File(dataPath + filename);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public synchronized <T> void writeList(String filename, List<T> data) {
        try {
            File file = new File(dataPath + filename);
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized <T> T read(String filename, Class<T> clazz) {
        try {
            File file = new File(dataPath + filename);
            if (!file.exists()) {
                return null;
            }
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public synchronized <T> void write(String filename, T data) {
        try {
            File file = new File(dataPath + filename);
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
