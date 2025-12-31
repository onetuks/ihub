package com.onetuks.ihub.dto.role;

import java.util.List;

public record UserRoleRequest(
    List<String> roleIds
) {

}
