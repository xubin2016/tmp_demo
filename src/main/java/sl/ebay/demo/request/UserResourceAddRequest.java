package sl.ebay.demo.request;


import sl.ebay.demo.pojo.AccessResource;

import java.util.List;

public class UserResourceAddRequest {

    private List<AccessResource> resourceList;

    public List<AccessResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<AccessResource> resourceList) {
        this.resourceList = resourceList;
    }
}
