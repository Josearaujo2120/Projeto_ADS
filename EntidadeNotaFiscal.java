package com.example.minhaaplicacao;

// Entidade NotaFiscal
@Entity
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private LocalDate dataEmissao;

    @ManyToOne
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    // getters e setters
}

// Entidade Produto
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String descricao;
    private String codigoBarras;
    private BigDecimal valorCompra;

    @ManyToOne
    private Fornecedor fornecedor;

    // getters e setters
}

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

// Repositório para NotaFiscal
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal, Long> {
}

// Repositório para Produto
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

// Repositório para Fornecedor
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}

// Serviço de Cadastro de Produtos
@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NotaFiscalRepository notaFiscalRepository;

    public Produto cadastrarProduto(Produto produto, NotaFiscal notaFiscal) {
        // Verificar se a nota fiscal já foi cadastrada (pode ser necessário adicionar essa verificação)
        notaFiscalRepository.save(notaFiscal);

        produto.setFornecedor(notaFiscal.getFornecedor());
        produtoRepository.save(produto);

        // Realizar o lançamento de contas a pagar
        lancarContasPagar(notaFiscal);

        return produto;
    }

    private void lancarContasPagar(NotaFiscal notaFiscal) {
        // Lógica para o lançamento de contas a pagar
        // Aqui você deve adicionar a lógica necessária para criar o lançamento de contas a pagar,
        // como salvar em uma tabela de contas a pagar no banco de dados, por exemplo.
    }
}

// Controlador para Produto
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto,
                                                    @RequestBody NotaFiscal notaFiscal) {
        Produto novoProduto = cadastroProdutoService.cadastrarProduto(produto, notaFiscal);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }
}
