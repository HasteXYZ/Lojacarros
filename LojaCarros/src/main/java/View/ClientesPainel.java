
package View;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connection.ClientesDAO;
import Controller.ClientesControl;
import Model.Clientes;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientesPainel extends JPanel {
    private JButton cadastrar, apagar, editar;
    private JTextField cliNomeField, cliEnderecoField, cliTelefoneField, clicpfField, cliIdadeField;
    private List<Clientes> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    public ClientesPainel() {
        super();
        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro de Clientes"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nome"));
        cliNomeField = new JTextField(20);
        inputPanel.add(cliNomeField);
        inputPanel.add(new JLabel("Endereço"));
        cliEnderecoField = new JTextField(20);
        inputPanel.add(cliEnderecoField);
        inputPanel.add(new JLabel("Telefone"));
        cliTelefoneField = new JTextField(20);
        inputPanel.add(cliTelefoneField);
        inputPanel.add(new JLabel("CPF"));
        clicpfField = new JTextField(20);
        inputPanel.add(clicpfField);
        inputPanel.add(new JLabel("Idade"));
        cliIdadeField = new JTextField(20);
        inputPanel.add(cliIdadeField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        add(botoes);
        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "Endereço", "Telefone", "CPF", "Idade" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        // cria banco de dados
        new ClientesDAO().criaTabelaCL();
        // executar o método de atualizar tabela
        atualizarTabela();

        // tratamento para click do mouse na tabela
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    cliNomeField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    cliEnderecoField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    cliTelefoneField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    clicpfField.setText((String) table.getValueAt(linhaSelecionada, 3));
                    cliIdadeField.setText((String) table.getValueAt(linhaSelecionada, 4));
                }
            }
        });

        ClientesControl opClientesControl = new ClientesControl(clientes, tableModel, table);

        // tratamento para botão cadastrar
        cadastrar.addActionListener(e -> {
            opClientesControl.cadastrar(cliNomeField.getText(), cliEnderecoField.getText(),
                    cliTelefoneField.getText(), clicpfField.getText(),
                    cliIdadeField.getText());
            cliNomeField.setText("");
            cliEnderecoField.setText("");
            cliTelefoneField.setText("");
            clicpfField.setText("");
            cliIdadeField.setText("");
        });

        // tratamento do botão editar
        editar.addActionListener(e -> {
            opClientesControl.atualizar(cliNomeField.getText(), cliEnderecoField.getText(),
                    (cliTelefoneField.getText()), clicpfField.getText(),
                    (cliIdadeField.getText()));
            cliNomeField.setText("");
            cliEnderecoField.setText("");
            cliTelefoneField.setText("");
            clicpfField.setText("");
            cliIdadeField.setText("");
        });

        // tratamento do botão apagar
        apagar.addActionListener(e -> {
            opClientesControl.apagar(clicpfField.getText());
            cliNomeField.setText("");
            cliEnderecoField.setText("");
            cliTelefoneField.setText("");
            clicpfField.setText("");
            cliIdadeField.setText("");
        });

    }

    // métodos (atualizar tabela)
    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarTodosCL();
        // Obtém os carros atualizados do banco de dados
        for (Clientes cliente : clientes) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getEndereco(),

                    cliente.getTelefone(), cliente.getCpf(), cliente.getIdade() });
        }
    }

}
