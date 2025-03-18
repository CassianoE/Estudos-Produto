package app.controller;

import app.model.Usuario;
import app.security.JwtUtil;
import app.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(request.username(), request.password());
            return ResponseEntity.ok(Map.of("username", usuario.getUsername(), "message", "Usuário registrado"));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Username já está em uso");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        Optional<Usuario> usuario = usuarioService.buscarPorUsername(request.username());
        if (usuario.isPresent() && passwordEncoder.matches(request.password(), usuario.get().getPassword())) {
            String token = JwtUtil.generateToken(usuario.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }


}
