package com.apollogix.demo.mapper;

public interface EntityMapper <U, V>{
    U toDTO(V source);
    V toEntity(U source);
}
