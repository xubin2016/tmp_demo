package sl.ebay.demo.common;

public enum RoleEnum {

    ADMIN("admin"),
    USER("user");

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
    String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
