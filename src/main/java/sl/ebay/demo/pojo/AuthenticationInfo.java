package sl.ebay.demo.pojo;

public class AuthenticationInfo {

    private Long userId;
    private String accountName;
    private String role;

    @Override
    public String toString() {
        return "AuthenticationInfo{" +
                "userId=" + userId +
                ", accountName='" + accountName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
