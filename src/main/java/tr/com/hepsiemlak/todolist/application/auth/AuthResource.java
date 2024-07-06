package tr.com.hepsiemlak.todolist.application.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Sign-in", description = "Sign-in")
    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> singIn(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.getAuthResponseDto(authRequestDto));

    }

    @Operation(summary = "Refresh-Token", description = "Refresh-Token")
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthRefreshTokenResponseDto> refreshToken(@RequestBody @Valid AuthRefreshTokenRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.getAuthRefreshTokenResponseDto(dto.refreshToken()));
    }
}
