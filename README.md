# Projeto Final - Unidade Curricular de Sistemas Distribuídos e Mobile  

## **Descrição do Projeto**  
A aplicação consiste em um sistema de gerenciamento de **clientes**, **vendedores**, **compras**, **produtos**, **supervisores** e **relatórios estatísticos**, utilizando tecnologias modernas para criar uma arquitetura distribuída e funcional.

---

## **Tecnologias Utilizadas**  

- **Java**  
- **Maven**  
- **MySQL**  
- **Spring Framework**  
  - Spring Boot  
  - Spring Data JPA  
  - Spring Security  
- **Docker**  

---

## **Como Iniciar a Aplicação**  

1. **Configuração do Banco de Dados:**  
   - Crie um banco de dados no MySQL chamado `db_venda` na porta `3306`, no `localhost`.  
   - Configure o usuário e a senha no arquivo:  
     ```plaintext
     src/main/resources/application.properties
     ```

2. **Preparação dos Scripts Shell:**  
   - Torne os scripts executáveis utilizando o comando:  
     ```bash
     chmod +x start.sh
     ```

3. **Inicialização da Aplicação:**  
   - Execute a aplicação com o comando:  
     ```bash
     ./start.sh
     ```  
   - Este script compila o projeto e sobe os containers Docker automaticamente.

---

## **Detalhes Importantes**  

- **Cadastro Inicial Automático:**  
  - Quando o projeto é iniciado, **produtos**, **vendedores** e **clientes** são cadastrados automaticamente no banco de dados.  

- **Cadastro de Vendas com o Postman:**  
  - Para adicionar vendas e gerar relatórios mais completos, utilize o Postman para acessar o endpoint:  
    ```http
    POST http://localhost:8080/sale/register
    ```

- **Relatórios:**  
  - Os relatórios gerados pela aplicação podem ser acessados na pasta **Documents** do projeto.  
  - Link para os documentos de relatórios:  
    [Relatórios](https://github.com/MarcusViniLima/Api_Sales_Reports/tree/main/documents)

---

## **Documentação de Endpoints**  

A documentação completa dos endpoints da aplicação está disponível no **Postman** através do seguinte link:  
[Documentação dos Endpoints](https://lively-comet-604522.postman.co/workspace/Inventory-Management-System~a0b87526-f639-4132-989f-da52ae79793c/collection/22798399-155997e4-a15f-4a1e-94b5-b6fe7f3c81fb?action=share&creator=22798399&active-environment=39944084-c207f625-9024-47e1-8310-427f47438d9c).  

---

Este projeto representa o aprendizado e aplicação prática de conceitos fundamentais da unidade curricular, explorando tecnologias modernas para a criação de sistemas distribuídos robustos e eficazes.
