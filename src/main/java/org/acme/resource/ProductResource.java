package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.acme.dto.ProductDTO;
import org.acme.dto.SearchDTO;
import org.acme.dto.SearchPriceDTO;
import org.acme.model.Product;
import org.acme.repository.ProductRepository;
import org.acme.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class ProductResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    ProductRepository productRepository;

    @Context
    UriInfo uriInfo;

    @GET
    public List<Product> getAllProducts() {
        try {
            return productRepository.listAll();
        } catch (Exception e) {
            LOGGER.info("Error : ", e);
            return null;
        }
    }

    @POST
    @Path("/searchByName")
    public List<Product> searchProductsByName(SearchDTO dto){
        try {
            return productRepository.searchByName(dto.getName());
        } catch (Exception e) {
            LOGGER.info("Error : ", e);
            return null;
        }
    }

    @POST
    @Path("/searchByPriceRange")
    public List<Product> searchProductsByPrice(SearchPriceDTO dto){
        try {
            return productRepository.searchByPriceRange(dto.getMinPrice(), dto.getMaxPrice());
        } catch (Exception e) {
            LOGGER.info("Error : ", e);
            return null;
        }
    }

    @POST
    @Transactional
    @Path("/insertOrUpdateProduct")
    public Response insertOrUpdateProduct(ProductDTO dto){
        try {
            Product item = new Product();

            if(dto.getId() != null){
                item = productRepository.findById(dto.getId());
                if(item == null){
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Product with id " + dto.getId() + " not found")
                            .build();
                }
            }

            item.setDescription(dto.getDescription());
            item.setName(dto.getName());
            item.setPrice(dto.getPrice());
            item.setCreatedAt(new Date());

            productRepository.persist(item);

            var createdUri = uriInfo.getAbsolutePathBuilder()
                    .path(item.getId().toString())
                    .build();

            return Response.ok(createdUri)
                    .entity(item)
                    .build();

        } catch (Exception e) {
            LOGGER.info("Error : ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Something went wrong: " + e.getMessage(), 500))
                    .build();
        }
    }


    @POST
    @Transactional
    @Path("/deleteProduct")
    public Response deleteProduct(ProductDTO dto){
        try {
            Product item = new Product();

            if(dto.getId() != null){
                item = productRepository.findById(dto.getId());
                if(item == null){
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("Product with id " + dto.getId() + " not found")
                            .build();
                }
            }

            productRepository.delete(item);

            return Response.ok("data with id " + dto.getId() + " successfully deleted")
                    .build();

        } catch (Exception e) {
            LOGGER.info("Error : ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Something went wrong: " + e.getMessage(), 500))
                    .build();
        }
    }



}
