package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class ItemRepository {

    private final EntityManager em;

    public ItemRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(Item item){
        em.persist(item);
        return item.getId();
    }

    public Optional<Item> findById(Long id){
        return em.createQuery("select i from Item i where i.id = :id", Item.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
