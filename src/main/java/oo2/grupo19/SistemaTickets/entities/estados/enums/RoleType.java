package oo2.grupo19.SistemaTickets.entities.estados.enums;

public enum RoleType {
    ADMIN,
    EMPLOYEE,
    USER;

    public String getPrefixedName() {
        return "ROLE_" + this.name();
    }
}
