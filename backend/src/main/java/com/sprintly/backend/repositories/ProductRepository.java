package com.sprintly.backend.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprintly.backend.entities.Products;
import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findByCategoryId(Long categoryId);

    // @EntityGraph(attributePaths = "category")
    @Query("""
    SELECT p FROM Products p
    JOIN FETCH p.category
    """)
    List<Products> findAllWithCategory();

    @Query("""
        SELECT p FROM Products p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Products> search(@Param("keyword") String keyword);

@Query(value = """
        SELECT
        p.id                                   AS id,
        p.category_id                          AS categoryId,

        pi.image_url                           AS image,

        p.name                                 AS title,
        c.name                                 AS category,

        p.price                                AS price,
        NULL                                   AS originalPrice,

        ROUND(AVG(r.rating), 1)                AS rating,
        COUNT(r.id)                            AS reviews,

        (p.created_at >= NOW() - INTERVAL '14 days') AS isNew,

        FALSE                                  AS isSale,

        p.stock_quantity                       AS stockQuantity
    FROM products p
    LEFT JOIN categories c
        ON c.id = p.category_id

    LEFT JOIN product_images pi
        ON pi.product_id = p.id
       AND pi.is_primary = TRUE

    LEFT JOIN reviews r
        ON r.product_id = p.id
    
    GROUP BY
        p.id,
        p.category_id,
        pi.image_url,
        p.name,
        c.name,
        p.price,
        p.created_at,
        p.stock_quantity
    ORDER BY p.id
""", nativeQuery = true)
List<Object[]> findAllWithIsSale();


@Query("""
SELECT DISTINCT p FROM Products p
LEFT JOIN FETCH p.productReviews
LEFT JOIN FETCH p.category
LEFT JOIN FETCH p.productProductImages
WHERE p.id = :id
""")
Products findDetails(@Param("id") Long id);


}
