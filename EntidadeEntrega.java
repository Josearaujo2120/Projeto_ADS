package com.example.minhaaplicacao;

// Entidade Entrega
@Entity
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataEntrega;
    private String status; // Ex: "Em andamento", "Entregue", "Tentativa 1", "Tentativa 2", "Não entregue"

    @OneToOne
    private Venda venda;

    // getters e setters
}

// Repositório para Entrega
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}

// Serviço de Acompanhamento de Entrega
@Service
public class AcompanhamentoEntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private VendaRepository vendaRepository;

    public String acompanharEntrega(Long vendaId) {
        // Verificar se a venda está associada a uma entrega
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> {
                    return new EntityNotFoundException("Venda não encontrada com ID: " + vendaId);
                });

        Entrega entrega = entregaRepository.findByVendaId(vendaId);

        if (entrega != null) {
            return "Status da entrega: " + entrega.getStatus();
        } else {
            return "Entrega não encontrada para a venda com ID: " + vendaId;
        }
    }
}

// Controlador para Acompanhamento de Entrega
@RestController
@RequestMapping("/api/vendas")
public class AcompanhamentoEntregaController {

    @Autowired
    private AcompanhamentoEntregaService acompanhamentoEntregaService;

    @GetMapping("/{id}/acompanhamento-entrega")
    public ResponseEntity<String> acompanharEntrega(@PathVariable Long id) {
        try {
            // Lógica para recuperar informações de acompanhamento de entrega
            String statusEntrega = acompanhamentoEntregaService.acompanharEntrega(id);
            return ResponseEntity.ok(statusEntrega);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao obter informações de entrega");
        }
    }
}
