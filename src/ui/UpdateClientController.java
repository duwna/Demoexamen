package ui;

import database.entities.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class UpdateClientController extends JFrame {

    private static final int PREF_WIDTH = 200;
    private static final int PREF_HEIGHT = 600;

    private JLabel labelId;
    private JTextField tfName;
    private JTextField tfNumber;
    private JTextField tfEmail;
    private JPanel panel;
    private JButton btnOk;
    private JLabel labelImage;

    public UpdateClientController(ClientsController parent, Client client) {
        setContentPane(panel);
        setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - PREF_WIDTH / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - PREF_HEIGHT / 2);

        bindClient(client);

        parent.btnAdd.setEnabled(false);
        parent.btnEdit.setEnabled(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parent.btnAdd.setEnabled(true);
                parent.btnEdit.setEnabled(true);
                super.windowClosing(e);
            }
        });

        btnOk.addActionListener(e -> {
            checkFields();
        });

        labelImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new ImageFilter());
                fileChooser.setAcceptAllFileFilterUsed(false);

                int option = fileChooser.showOpenDialog(panel);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    double mb = file.length() / (1024 * 1024);
                    if (mb > 2) return;
                    System.out.println(mb);
                    try {
                        Files.copy(file.toPath(), Paths.get("Клиенты/" + file.getName()), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    labelImage.setText(file.getName());
                    ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                    labelImage.setIcon(imageIcon);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }

    private void bindClient(Client client) {
        if (client == null) return;
        tfEmail.setText(client.getEmail());
        tfName.setText(client.getFirstName());
        tfNumber.setText(client.getPhone());
        labelId.setText(String.valueOf(client.getId()));

        ImageIcon imageIcon = new ImageIcon(client.getPhotoPath().replace('\\', '/'));
        labelImage.setIcon(imageIcon);
        labelImage.setText(client.getPhotoPath());
    }


    private void checkFields() {
        boolean nameValid = Pattern.compile("^[ \\-A-Za-z]+$").matcher(tfName.getText()).matches();
        boolean phoneValid = Pattern.compile("^[ ()+\\-\\d]+$").matcher(tfNumber.getText()).matches();
        boolean emailValid = Pattern.compile("[^@]+@[^.]+\\..+").matcher(tfEmail.getText()).matches();

        System.out.println("nameValid " + nameValid);
        System.out.println("phoneValid " + phoneValid);
        System.out.println("emailValid " + emailValid + "\n");
    }

    boolean checkDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
