package ru.geekbrains.smartkt.services;

import ru.geekbrains.smartkt.dao.Product;
import ru.geekbrains.smartkt.dto.ProductDTO;
import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;
import ru.geekbrains.smartkt.repositories.ProductRepository;

import static ru.geekbrains.smartkt.prefs.Prefs.*;
import static ru.geekbrains.smartkt.repositories.specs.ProductSpecs.*;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

import lombok.*;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    //использовать одну общую страницу для многоклиентского сервиса недопустимо -
    //все клиенты будут переходить именно на нее
    //private Pageable curPage;

    // если товар не найден, нужно возвращать не null, а исключение
    public ProductDTO getOne(int id) {
        Product p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ERR_PRODUCT_NOT_FOUND +": id="+id));
        return new ProductDTO(p);
    }

    // получить список всех товаров...
    // ...без разбиения по страницам
    public List<ProductDTO> getAll() {
        return repository.findAll()
                .stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    // ...с разбиением и учетом числа выводимых его элементов на страницу
    public Page<Product> getSome(Integer page, Integer pageSize,
                                 String title, Integer minCost, Integer maxCost) {

        Specification<Product> spec = Specification.where(null);
        //Pageable p;
        //findAllProductsByNextPage(){
        //setCurPage(curPage.next());
        //setCurPage(curPage.previousOrFirst());
        //return repository.findAll(curPage).getContent();}
        //at Controller:
        //@GetMapping("/prev")
        //public List<Product> getPrevPage() {
        //return service.findAllProductsByPrevPage();
        if (title != null && !title.isBlank()) spec = spec.and(textLikeIgnoreCase("title", title));
        if (minCost != null) spec = spec.and(intGreaterThanOrEqualTo("cost", minCost));
        if (maxCost != null) spec = spec.and(intLessThanOrEqualTo("cost", maxCost));

        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = DEFAULT_PAGE_SIZE;
        Page<Product> p = repository.findAll(spec, PageRequest.of(page - 1, pageSize));
        //System.out.println(p.getTotalElements());
        return p;
    }

    // перед добавлением нового товара товара в БД, его идентификатор сбрасывается,
    // поэтому попытка добавления существующего товара не может произойти
    // TODO(?):
    //  сохранение уникальности первичного ключа -- если удалить товар, затем снова добавить,
    //  id последнего добавленного будет на 1 больше последнего существующего (autoincrement),
    //  при каждой последующей паре удалений и добавлений обратно
    public Product/*ResponseEntity<?>*/ add(Product product) {
        //repository./*add*/save(product);
        /*if (repository.productExists(product.getTitle()))
            return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(),
                    "Product '"+product.getTitle()+"' already exists"), HttpStatus.CONFLICT);*/
        return repository.save(product);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    // после изменения полей объекта сущности при завершении транзакции он автоматически
    // будет сохранен в таблице БД, поэтому использовать аннотацию @Modifying нет нужды
    @Transactional
    public Product update(ProductDTO product) {
        Product p = repository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ERR_CANNOT_UPDATE +", id="+product.getId()));
        p.setCost(product.getCost());
        p.setTitle(product.getTitle());
        return p;
    }

    public void deleteOne(Integer id) { repository./*delete*/deleteById(id); }
}