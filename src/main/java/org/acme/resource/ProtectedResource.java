package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import jakarta.inject.Inject;

@Path("/protected")
public class ProtectedResource {

    @Inject
    @Claim("upn")
    ClaimValue<String> username;

    @GET
    @RolesAllowed("user")
    public String securedHello() {
        return "Hello " + username.getValue();
    }
}
