package com.empresa.iogurtes.gestaoiogurtes.core.ports;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
