package br.dev.bs.shortenerapi.enums;

public enum UsersRoles {
    USER("USER"), ADMIN("ADMIN");
    private final String value;

    UsersRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UsersRoles fromString(String name) {
        for (UsersRoles tipo : UsersRoles.values()) {
            if (tipo.name().equals(name)) {
                return tipo;
            }
        }
        return null;
    }

}
