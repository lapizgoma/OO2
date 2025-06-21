package oo2.grupo19.SistemaTickets.entities.estados.enums;

public enum RoleType {
    ADMIN,
    EMPLOYEE,
    CUSTOMER;

    public String getPrefixedName() {
        return "ROLE_" + this.name();
    }
    /*public static RoleType fromString(String value) {
    for (RoleType type : RoleType.values()) {
        if (type.name().equalsIgnoreCase(value) || type.getPrefixedName().equalsIgnoreCase(value)) {
            return type;
        }
    }
    throw new IllegalArgumentException("No enum constant for value: " + value);
    } */
}
