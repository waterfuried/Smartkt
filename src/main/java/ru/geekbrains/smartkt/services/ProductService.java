package ru.geekbrains.smartkt.services;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

import lombok.*;

import ru.geekbrains.smartkt.dao.items.StoredItem;
import ru.geekbrains.smartkt.dto.StoredItemDTO;
import ru.geekbrains.smartkt.exceptions.ResourceNotFoundException;
import ru.geekbrains.smartkt.repositories.ProductRepository;
import static ru.geekbrains.smartkt.prefs.Prefs.*;
import static ru.geekbrains.smartkt.repositories.specs.ProductSpecs.*;

@Service
@Data
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    //использовать одну общую страницу для многоклиентского сервиса недопустимо -
    //все клиенты будут переходить именно на нее
    //private Pageable curPage;

    // если товар не найден, нужно возвращать не null, а исключение
    public StoredItemDTO getOne(int id) {
        StoredItem p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ERR_ITEM_NOT_FOUND +": id="+id, null));
        return new StoredItemDTO(p);
    }

    // получить список всех товаров...
    // ...без разбиения по страницам
    public List<StoredItemDTO> getAll() {
        return repository.findAll()
                .stream().map(StoredItemDTO::new).collect(Collectors.toList());
    }

    // ...с разбиением и учетом числа выводимых его элементов на страницу
    public Page<StoredItem> getSome(Integer page, Integer pageSize,
                              String title, Integer minCost, Integer maxCost) {

        Specification<StoredItem> spec = Specification.where(null);
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
        return repository.findAll(spec, PageRequest.of(page - 1, pageSize));
    }

    // перед добавлением нового товара товара в БД, его идентификатор сбрасывается,
    // поэтому попытка добавления существующего товара не может произойти
    // TODO(?):
    //  сохранение уникальности первичного ключа -- если удалить товар, затем снова добавить,
    //  id последнего добавленного будет на 1 больше последнего существующего (autoincrement),
    //  при каждой последующей паре удалений и добавлений обратно
    public StoredItemDTO/*ResponseEntity<?>*/ add(StoredItemDTO item) {
        //repository./*add*/save(product);
        /*if (repository.productExists(product.getTitle()))
            return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(),
                    "Product '"+product.getTitle()+"' already exists"), HttpStatus.CONFLICT);*/
        return new StoredItemDTO(repository.save(new StoredItem(item)));
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    // после изменения полей объекта сущности при завершении транзакции он автоматически
    // будет сохранен в таблице БД, поэтому использовать аннотацию @Modifying нет нужды
    @Transactional
    public StoredItemDTO update(StoredItemDTO product) {
        StoredItem p = repository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ERR_CANNOT_UPDATE +": id="+product.getId(), null));
        p.setCost(product.getCost());
        p.setTitle(product.getTitle());
        return new StoredItemDTO(p);
    }

    public void deleteOne(Integer id) { repository./*delete*/deleteById(id); }
}