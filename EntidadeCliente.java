package com.example.minhaaplicacao;

// Entidade Cliente
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Endereco> enderecos;

    // getters e setters
}

// Entidade Endereco
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cidade;
    private String estado;
    private String cep;

    @ManyToOne
    private Cliente cliente;

    // getters e setters
}

// Repositório para Cliente
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

// Repositório para Endereco
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}

// Serviço de Manutenção de Clientes
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        // Adicione lógica de validação, por exemplo, verificar se o CPF é único
        return clienteRepository.save(cliente);
    }

    public Cliente obterClientePorId(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Cliente não encontrado com ID: " + clienteId);
                });
    }

    public Cliente adicionarEndereco(Long clienteId, Endereco endereco) {
        Cliente cliente = obterClientePorId(clienteId);
        endereco.setCliente(cliente);
        cliente.getEnderecos().add(endereco);
        enderecoRepository.save(endereco);
        return clienteRepository.save(cliente);
    }

    public void removerCliente(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}

// Controlador para Cliente
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.obterClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Cliente> adicionarEndereco(@PathVariable Long id,
                                                     @RequestBody Endereco endereco) {
        Cliente cliente = clienteService.adicionarEndereco(id, endereco);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerCliente(@PathVariable Long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.ok("Cliente removido com sucesso");
    }
}
