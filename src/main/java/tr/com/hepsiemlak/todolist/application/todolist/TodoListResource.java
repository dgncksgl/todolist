package tr.com.hepsiemlak.todolist.application.todolist;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserService;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListCreateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListGetDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListUpdateDto;
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.port.TodoListService;
import tr.com.hepsiemlak.todolist.shared.util.JwtTokenUtil;

import java.util.List;

@RestController
@RequestMapping("/todo-lists")
@Tag(name = "TodoList Services (Todo listesi için CRUD işlemlerin yapılabileceği servis grubudur)")
public class TodoListResource {

    private final TodoListService todoListService;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    public TodoListResource(TodoListService todoListService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.todoListService = todoListService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Operation(summary = "GET Todo List", description = "Tüm todo list kayıt listesini döndüren servistir.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/")
    public ResponseEntity<List<TodoListGetDto>> getAllTodoList() {
        UserGetDto user = userService.getUserByName(jwtTokenUtil.getCurrentUser().orElse(null));
        return ResponseEntity.ok(todoListService.getAllTodoListByUserId(user.getId()));
    }

    @Operation(summary = "GET TodoList By Id", description = "'id' bilgisi verilen todo list kaydının bilgilerini döndüren servistir.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<TodoListGetDto> getTodoListItemById(@PathVariable("id") String id) {
        return ResponseEntity.ok(todoListService.getTodoListById(id));
    }

    @Operation(summary = "ADD Todo List Item",
            description = "Todo list kaydı eklenemede kullanılan servistir. Eklenen kaydın 'id' bilgisini döndürür. " +
                    "<ul><li>__Doküman gönderilirken dikkat edilmesi gereken durum !__ </li></ul>" +
                    "<ul><li>```documents parametresi altında bulunan data parametresine dokümanın base64 formatı gönderilmelidir.```</li></ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/")
    public ResponseEntity<String> addTodoListItem(@Valid @RequestBody TodoListCreateDto todoListCreateDto) {
        UserGetDto user = userService.getUserByName(jwtTokenUtil.getCurrentUser().orElse(null));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoListService.addTodoList(todoListCreateDto, user.getId()));
    }

    @Operation(summary = "UPDATE Todo List Item",
            description = "Varolan todo list kaydının bilgilerini güncellemek için kullanılan servistir. " +
                    "Güncellenen kaydın bilgilerini döndürür." +
                    "<ul><li>__Doküman gönderilirken dikkat edilmesi gereken durum !__ </li></ul>" +
                    "<ul><li>```documents parametresi altında bulunan data parametresine dokümanın base64 formatı gönderilmelidir.```</li></ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/")
    public ResponseEntity<TodoListGetDto> updateTodoListItem(@Valid @RequestBody TodoListUpdateDto todoListUpdateDto) {
        UserGetDto user = userService.getUserByName(jwtTokenUtil.getCurrentUser().orElse(null));
        return ResponseEntity.ok(todoListService.updateTodoList(todoListUpdateDto, user.getId()));
    }

    @Operation(summary = "DELETE Todo List Item", description = "Varolan todo list kaydını silmek için kullanılan servistir.")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoListItem(@PathVariable("id") String id) {
        todoListService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "DONE Todo List Item", description = "Varolan todo list kaydını tamamlandı durumuna çekmek için kullanılan servistir.")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<TodoListGetDto> doneTodoListItem(@PathVariable("id") String id) {
        return ResponseEntity.ok(todoListService.doneTodoListItem(id));
    }
}
