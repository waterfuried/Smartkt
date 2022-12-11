package ru.geekbrains.smartkt.controllers;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;

import java.util.*;

import lombok.*;

import ru.geekbrains.smartkt.dao.items.StoredItem;
import ru.geekbrains.smartkt.dto.ProductDTO;
import ru.geekbrains.smartkt.services.ProductService;
import static ru.geekbrains.smartkt.prefs.Prefs.*;

// Сделать RestController позволяющий выполнять набор операций над сущностью
// Реализуйте REST контроллер для работы с сущностью Product
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    //или без использования Ломбок можно оставить этот конструктор
    //public ProductController(ProductService service) { this.service = service; }

    /*
        получение товара по id - отдельно отобразить товар с указанным id

        поскольку запросы отсылаются по нажатию разных кнопок на форме,
        тип запроса определяется в коде соответсвующего обработчика на фронте,
        и поскольку работа происходит со страницей, в адресной строке прописывается
        именно она, а здесь отпадает необходимость указания этих типов - get, delete итд,
        но для проверки можно по ним переходить (вручную)
        сами же типы запросов определяют выполнение соответствующей CRUD-операции:
        post-запрос - Create, get-запрос - Read, put-запрос - Update, delete-запрос - Delete

        по запросу пользователя стоит возвращать данные, определяемые "контрактом" с ним -
        только самые необходимые поля, а не всю их реализацию в виде сущностей БД,
        поскольку это может уменьшить объем передаваемых данных, т.к. реализация сущностей
        может иметь более развернутую структуру (модель).
        В любом случае - эквивалентны они или нет - для реализации "контракта с пользователем"
        можно использовать метод, преобразовывающий данные сущностей в "контркатные данные",
        так называемые объекты для передачи данных - DTO (Data Transfer Objects),
        но это все касается только передаваемых пользователю данных - запросы на добавление,
        обновление и удаление данных - внутренние и определяются моделью данных (сущностями)
    */
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Integer id) { return service.getOne(id); }

    // получение всех товаров единым списком
    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() { return service.getAll(); }

    // получение всех товаров с разбиением по страницам
    @GetMapping
    public Page<ProductDTO> getProducts(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "page_size", defaultValue = DEFAULT_PAGE_SIZE+"") Integer pageSize,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost)
    {
        return service.getSome(page < 1 ? 1 : page, pageSize, title, minCost, maxCost)
                .map(ProductDTO::new);
    }

    /*
        создание нового товара
        Создать страницу со списком товаров, на которой можно добавлять позиции
        и редактировать существующие.
        На эту страницу должны иметь доступ админы и менеджеры.

        входные данные приходят тоже не по полной, а по контрактной модели
        при добавлении нового товара в БД он еще не имеет своего id,
        и получает его только после добавления
        возвращаемые данные могут использоваться для сравнения того, что
        требовалось записать в БД и что было записано фактически
    */
    @PostMapping//("/")
    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_MANAGER", "ROLE_MAJOR_MANAGER"})
    public ProductDTO addProduct(@RequestBody ProductDTO product) {
        product.setId(null); // если был передан уже существующий товар, сбросить его идентификатор
        // если какие-то данные пользователем не введены или введены некорректно,
        // выбросить исключение ValidationException
        product.validate();
        return new ProductDTO(service.add(new StoredItem(product)));
    }

    // удаление товара по id
    @DeleteMapping("/{id}")//("/products/delete/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_MANAGER", "ROLE_MAJOR_MANAGER"})
    public void delProduct(@PathVariable Integer id) {
        service.deleteOne(id);
    }

    @PutMapping
    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_MANAGER", "ROLE_MAJOR_MANAGER"})
    public void updateProduct(@RequestBody ProductDTO product) {
        service.update(product);
    }
}