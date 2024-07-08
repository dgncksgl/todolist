package tr.com.hepsiemlak.todolist.application.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.port.AuthService;

@RestController()
@RequestMapping("/auth")
@Tag(name = "Auth Services")
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Sign-in",
            description = "Kullanıcıların username ve password bilgileri kontrol edilerek, uygunluk durumuna göre token üretilen servistir." +
                    "<br>" +
                    "__Service Request Parametreleri__ " +
                    "<ul>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>password: Kullanıcı Şifresi</li>" +
                    "</ul>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>username: Kullanıcı Giriş Adı</li>" +
                    "<li>accessToken: Kullanıcının sisteme giriş yapabilmesi ve diğer servislere istek atabilmesi için gereklidir.</li>" +
                    "<li>refreshToken: Access Token süresi dolduktan sonra token'ı yenilemek için kullanılır.</li>" +
                    "<li>tokenType: AccessToken tipini belirtir.</li>" +
                    "</ul>")
    @PostMapping("/sign-in")
    @Transactional
    public ResponseEntity<AuthResponseDto> authenticateAndGenerateToken(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.authenticateAndGenerateToken(authRequestDto));

    }

    @Operation(summary = "Refresh-Token",
            description = "Access token süresi dolan kullancıların refresh token ile tekrar access token üretilen servistir. Refresh token süreside kontrol edilmektedir." +
                    "<br>" +
                    "__Service Request Parametreleri__ " +
                    "<ul>" +
                    "<li>refreshToken: Access Token süresi dolduktan sonra token'ı yenilemek için kullanılır.</li>" +
                    "</ul>" +
                    "__Service Response Parametreleri__" +
                    "<ul>" +
                    "<li>accessToken: Kullanıcının sisteme giriş yapabilmesi ve diğer servislere istek atabilmesi için gereklidir. refreshToken kontrolü yapılarak yeniden üretilmiştir.</li>" +
                    "<li>refreshToken: Access Token süresi dolduktan sonra token'ı yenilemek için kullanılır.</li>" +
                    "<li>tokenType: AccessToken tipini belirtir.</li>" +
                    "</ul>")
    @PostMapping("/refresh-token")
    @Transactional
    public ResponseEntity<AuthRefreshTokenResponseDto> regenerateAccessTokenByRefreshToken(
            @RequestBody @Valid AuthRefreshTokenRequestDto dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.regenerateAccessTokenByRefreshToken(dto.refreshToken()));
    }
}
