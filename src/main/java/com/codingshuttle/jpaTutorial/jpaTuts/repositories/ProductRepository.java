package com.codingshuttle.jpaTutorial.jpaTuts.repositories;

import com.codingshuttle.jpaTutorial.jpaTuts.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByTitle(String title);

    //List<ProductEntity> findByTitleOrderByPrice(String title);

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime localDateTime);

    List<ProductEntity> findByQuantityAndPrice(int quantity, BigDecimal price);

    List<ProductEntity> findByQuantityGreaterThanAndPriceLessThan(int quantity, BigDecimal price);

    List<ProductEntity> findByTitleLike(String title);

    List<ProductEntity> findByTitleContaining(String title);

    List<ProductEntity> findByTitleContainingIgnoreCase(String title);

   // Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    //Defining our own query using JPQL
   // @Query("select e.title from ProductEntity e where e.title=:title and e.price=:price")
   @Query("select e from ProductEntity e where e.title=?1 and e.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

}
