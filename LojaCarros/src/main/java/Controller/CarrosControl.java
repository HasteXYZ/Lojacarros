package Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.CarrosDAO;
import Model.Carros;

public class CarrosControl {
    // atributos
    private List<Carros> carros;
    private DefaultTableModel tableModel;
    private JTable table;

    // construtor
    public CarrosControl(List<Carros> carros, DefaultTableModel tableModel, JTable table) {
        this.carros = carros;
        this.tableModel = tableModel;
        this.table = table;
    }

    // métodos do modelo CRUD
    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        carros = new CarrosDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Carros carro : carros) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { carro.getMarca(), carro.getModelo(),

                    carro.getAno(), carro.getPlaca(), carro.getValor() });
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String marca, String modelo, String anoStr, String placa, String valorStr) {
        try {
            // Verifica se a marca é nula ou vazia
            if (marca == null || marca.isEmpty()) {
                throw new IllegalArgumentException("Preencha o Campo: Marca");
            }

            // Verifica se o modelo é nulo ou vazio
            if (modelo == null || modelo.isEmpty()) {
                throw new IllegalArgumentException("Preencha o Campo: Modelo");
            }

            // Verifica se o ano não é vazio e é um número
            if (anoStr == null || anoStr.isEmpty()) {
                throw new IllegalArgumentException("Preencha o Campo: Ano");
            }
            int ano = Integer.parseInt(anoStr);

            // Verifica se o ano não é inferior a 1950
            if (ano < 1950) {
                throw new IllegalArgumentException("O ano não pode ser inferior a 1950");
            }

            // Verifica se a placa não é vazia
            if (placa == null || placa.isEmpty()) {
                throw new IllegalArgumentException("Preencha o Campo: Placa");
            }

            // Verifica se o valor não é vazio e é um número
            if (valorStr == null || valorStr.isEmpty()) {
                throw new IllegalArgumentException("Preencha o Campo: Valor");
            }
            int valor = Integer.parseInt(valorStr);

            // Chama o método de cadastro no banco de dados
            new CarrosDAO().cadastrar(marca, modelo, ano, placa, valor);

        } catch (NumberFormatException e) {
            // Tratamento de exceção para conversão de String para int
            JOptionPane.showMessageDialog(null, "Erro durante o cadastro: Ano e Valor devem ser números.");
        } catch (IllegalArgumentException e) {
            // Tratamento de exceção para outras validações
            JOptionPane.showMessageDialog(null, "Erro durante o cadastro: " + e.getMessage());
        } catch (Exception e) {
            // Tratamento de exceção, se necessário
            JOptionPane.showMessageDialog(null, "Erro durante o cadastro: " + e.getMessage());
        } finally {
            // Atualiza a tabela de exibição após o cadastro, mesmo que haja uma exceção
            atualizarTabela();
        }
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String marca, String modelo, int ano, String placa, int valor) {
        new CarrosDAO().atualizar(marca, modelo, ano, placa, valor);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String placa) {
        new CarrosDAO().apagar(placa);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }
}