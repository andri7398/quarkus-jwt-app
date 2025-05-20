package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UserDTO;
import org.acme.model.User;
import org.acme.service.UserService;
import org.acme.util.ErrorResponse;
import org.acme.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    @Transactional
    public Response register(UserDTO dto) {
        try {
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            boolean created = userService.register(user);
            if (!created) {
                return Response.status(Response.Status.CONFLICT).entity("User already exists").build();
            }
            return Response.ok("User Registered").build();
        } catch (Exception e) {
            LOGGER.error("error : ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Something went wrong: " + e.getMessage(), 500))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(UserDTO dto) {
        try {
            User user = userService.authenticate(dto.getUsername(), dto.getPassword());
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"Invalid username or password\"}")
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            String token = JwtUtil.generateToken(user.getUsername());
            return Response.ok().entity("{\"token\":\"" + token + "\"}").build();
        } catch (Exception e) {
            LOGGER.error("error : ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Something went wrong: " + e.getMessage(), 500))
                    .build();
        }
    }
}