package com.example.DoAnMH;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ADMIN(1),
    USER(2),
    EMPLOYEE(3);

    public final long value;
}