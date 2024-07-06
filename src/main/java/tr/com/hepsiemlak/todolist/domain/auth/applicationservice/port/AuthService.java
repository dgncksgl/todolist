package tr.com.hepsiemlak.todolist.domain.auth.applicationservice.port;

import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthResponseDto;

public interface AuthService {

    AuthResponseDto getAuthResponseDto(AuthRequestDto authRequestDto);

    AuthRefreshTokenResponseDto getAuthRefreshTokenResponseDto(String refreshToken);
}
