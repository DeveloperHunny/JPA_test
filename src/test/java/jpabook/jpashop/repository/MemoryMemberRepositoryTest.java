package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        repository.save(member);
        Member findOne = repository.findById(member.getId()).get();

        //then
        assertThat(findOne).isEqualTo(member);
    }

    @Test
    public void findByName() throws Exception {
        //given
        Member memberA = new Member();
        memberA.setName("memberA");

        Member memberB = new Member();
        memberB.setName("memberB");

        repository.save(memberA);
        repository.save(memberB);

        //when
        Member findMemberB = repository.findByName("memberB").get();
        Member findMemberA = repository.findByName("memberA").get();

        //then
        assertThat(findMemberA).isEqualTo(memberA);
        assertThat(findMemberB).isEqualTo(memberB);
    }

    @Test
    public void findAll() throws Exception {
        //given
        Member memberA = new Member();
        memberA.setName("memberA");
        repository.save(memberA);

        Member memberB = new Member();
        memberB.setName("memberB");
        repository.save(memberB);

        Member memberC = new Member();
        memberC.setName("memberC");
        repository.save(memberC);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(3);
    }
}