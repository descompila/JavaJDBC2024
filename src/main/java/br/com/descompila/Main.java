package br.com.descompila;

import java.io.Console;
import java.util.List;

public class Main {

    static Console console = System.console();
    public static void main(String[] args) {
        
        int opcao;
        do {
            exibirMenu();
            opcao = Integer.parseInt(console.readLine());
            switch (opcao) {
                case 0 -> salvarProduto();
                case 1 -> buscarTodosProdutos();
                case 2 -> buscarProdutoPorId();
                case 3 -> atualizarProduto();
                case 4 -> excluirProduto();
                case 5 -> System.exit(0);
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
    }

    private static void exibirMenu() {
        System.out.println("\n### Menu de Operações ###");
        System.out.println("0. Salvar novo produto");
        System.out.println("1. Buscar todos produtos");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Atualizar produto");
        System.out.println("4. Excluir produto");
        System.out.println("5. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void salvarProduto() {
        System.out.println("\n### Criar Novo Produto ###");

        System.out.println("Nome:");
        String nome = console.readLine();
        System.out.println("Quantidade:");
        int quantidade = Integer.parseInt(console.readLine());
        System.out.println("Valor:");
        Double valor = Double.parseDouble(console.readLine());

        Produto produto = new Produto(nome, quantidade, valor);
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            produtoDAO.salvar(produto);
            System.out.println("Produto criado com sucesso!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        
    }

    private static void buscarTodosProdutos() {
        System.out.println("\n### Buscar Todos ###");

        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            List<Produto> produtos = produtoDAO.buscarTodos();

            if(produtos != null){
                System.out.println("Lista de Produto:");

                for (Produto produto : produtos) {
                    System.out.println("Nome: "+produto.nome());
                }

            } else {
                System.out.println("Produto não encontrado");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void buscarProdutoPorId() {
        System.out.println("\n### Buscar Produto por ID ###");

        System.out.println("Digite o ID do produto:");
        Long id = Long.parseLong(console.readLine());

        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            Produto produto = produtoDAO.buscarPorId(id);

            if(produto != null){
                System.out.println("Produto encontrado:");
                System.out.println(produto.nome());
            } else {
                System.out.println("Produto não encontrado");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void atualizarProduto() {
        System.out.println("\n### Atualizar Produto ###");

        System.out.println("Digite o ID do produto que deseja atualizar:");
        Long id = Long.parseLong(console.readLine());

        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            Produto produtoExistente = produtoDAO.buscarPorId(id);

            if(produtoExistente != null){
                
                System.out.println("Novo nome (atual: "+produtoExistente.nome()+"): ");
                String nome = console.readLine();
                System.out.println("Nova quantidade (atual: "+produtoExistente.quantidade()+"): ");
                int quantidade = Integer.parseInt(console.readLine());
                System.out.println("Novo valor (atual: "+produtoExistente.valor()+"): ");
                Double valor = Double.parseDouble(console.readLine());

                Produto produtoAtualizado = new Produto(id, nome, quantidade, valor);

                try {
                    produtoDAO.atualizar(produtoAtualizado);
                    System.out.println("Produto atualizado com sucesso!");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("Produto não encontrado");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void excluirProduto() {
        System.out.println("\n### Excluir Produto ###");
        
        System.out.println("Digite o ID do produto que deseja excluir:");
        Long id = Long.parseLong(console.readLine());

        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            Produto produtoExistente = produtoDAO.buscarPorId(id);

            if(produtoExistente != null){

                try {
                    produtoDAO.excluir(produtoExistente.id());
                    System.out.println("Produto excluido com sucesso!");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("Produto não encontrado");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}