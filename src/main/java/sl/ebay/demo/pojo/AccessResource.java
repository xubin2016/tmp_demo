package sl.ebay.demo.pojo;

import java.util.Objects;

public class AccessResource {

    private Long userId;
    private String resourceName;

    public AccessResource() {
    }

    public AccessResource(Long userId, String resourceName) {
        this.userId = userId;
        this.resourceName = resourceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessResource that = (AccessResource) o;
        return Objects.equals(userId, that.userId) && Objects.equals(resourceName, that.resourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, resourceName);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
