# Protótipo de um projeto desenvolvido em JAVA, para cadastro de produtos e pedidos.

- Para rodar o projeto, siga as instruções:
1. Criar uma base de dados no banco de dados PostgreSQL, com o nome de db_sale;
2. Ajustar o arquivo application.properties com as configurações do banco de dados criado no item 1.

- Para testar o projeto, foi implementados testes, visando testar de forma completa as funcionalidades.
1. Executar os arquivos ProductControllerTest, SolicitationControllerTest, testam funcionalidade de create, update, list 
   e delete.

2. O arquivo ItemSolicitationControllerTest, além de testar as funcionalidades de create, update, list e delete, contém 
   também um método que possibilidade lançar uma porcentagem de desconto em produtos que não serviços.
   
   - Método (persistItemSolicitationWithDeduction) testa um desconto de 15% em cima do valor total dos produtos.
