package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.SearchDTO;
import org.acme.dto.SearchPriceDTO;
import org.acme.model.Product;
import org.acme.repository.ProductRepository;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class ProductResource {
    @Inject
    ProductRepository productRepository;

    @GET
    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    @POST
    @Path("/searchByName")
    public List<Product> searchProductsByName(SearchDTO dto){
        try {
            return productRepository.searchByName(dto.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/searchByPriceRange")
    public List<Product> searchProductsByName(SearchPriceDTO dto){
        try {
            return productRepository.searchByPriceRange(dto.getMinPrice(), dto.getMaxPrice());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
