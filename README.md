# Respostas_Projeto_ADS

1- Metodologia para o Desenvolvimento do Software 
- Utilização da metodologia ágil, como Scrum. Essa escolha permite entregas incrementais, contínuas e flexíveis para ajustar requisitos ao longo do desenvolvimento. A abordagem colaborativa do Scrum se alinha bem com a necessidade de interação constante com os stakeholders, adaptando-se às mudanças e prioridades. 


2- Requisitos Funcionais
- Cadastro de produtos com dados da nota fiscal (código, descrição, código de barras, valor de compra, fornecedor). 
- Lançamento de contas a pagar ao cadastrar nota fiscal. 
- Exibição de produtos disponíveis para venda no site. 
- Adição de produtos ao carrinho sem necessidade de cadastro. 
- Login para finalização de compra. 
- Cadastro de usuários com dados pessoais e possibilidade de múltiplos endereços. 
- Escolha o endereço de entrega durante a finalização da venda. 
- Apresentação de formas de pagamento (cartão de crédito, débito, boleto, pix, crediário). 
- Geração de número único, dados, valor total, produtos e formas de pagamento para cada venda. 
- Atualização de estoque após a conclusão da venda. 
- Acompanhamento do processo de entrega pelos clientes. 
- Retorno de pedidos não entregues após 3 tentativas para retirada na sede. 




# Projeto_ADS

Desenvolvimento de um E-Commerce

Você é um analista de sistemas e foi contratado por uma grande multinacional para desenvolver um sistema de E-commerce. A empresa, de nome BRASILEIRAS, omercializa vários produtos, porém só na forma de venda física. A BRASILEIRAS quer entrar no mercado de vendas online.

A loja BRASILEIRAS vende vários produtos, tais como eletrônicos, eletrodomésticos, cama, mesa, vestuários masculino e feminino, móveis dentre outros. Os produtos são adquiridos de diversos fornecedores. 

Quando os produtos são adquiridos eles devem ser lançados em estoque. Todos os produtos vêm descriminados na nota fiscal com os seguintes dados: código do produto, descrição, código de barras, valor de compra. As notas fiscais tem um número único e data de emissão, dados da empresa fornecedora (razão social, cnpj, etc). Quando uma nota fiscal é lançada, o sistema deve, obrigatoriamente, realizar um lançamento de contas a pagar, com o valor total da nota e com data de pagamento com 30 dias após a data que a nota fiscal foi dada entrada.

No cadastro de produtos deve possuir os dados do produto de acordo com a nota fiscal de entrada, além do estoque, valor de venda e fornecedor.

O sistema deve permitir que qualquer pessoa que acesse o site consiga visualizar os produtos que estão disponíveis a venda, independente de estar cadastrado ou não no site. Deve também permitir que seja adicionado os produtos ao carrinho sem necessidade de cadastro. Quando o visitante for finalizar a venda o sistema deve verificar se existe um usuário válido logado, caso contrário, deve permitir fazer o login em caso de visitantes que já tenha cadastro no site, ou realizar um novo cadastro para visitantes que não tenha cadastro. Não deve ser possível concluir uma venda sem está logado com um usuário válido.

O usuário, no momento do cadastro, deve fornecer os seus dados pessoais, tais como: nome, cpf, email, telefone, e pelo menos um endereço. O sistema deve permitir que um usuário possua mais que um endereço para entrega.

No momento da finalização de uma venda o usuário deve optar por um dos endereços presentes no seu cadastro para entrega. Não será possível alterar o endereço de entrega após a finalização da venda.

Na finalização da venda deve ser apresentado as mais diversas formas de  pagamentos disponíveis, como: cartão de credito, cartão de débito, boleto, pix, crediário etc.

Toda venda deve ter um número único, data, valor total, os produtos e as  formas de pagamento. Deve ser possível selecionar mais que uma forma de  pagamento para uma venda.

Com a venda finalizada, o sistema deve realizar a atualização de estoque dos produtos vendidos.

Cada venda deve gerar, pelo menos um lançamento no contas a receber,  dependendo da forma de pagamento. Caso boleto, pix, cartão de débito o lançamento deve ser único, com data de lançamento do dia da venda e valor total da venda. Caso seja cartão de crédito, crediário etc, o sistema deve considerar a quantidade de vezes que o cliente optou em dividir a compra e fazer os respectivos lançamentos no contas a receber com as datas de acordo com o prazo escolhido.

Após a confirmação da venda a empresa BRASILEIRAS deve entregar os produtos. A empresa BRASILEIRAS possui uma frota de caminhões próprios e cada caminhão sai diariamente com vários produtos dos clientes. Deve ser possível o cliente acompanhar o processo de entrega das mercadorias. No momento da entrega, caso o cliente não esteja em casa, o pedido deve retornar para a empresa e será realizada mais 2 tentativas de entrega. Se não for possível realizar a entrega por ausência do comprador, o pedido retorna e fica disponível para retirada na sede da empresa BRASILEIRAS.
