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
import tr.com.hepsiemlak.todolist.domain.todolist.applicationservice.dto.TodoListItemPriorityGetDto;
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

    @Operation(summary = "GET Todo List",
            description = "Tüm todo list kayıt listesini döndüren servistir." +
                    "<br>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen todolist kayıt id'sidir.</li>" +
                    "<li>title: Todo list kaydı için kullanılan başlık alanıdır.</li>" +
                    "<li>note: Todo list kaydı için kullanılan başlık altında kullanılan not alanıdır.</li>" +
                    "<li>status: Todo list kaydının durumudur. İki farklı değeri vardır. DONE ve NOT_DONE. Eğer kayıt tamamlanmış ise durum DONE olur. Devam ediyorsa NOT_DONE'da kalır.</li>" +
                    "<li>priority: Todo list kaydının önceliklendirme durumunu belirtir. Üç farklı değeri vardır. LOW, MEDIUM, HIGH.</li>" +
                    "<li>endTime: Todo list kaydının ne zamana kadar DONE durumuna çekilmesi gerektiğinin belirtildiği alandır.</li>" +
                    "<li>userId: Todo list kaydını oluşturulan kullanıcının id'sinin belirtildiği alandır. Kaydın kime ait olduğunu belirtmek için konulmuştur.</li>" +
                    "<li>tags: Todo list kaydına etiketler eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "<li>assignedUsers: Todo list kaydı oluşturulurken diğer kullanıcılara da atanabilir. Hangi kullanıcılara atandığı belirtilen alandır. Liste formundadır.</li>" +
                    "<li>documents: Todo list kaydına dokümanlar eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/")
    public ResponseEntity<List<TodoListGetDto>> getAllTodoList() {
        UserGetDto user = userService.getUserByName(jwtTokenUtil.getCurrentUser().orElse(null));
        return ResponseEntity.ok(todoListService.getAllTodoListByUserId(user.getId()));
    }

    @Operation(summary = "GET TodoList By Id",
            description = "'id' bilgisi verilen todo list kaydının bilgilerini döndüren servistir." +
                    "<br>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen todolist kayıt id'sidir.</li>" +
                    "<li>title: Todo list kaydı için kullanılan başlık alanıdır.</li>" +
                    "<li>note: Todo list kaydı için kullanılan başlık altında kullanılan not alanıdır.</li>" +
                    "<li>status: Todo list kaydının durumudur. İki farklı değeri vardır. DONE ve NOT_DONE. Eğer kayıt tamamlanmış ise durum DONE olur. Devam ediyorsa NOT_DONE'da kalır.</li>" +
                    "<li>priority: Todo list kaydının önceliklendirme durumunu belirtir. Üç farklı değeri vardır. LOW, MEDIUM, HIGH.</li>" +
                    "<li>endTime: Todo list kaydının ne zamana kadar DONE durumuna çekilmesi gerektiğinin belirtildiği alandır.</li>" +
                    "<li>userId: Todo list kaydını oluşturulan kullanıcının id'sinin belirtildiği alandır. Kaydın kime ait olduğunu belirtmek için konulmuştur.</li>" +
                    "<li>tags: Todo list kaydına etiketler eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "<li>assignedUsers: Todo list kaydı oluşturulurken diğer kullanıcılara da atanabilir. Hangi kullanıcılara atandığı belirtilen alandır. Liste formundadır.</li>" +
                    "<li>documents: Todo list kaydına dokümanlar eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<TodoListGetDto> getTodoListItemById(@PathVariable("id") String id) {
        return ResponseEntity.ok(todoListService.getTodoListById(id));
    }

    @Operation(summary = "ADD Todo List Item",
            description = "Todo list kaydı eklenemede kullanılan servistir. Eklenen kaydın 'id' bilgisini döndürür. " +
                    "<br>" +
                    "__Doküman gönderilirken dikkat edilmesi gereken durum !__" +
                    "<ul><li>```documents parametresi altında bulunan data parametresine dokümanın base64 formatı gönderilmelidir.```</li></ul>" +
                    "__Service Request Parametreleri__" +
                    "<ul>" +
                    "<li>title: Todo list kaydı için kullanılan başlık alanıdır. Zorunlu alandır.</li>" +
                    "<li>note: Todo list kaydı için kullanılan başlık altında kullanılan not alanıdır. Zorunlu alan değildir.</li>" +
                    "<li>priority: Todo list kaydının önceliklendirme durumunu belirtir. Üç farklı değeri vardır. LOW, MEDIUM, HIGH. Zorunlu alan değildir.</li>" +
                    "<li>endTime: Todo list kaydının ne zamana kadar DONE durumuna çekilmesi gerektiğinin belirtildiği alandır. Zorunlu alan değildir.</li>" +
                    "<li>tags: Todo list kaydına etiketler eklemek için kullanılan alandır. Liste formundadır. Zorunlu alan değildir.</li>" +
                    "<li>assignedUsers: Todo list kaydı oluşturulurken diğer kullanıcılara da atanabilir. Hangi kullanıcılara atandığı belirtilen alandır. Liste formundadır. Zorunlu alan değildir.</li>" +
                    "<li>documents: Todo list kaydına dokümanlar eklemek için kullanılan alandır. Liste formundadır. Zorunlu alan değildir.</li>" +
                    "</ul>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen todo list kayıt id'sidir.</li>" +
                    "</ul>")
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
                    "<br>" +
                    "__Doküman gönderilirken dikkat edilmesi gereken durum !__" +
                    "<ul><li>```documents parametresi altında bulunan data parametresine dokümanın base64 formatı gönderilmelidir.```</li></ul>" +
                    "__Service Request Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen todolist kayıt id'sidir. Zorunlu alandır.</li>" +
                    "<li>title: Todo list kaydı için kullanılan başlık alanıdır. Zorunlu alandır.</li>" +
                    "<li>note: Todo list kaydı için kullanılan başlık altında kullanılan not alanıdır. Zorunlu alan değildir.</li>" +
                    "<li>priority: Todo list kaydının önceliklendirme durumunu belirtir. Üç farklı değeri vardır. LOW, MEDIUM, HIGH. Zorunlu alan değildir.</li>" +
                    "<li>endTime: Todo list kaydının ne zamana kadar DONE durumuna çekilmesi gerektiğinin belirtildiği alandır. Zorunlu alan değildir.</li>" +
                    "<li>tags: Todo list kaydına etiketler eklemek için kullanılan alandır. Liste formundadır. Zorunlu alan değildir.</li>" +
                    "<li>assignedUsers: Todo list kaydı oluşturulurken diğer kullanıcılara da atanabilir. Hangi kullanıcılara atandığı belirtilen alandır. Liste formundadır. Zorunlu alan değildir.</li>" +
                    "<li>documents: Todo list kaydına dokümanlar eklemek için kullanılan alandır. Liste formundadır. Zorunlu alan değildir.</li>" +
                    "</ul>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen todolist kayıt id'sidir.</li>" +
                    "<li>title: Todo list kaydı için kullanılan başlık alanıdır.</li>" +
                    "<li>note: Todo list kaydı için kullanılan başlık altında kullanılan not alanıdır.</li>" +
                    "<li>status: Todo list kaydının durumudur. İki farklı değeri vardır. DONE ve NOT_DONE. Eğer kayıt tamamlanmış ise durum DONE olur. Devam ediyorsa NOT_DONE'da kalır.</li>" +
                    "<li>priority: Todo list kaydının önceliklendirme durumunu belirtir. Üç farklı değeri vardır. LOW, MEDIUM, HIGH.</li>" +
                    "<li>endTime: Todo list kaydının ne zamana kadar DONE durumuna çekilmesi gerektiğinin belirtildiği alandır.</li>" +
                    "<li>userId: Todo list kaydını oluşturulan kullanıcının id'sinin belirtildiği alandır. Kaydın kime ait olduğunu belirtmek için konulmuştur.</li>" +
                    "<li>tags: Todo list kaydına etiketler eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "<li>assignedUsers: Todo list kaydı oluşturulurken diğer kullanıcılara da atanabilir. Hangi kullanıcılara atandığı belirtilen alandır. Liste formundadır.</li>" +
                    "<li>documents: Todo list kaydına dokümanlar eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "</ul>")
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

    @Operation(summary = "DONE Todo List Item",
            description = "Varolan todo list kaydını tamamlandı durumuna çekmek için kullanılan servistir." +
                    "<br>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen todolist kayıt id'sidir.</li>" +
                    "<li>title: Todo list kaydı için kullanılan başlık alanıdır.</li>" +
                    "<li>note: Todo list kaydı için kullanılan başlık altında kullanılan not alanıdır.</li>" +
                    "<li>status: Todo list kaydının durumudur. İki farklı değeri vardır. DONE ve NOT_DONE. Eğer kayıt tamamlanmış ise durum DONE olur. Devam ediyorsa NOT_DONE'da kalır.</li>" +
                    "<li>priority: Todo list kaydının önceliklendirme durumunu belirtir. Üç farklı değeri vardır. LOW, MEDIUM, HIGH.</li>" +
                    "<li>endTime: Todo list kaydının ne zamana kadar DONE durumuna çekilmesi gerektiğinin belirtildiği alandır.</li>" +
                    "<li>userId: Todo list kaydını oluşturulan kullanıcının id'sinin belirtildiği alandır. Kaydın kime ait olduğunu belirtmek için konulmuştur.</li>" +
                    "<li>tags: Todo list kaydına etiketler eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "<li>assignedUsers: Todo list kaydı oluşturulurken diğer kullanıcılara da atanabilir. Hangi kullanıcılara atandığı belirtilen alandır. Liste formundadır.</li>" +
                    "<li>documents: Todo list kaydına dokümanlar eklemek için kullanılan alandır. Liste formundadır.</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<TodoListGetDto> doneTodoListItem(@PathVariable("id") String id) {
        return ResponseEntity.ok(todoListService.doneTodoListItem(id));
    }

    @Operation(summary = "GET Todo List Item Priority List",
            description = "Tüm todo list item priority kayıt listesini döndüren servistir. " +
                    "Bu servisten gelen kayıtlardan herhangi biri kullanılarak todo list kaydı yapılırken 'todoListItemPriority' " +
                    "parametre değeri doldurulabilir." +
                    "<br>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Todo list item priority enum'ının value değeridir.</li>" +
                    "<li>name: Todo list item priority enum'ının label değeridir.</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/item-priority")
    public ResponseEntity<List<TodoListItemPriorityGetDto>> getTodoListItemPriorityList() {
        return ResponseEntity.ok(todoListService.getTodoListItemPriorityList());
    }
}
