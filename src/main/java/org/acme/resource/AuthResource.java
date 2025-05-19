package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UserDTO;
import org.acme.model.User;
import org.acme.service.UserService;
import org.acme.util.JwtUtil;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getUsername());
        boolean created = userService.register(user);
        if (!created) {
            return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
        }
        return Response.ok("User Registered").build();
    }

    @POST
    @Path("/login")
    public Response login(UserDTO dto) {
        User user = userService.authenticate(dto.getUsername(), dto.getPassword());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = JwtUtil.generateToken(user.getUsername());
        return Response.ok().entity("{\"token\":\"" + token + "\"}").build();
    }
}