package jwt.auth.springboot.userauth.dtos;

public record RegisterUserDto(String email, String password, String fullName) {
}
