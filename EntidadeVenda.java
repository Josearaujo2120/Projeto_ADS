package com.example.minhaaplicacao;

// Entidade Venda
@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;
    private BigDecimal valorTotal;

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ProdutoVendido> produtosVendidos;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<LancamentoContasReceber> lancamentosContasReceber;

    // getters e setters
}

// Entidade ProdutoVendido
@Entity
public class ProdutoVendido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Venda venda;

    @ManyToOne
    private Produto produto;

    private int quantidade;

    // getters e setters
}

// Entidade LancamentoContasReceber
@Entity
public class LancamentoContasReceber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataLancamento;
    private BigDecimal valor;

    @ManyToOne
    private Venda venda;

    // getters e setters
}

// Serviço de Cadastro de Vendas
@Service
public class CadastroVendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Venda finalizarVenda(Venda venda, List<ProdutoVendido> produtosVendidos) {
        venda.setData(LocalDateTime.now());
        venda.setProdutosVendidos(produtosVendidos);
        calcularValorTotal(venda);
        vendaRepository.save(venda);

        // Realizar a atualização de estoque
        atualizarEstoque(produtosVendidos);

        // Geração de lançamentos no contas a receber
        gerarLancamentosContasReceber(venda);

        return venda;
    }

    private void calcularValorTotal(Venda venda) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ProdutoVendido produtoVendido : venda.getProdutosVendidos()) {
            valorTotal = valorTotal.add(produtoVendido.getProduto().getValorCompra().multiply(BigDecimal.valueOf(produtoVendido.getQuantidade())));
        }
        venda.setValorTotal(valorTotal);
    }

    private void atualizarEstoque(List<ProdutoVendido> produtosVendidos) {
        for (ProdutoVendido produtoVendido : produtosVendidos) {
            Produto produto = produtoVendido.getProduto();
            int quantidadeVendida = produtoVendido.getQuantidade();
            int estoqueAtual = produto.getEstoque();
            produto.setEstoque(estoqueAtual - quantidadeVendida);
            produtoRepository.save(produto);
        }
    }

    private void gerarLancamentosContasReceber(Venda venda) {
        // Lógica para geração de lançamentos no contas a receber
        // Aqui você deve adicionar a lógica necessária para criar os lançamentos de contas a receber,
        // como salvar em uma tabela de contas a receber no banco de dados, por exemplo.
    }
}

// Controlador para Venda
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private CadastroVendaService cadastroVendaService;

    @PostMapping("/finalizar")
    public ResponseEntity<Venda> finalizarVenda(@RequestBody VendaRequest vendaRequest) {
        // Transformar a requisição em uma entidade Venda
        Venda venda = transformarVendaRequestEmVenda(vendaRequest);

        // Chamar o serviço de Cadastro de Vendas
        Venda vendaFinalizada = cadastroVendaService.finalizarVenda(venda, venda.getProdutosVendidos());

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaFinalizada);
    }

    private Venda transformarVendaRequestEmVenda(VendaRequest vendaRequest) {
        // Implemente a lógica para transformar a requisição em uma entidade Venda
        // Aqui você deve mapear os dados da requisição para a entidade Venda e os produtos vendidos.
    }
}
