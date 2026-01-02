package com.example.E_commerce_api.repository;

import com.example.E_commerce_api.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {


  Optional <Wishlist> findByUserId(long id);
}
