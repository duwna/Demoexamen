package ui;

import database.Database;
import database.entities.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClientsController extends JFrame {

    private static final int PREF_WIDTH = 1280;
    private static final int PREF_HEIGHT = 720;
    private static final String TITLE = "Клиенты";

    private JPanel panel;
    private JTable table;
    private JTextField tfName;
    private JTextField tfNumber;
    private JRadioButton rbAll;
    private JRadioButton rbMale;
    private JRadioButton rbFemale;
    private JButton btnDelete;
    private JCheckBox cbCurrentMonth;
    JButton btnEdit;
    JButton btnAdd;
    private JButton btnPreviousPage;
    private JButton btnNextPage;
    private JLabel labelCount;

    private List<Client> clients;
    private int currentPage;
    private int rowsCount = 20;

    private final Database db = Database.getInstance();

    private final String[] columnNames = {
            "ID",
            "FirstName",
            "LastName",
            "Patronymic",
            "Birthday",
            "RegistrationDate",
            "Email",
            "Phone",
            "GenderCode",
            "PhotoPath",
    };

    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

    public ClientsController() {

        setContentPane(panel);
        setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - PREF_WIDTH / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - PREF_HEIGHT / 2);

        setTitle(TITLE);
        initRadioButtons();
        initTextFields();
        initButtons();
        resetTable();
        table.setModel(tableModel);


        try {
            setIconImage(ImageIO.read(this.getClass().getResourceAsStream("res/service_logo.png")));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        btnDelete.addActionListener(e -> deleteClient());
        btnEdit.addActionListener(e -> {

        });
        btnAdd.addActionListener(e -> {
            UpdateClientController updateClientController = new UpdateClientController(this, null);
            updateClientController.pack();
            updateClientController.setVisible(true);
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Не выбрана строка", "Внимание", JOptionPane.WARNING_MESSAGE);
                return;
            }

            UpdateClientController updateClientController = new UpdateClientController(this, clients.get(selectedRow));
            updateClientController.pack();
            updateClientController.setVisible(true);
        });

        btnNextPage.addActionListener(e -> {
            currentPage++;
            loadPage();
        });

        btnPreviousPage.addActionListener(e -> {
            currentPage--;
            loadPage();
        });
    }

    private void deleteClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Не выбрана строка", "Внимание", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int input = JOptionPane.showOptionDialog(null, "Точно удалить?", "Удаление",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input != JOptionPane.OK_OPTION) return;
        int id = Integer.parseInt((String) table.getValueAt(selectedRow, 0));
        try {
            db.removeClient(id);
            resetTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initRadioButtons() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbAll);
        buttonGroup.add(rbMale);
        buttonGroup.add(rbFemale);
        rbAll.addActionListener(e -> resetTable());
        rbMale.addActionListener(e -> resetTable());
        rbFemale.addActionListener(e -> resetTable());
        cbCurrentMonth.addActionListener(e -> resetTable());
    }


    private void resetTable() {
        try {
            int gender = 0;
            if (rbMale.isSelected()) gender = 1;
            else if (rbFemale.isSelected()) gender = 2;

            clients = db.getClients(gender, tfName.getText(), tfNumber.getText(), cbCurrentMonth.isSelected());

            currentPage = 0;
            loadPage();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Отсутствует подключение к БД", "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void loadPage() {
        removeAllRows();

        int loadFrom = currentPage * rowsCount;
        int loadTo = loadFrom + rowsCount;

        for (int i = loadFrom; i < loadTo && i < clients.size(); i++) {
            tableModel.addRow(clients.get(i).toArray());
        }

        btnPreviousPage.setEnabled(currentPage != 0);
        btnNextPage.setEnabled(loadTo < clients.size());
        labelCount.setText(loadFrom + "/" + clients.size());
    }

    private void removeAllRows() {
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
    }


    private void initTextFields() {
        tfName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                resetTable();
            }
        });

        tfNumber.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                resetTable();
            }
        });
    }
}
