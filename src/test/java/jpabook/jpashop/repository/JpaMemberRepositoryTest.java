package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaMemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member();
        member.setName("member");

        Long saveId = memberRepository.save(member);
        Member findOne = memberRepository.findById(saveId).get();

        assertThat(findOne.getName()).isEqualTo(member.getName());
    }

    @Test
    void findById() {
        Member member1 = new Member();
        member1.setName("member1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setName("member3");
        memberRepository.save(member3);

        Member findOne = memberRepository.findById(member2.getId()).get();

        assertThat(findOne.getName()).isEqualTo(member2.getName());
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("member1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setName("member3");
        memberRepository.save(member3);

        Member findOne = memberRepository.findByName(member2.getName()).get();

        assertThat(findOne.getId()).isEqualTo(member2.getId());
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("member1");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setName("member3");
        memberRepository.save(member3);

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(3);


    }
}