package tr.com.hepsiemlak.todolist.domain.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRefreshTokenResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthRequestDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.dto.AuthResponseDto;
import tr.com.hepsiemlak.todolist.domain.auth.applicationservice.port.AuthService;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.RefreshToken;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.dto.RefreshTokenDto;
import tr.com.hepsiemlak.todolist.domain.auth.refreshtoken.applicationservice.port.RefreshTokenRepository;
import tr.com.hepsiemlak.todolist.domain.auth.user.User;
import tr.com.hepsiemlak.todolist.domain.auth.user.UserAuthModel;
import tr.com.hepsiemlak.todolist.domain.auth.user.applicationservice.port.UserRepository;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotActiveException;
import tr.com.hepsiemlak.todolist.shared.exception.type.NotFoundException;
import tr.com.hepsiemlak.todolist.shared.exception.type.TokenRefreshException;
import tr.com.hepsiemlak.todolist.shared.util.JwtTokenUtil;

import java.time.Instant;

@Service
public class AuthDomainService implements AuthService {


    @Value("${todolist.jwt.refresh-expiration-time}")
    Long refreshExpirationTime;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public AuthDomainService(AuthenticationManager authenticationManager,
                             JwtTokenUtil jwtTokenUtil,
                             RefreshTokenRepository refreshTokenRepository,
                             UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDto authenticateAndGenerateToken(AuthRequestDto authRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.username(), authRequestDto.password())
        );

        UserAuthModel userDetails = (UserAuthModel) authentication.getPrincipal();

        if (Boolean.FALSE.equals(userDetails.isEnabled())) {
            throw new NotActiveException(User.class.getSimpleName());
        }

        String refreshToken = refreshTokenRepository.save(
                RefreshTokenDto.convertToRefreshToken(userDetails.getUserId(), refreshExpirationTime)
        ).getToken();

        return AuthResponseDto.convertToAuthDto(
                userDetails,
                jwtTokenUtil.generateToken(userDetails.getUsername()),
                refreshToken
        );
    }

    @Override
    public AuthRefreshTokenResponseDto regenerateAccessTokenByRefreshToken(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(RefreshToken.class.getSimpleName()));

        if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.deleteByUserId(token.getUserId());
            throw new TokenRefreshException();
        }

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName()));

        return AuthRefreshTokenResponseDto.convertToAuthRefreshTokenResponseDto(
                jwtTokenUtil.generateToken(user.getUsername()),
                token.getToken()
        );
    }
}
