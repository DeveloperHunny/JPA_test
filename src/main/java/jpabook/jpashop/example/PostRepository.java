package jpabook.jpashop.example;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PostRepository {

    private final EntityManager em;

    public PostRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(Post post){
        em.persist(post);
        return post.getId();
    }

    public Post findOne(Long id){
        return em.find(Post.class, id);
    }
}
