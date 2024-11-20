package com.example.venda.entities.Enum;

public enum AcessLevels {

        ROLE_CLIENT("CLIENT"),
        ROLE_SELLER("SELLER"),
        ROLE_SUPERVISOR("SUPERVISOR");

        private final String role;

    AcessLevels(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
