package tr.com.hepsiemlak.todolist.application.auth.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserCreateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserGetDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.dto.UserUpdateDto;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Services (Kullanıcı için CRUD işlemlerin yapılabileceği servis grubudur)")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "GET User List",
            description = "Tüm kullanıcıların listesini döndüren servistir." +
                    "<br>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen kullanıcı id'sidir.</li>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>password: Kullanıcı Şifresi</li>" +
                    "<li>active: Kullanıcı durumunu belirtir. Eğer değeri 'false' ise kullanıcı pasife çekilmiştir ve sisteme giriş yapamaz. Yani accessToken üretilmez.</li>" +
                    "<li>name: Kullanıcı Adı</li>" +
                    "<li>surname: Kullanıcı Soyadı</li>" +
                    "<li>gsm: Kullanıcı Gsm</li>" +
                    "<li>email: Kullanıcı Email</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/")
    public ResponseEntity<List<UserGetDto>> getAllUserList() {
        return ResponseEntity.ok(userService.getAllUserList());
    }

    @Operation(summary = "GET User By Id",
            description = "'id' bilgisi verilen kullanıcının bilgilerini döndüren servistir." +
                    "<br>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen kullanıcı id'sidir.</li>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>password: Kullanıcı Şifresi</li>" +
                    "<li>active: Kullanıcı durumunu belirtir. Eğer değeri 'false' ise kullanıcı pasife çekilmiştir ve sisteme giriş yapamaz. Yani accessToken üretilmez.</li>" +
                    "<li>name: Kullanıcı Adı</li>" +
                    "<li>surname: Kullanıcı Soyadı</li>" +
                    "<li>gsm: Kullanıcı Gsm</li>" +
                    "<li>email: Kullanıcı Email</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "ADD User",
            description = "Kullanıcı eklenemede kullanılan servistir. Eklenen kullanıcının 'id' bilgisini döndürür." +
                    "<br>" +
                    "__Service Request Parametreleri__" +
                    "<ul>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>password: Kullanıcı Şifresi</li>" +
                    "<li>active: Kullanıcı durumunu belirtir. Eğer değeri 'false' ise kullanıcı pasife çekilmiştir ve sisteme giriş yapamaz. Yani accessToken üretilmez.</li>" +
                    "<li>name: Kullanıcı Adı</li>" +
                    "<li>surname: Kullanıcı Soyadı</li>" +
                    "<li>gsm: Kullanıcı Gsm</li>" +
                    "<li>email: Kullanıcı Email</li>" +
                    "</ul>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen kullanıcı id'sidir.</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserCreateDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDto));
    }

    @Operation(summary = "UPDATE User",
            description = "Varolan kullanıcının bilgilerini güncellemek için kullanılan servistir. Güncellenen kullanıcının bilgilerini döndürür." +
                    "<br>" +
                    "__Service Request Parametreleri__" +
                    "<ul>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>password: Kullanıcı Şifresi</li>" +
                    "<li>active: Kullanıcı durumunu belirtir. Eğer değeri 'false' ise kullanıcı pasife çekilmiştir ve sisteme giriş yapamaz. Yani accessToken üretilmez.</li>" +
                    "<li>name: Kullanıcı Adı</li>" +
                    "<li>surname: Kullanıcı Soyadı</li>" +
                    "<li>gsm: Kullanıcı Gsm</li>" +
                    "<li>email: Kullanıcı Email</li>" +
                    "</ul>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>id: Kaydedilen kullanıcı id'sidir.</li>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>password: Kullanıcı Şifresi</li>" +
                    "<li>active: Kullanıcı durumunu belirtir. Eğer değeri 'false' ise kullanıcı pasife çekilmiştir ve sisteme giriş yapamaz. Yani accessToken üretilmez.</li>" +
                    "<li>name: Kullanıcı Adı</li>" +
                    "<li>surname: Kullanıcı Soyadı</li>" +
                    "<li>gsm: Kullanıcı Gsm</li>" +
                    "<li>email: Kullanıcı Email</li>" +
                    "</ul>")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/")
    public ResponseEntity<UserGetDto> updateUser(@Valid @RequestBody UserUpdateDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Operation(summary = "DELETE User", description = "Varolan kullanıcı silmek için kullanılan servistir.")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
