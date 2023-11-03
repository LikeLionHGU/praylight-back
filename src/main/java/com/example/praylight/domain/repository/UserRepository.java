package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.praylight.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}

