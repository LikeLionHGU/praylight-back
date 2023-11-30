package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.PrayTogether;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrayTogetherRepository extends JpaRepository<PrayTogether, Long>{

    PrayTogether findByMemberAndPrayer(Member member, Prayer prayer);
    List<PrayTogether> findAllByMember(Member member);

}

