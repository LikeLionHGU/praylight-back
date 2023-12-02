package com.example.praylight.domain.repository;
import com.example.praylight.application.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.praylight.domain.entity.Member;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findById(String id);
    Member findByUid(String uid);
}

