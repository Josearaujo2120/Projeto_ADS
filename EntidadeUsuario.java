package com.example.minhaaplicacao;

// Entidade Usuario
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Endereco> enderecos;

    // getters e setters
}

// Repositório para Usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

// Serviço de Cadastro de Usuários
@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Adicione lógica de validação, por exemplo, verificar se o CPF e o email são únicos
        return usuarioRepository.save(usuario);
    }

    public Usuario obterUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Usuário não encontrado com ID: " + usuarioId);
                });
    }

    public void removerUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}

// Controlador para Usuario
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = cadastroUsuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = cadastroUsuarioService.obterUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerUsuario(@PathVariable Long id) {
        cadastroUsuarioService.removerUsuario(id);
        return ResponseEntity.ok("Usuário removido com sucesso");
    }
}
