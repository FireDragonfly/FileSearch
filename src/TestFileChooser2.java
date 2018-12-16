import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

public class TestFileChooser2 {

    public static void main(String[] args) {
        new TestFileChooser2();
    }

    public TestFileChooser2() {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new MainPane());
                frame.setSize(800, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });

    }

    protected class MainPane extends JPanel {

        private JFileChooser fileChooser;
        private JPanel filePane;
        private JTextField fileField;

        public MainPane() {

            setLayout(new BorderLayout());

            fileChooser = new JFileChooser();
            fileChooser.setApproveButtonText("delete");
            removeButtons(fileChooser);

            JList list = findFirstChildren(fileChooser, JList.class);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        File file = (File)((JList)e.getSource()).getSelectedValue();
                        if (file != null) {
                            setFile(file);
                        }
                    }
                }
            });

//            fileChooser.addPropertyChangeListener(new PropertyChangeListener() {
//                @Override
//                public void propertyChange(PropertyChangeEvent evt) {
//                    System.out.println(evt.getPropertyName());
//                    if (evt.getPropertyName().equals("SelectedFileChangedProperty")) {
//                        File file = fileChooser.getSelectedFile();
//                        if (file != null) {
//                            setFile(file);
//                        }
//                    }
//                }
//            });

            add(fileChooser, BorderLayout.WEST);

            filePane = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            fileField = new JTextField(10);
            filePane.add(fileField, gbc);

            add(filePane);

        }

        protected void setFile(File file) {

            fileField.setText(file.getPath());

        }

        protected void removeButtons(Container container) {
            for (Component child : container.getComponents()) {

                if (child instanceof JButton) {
                    JButton btn = (JButton) child;
                    if (btn.getText() != null && (btn.getText().equals(fileChooser.getApproveButtonText()) || btn.getText().equals("Cancel"))) {
                        container.remove(child);
                    }
                } else if (child instanceof Container) {
                    removeButtons((Container) child);
                }

            }
        }

        public <T extends Component> T findFirstChildren(JComponent component, Class<T> clazz) {

            T child = null;
            for (Component comp : component.getComponents()) {

                if (clazz.isInstance(comp)) {

                    child = (T) comp;
                    break;

                } else if (comp instanceof JComponent) {

                    child = findFirstChildren((JComponent) comp, clazz);
                    if (child != null) {
                        break;
                    }

                }

            }

            return child;

        }
    }
}