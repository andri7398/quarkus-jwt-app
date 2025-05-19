package org.acme.util;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.acme.model.Product;
import org.acme.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Date;

public class ProductDataInitializer {
    @Inject
    ProductRepository productRepository;

    @PostConstruct
    void init() {
        if (productRepository.count() == 0) {

            Product p1 = new Product();
            p1.setName("Kopi Luwak");
            p1.setDescription("Kopi termahal dari biji kopi asli Indonesia");
            p1.setPrice(new BigDecimal("150000"));
            p1.setCreatedAt(new Date());

            Product p2 = new Product();
            p2.setName("Batik Pekalongan");
            p2.setDescription("Kain batik khas Pekalongan dengan motif tradisional");
            p2.setPrice(new BigDecimal("300000"));
            p2.setCreatedAt(new Date());

            Product p3 = new Product();
            p3.setName("Keris Jawa");
            p3.setDescription("Senjata tradisional dengan nilai budaya tinggi");
            p3.setPrice(new BigDecimal("750000"));
            p3.setCreatedAt(new Date());

            Product p4 = new Product();
            p4.setName("Tempe Organik");
            p4.setDescription("Tempe sehat dan organik dari kedelai pilihan");
            p4.setPrice(new BigDecimal("12000"));
            p4.setCreatedAt(new Date());

            Product p5 = new Product();
            p5.setName("Lemang");
            p5.setDescription("Makanan tradisional dari ketan dan santan dalam bambu");
            p5.setPrice(new BigDecimal("25000"));
            p5.setCreatedAt(new Date());

            Product p6 = new Product();
            p6.setName("Gamelan Miniatur");
            p6.setDescription("Miniatur alat musik tradisional Jawa untuk hiasan");
            p6.setPrice(new BigDecimal("450000"));
            p6.setCreatedAt(new Date());

            Product p7 = new Product();
            p7.setName("Baju Kebaya");
            p7.setDescription("Pakaian tradisional wanita Indonesia yang elegan");
            p7.setPrice(new BigDecimal("600000"));
            p7.setCreatedAt(new Date());

            Product p8 = new Product();
            p8.setName("Sambal Terasi");
            p8.setDescription("Sambal pedas khas Indonesia dengan terasi pilihan");
            p8.setPrice(new BigDecimal("15000"));
            p8.setCreatedAt(new Date());

            Product p9 = new Product();
            p9.setName("Keripik Singkong");
            p9.setDescription("Camilan gurih dan renyah dari singkong");
            p9.setPrice(new BigDecimal("20000"));
            p9.setCreatedAt(new Date());

            Product p10 = new Product();
            p10.setName("Batik Solo");
            p10.setDescription("Kain batik khas Solo dengan motif klasik");
            p10.setPrice(new BigDecimal("350000"));
            p10.setCreatedAt(new Date());

            productRepository.persist(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        }
    }
}
