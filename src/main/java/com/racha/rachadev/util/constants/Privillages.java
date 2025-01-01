package com.racha.rachadev.util.constants;

public enum Privillages {
    RESET_ANY_USER_PASSWORD("1", "RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL("2", "ACCESS_ADMIN_PANEL");

    private String Id;
    private String privillage;
    private Privillages(String Id, String privillage) {
        this.Id = Id;
        this.privillage = privillage;
    }
    public String getId() {
        return Id;
    }
    public String getPrivillage() {
        return privillage;
    }
}
