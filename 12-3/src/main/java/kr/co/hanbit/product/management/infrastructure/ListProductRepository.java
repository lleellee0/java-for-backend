package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("test")
public class ListProductRepository implements ProductRepository {

    private List<Product> products = new CopyOnWriteArrayList<>();
    private AtomicLong sequence = new AtomicLong(1L);

    public Product add(Product product) {
        product.setId(sequence.getAndAdd(1L));

        products.add(product);
        return product;
    }

    public Product findById(Long id) {
        return products.stream()
            .filter(product -> product.sameId(id))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Product를 찾지 못했습니다."));
    }

    public List<Product> findAll() {
        return products;
    }

    public List<Product> findByNameContaining(String name) {
        return products.stream()
            .filter(product -> product.containsName(name))
            .toList();
    }

    public Product update(Product product) {
        Integer indexToModify = products.indexOf(product);
        products.set(indexToModify, product);
        return product;
    }

    public void delete(Long id) {
        Product product = this.findById(id);
        products.remove(product);
    }

}
