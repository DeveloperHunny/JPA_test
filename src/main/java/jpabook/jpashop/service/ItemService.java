package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long saveItem(Item item){
        return itemRepository.save(item);
    }

    public Optional<Item> findItem(Long id){
        return itemRepository.findById(id);
    }

    public List<Item> findItemList(){
        return itemRepository.findAll();
    }
}
