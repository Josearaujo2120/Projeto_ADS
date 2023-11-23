package com.example.minhaaplicacao;

// Entidade Fornecedor
@Entity
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razaoSocial;
    private String cnpj;

    // getters e setters
}

// Repositório para Fornecedor
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}

// Serviço de Manutenção de Fornecedores
@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> listarFornecedores() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor cadastrarFornecedor(Fornecedor fornecedor) {
        // Adicione lógica de validação, por exemplo, verificar se o CNPJ é único
        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor obterFornecedorPorId(Long fornecedorId) {
        return fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Fornecedor não encontrado com ID: " + fornecedorId);
                });
    }

    public void removerFornecedor(Long fornecedorId) {
        fornecedorRepository.deleteById(fornecedorId);
    }
}

// Controlador para Fornecedor
@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.listarFornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    @PostMapping
    public ResponseEntity<Fornecedor> cadastrarFornecedor(@RequestBody Fornecedor fornecedor) {
        Fornecedor novoFornecedor = fornecedorService.cadastrarFornecedor(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> obterFornecedorPorId(@PathVariable Long id) {
        Fornecedor fornecedor = fornecedorService.obterFornecedorPorId(id);
        return ResponseEntity.ok(fornecedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerFornecedor(@PathVariable Long id) {
        fornecedorService.removerFornecedor(id);
        return ResponseEntity.ok("Fornecedor removido com sucesso");
    }
}
