package jpabook.jpashop.example;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CommentRepository {

    private final EntityManager em;


    public CommentRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(Comment comment){
        em.persist(comment);
        return comment.getId();
    }

    public Comment findOne(Long id){
        return em.find(Comment.class, id);
    }
}
