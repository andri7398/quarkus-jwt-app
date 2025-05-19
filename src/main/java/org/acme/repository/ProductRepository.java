package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> searchByName(String name) {
        return find("LOWER(name) LIKE LOWER(?1) ORDER BY name ASC", "%" + name + "%").list();
    }

    public List<Product> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if(minPrice != null && maxPrice != null){
            return find("price BETWEEN ?1 and ?2 ORDER BY price ASC", minPrice,maxPrice).list();
        }else if(minPrice != null){
            return find("price >= ?1 ORDER BY price ASC", minPrice).list();
        }else if(maxPrice != null){
            return find("price <= ?1 ORDER BY price ASC", maxPrice).list();
        }else{
            return listAll();
        }
    }
}
