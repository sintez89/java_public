import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.lingala.zip4j.core.ZipFile;

public class WindowForm {

  public final static String DECRYPT_TEXT = "Расшифровать";
  public final static String ENCRYPT_TEXT = "Зашифровать";

  private JPanel rootPanel;
  private JTextField filePath;
  private JButton viewButton;
  private JButton actionButton;
  private File selectedFile;
  private boolean isEncryptedFileSelected;

  public WindowForm() {
    viewButton.addActionListener(new Action() {
      @Override
      public Object getValue(String key) {
        return null;
      }

      @Override
      public void putValue(String key, Object value) {

      }

      @Override
      public void setEnabled(boolean b) {

      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void addPropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void removePropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.showOpenDialog(rootPanel);
        selectedFile = chooser.getSelectedFile();

        if (selectedFile == null) {
          filePath.setText("Выберите файл или папку");
          actionButton.setVisible(false);
        } else {
          toDoViewButtonOperation();
          actionButton.setVisible(true);
        }
      }
    });
    actionButton.addActionListener(new Action() {
      @Override
      public Object getValue(String key) {
        return null;
      }

      @Override
      public void putValue(String key, Object value) {

      }

      @Override
      public void setEnabled(boolean b) {

      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void addPropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void removePropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void actionPerformed(ActionEvent e) {
        if (selectedFile == null) {
          return;
        }

        String password = JOptionPane.showInputDialog("Введите пароль:");
        if (password == null || password.length() == 0) {
          showWarning("Пароль не указан!");
          return;
        }

        toDoActionButtonOperation(password);
      }
    });
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  private void toDoViewButtonOperation() {
    filePath.setText(selectedFile.getAbsolutePath());
    try {
      ZipFile zipFile = new ZipFile(selectedFile);
      if (zipFile.isValidZipFile() && zipFile.isEncrypted()) {
        actionButton.setText(DECRYPT_TEXT);
        isEncryptedFileSelected = false;
      } else {
        actionButton.setText(ENCRYPT_TEXT);
        isEncryptedFileSelected = true;
      }
    } catch (Exception viewException) {
      showWarning(viewException.getMessage());
      showInfo("Операция не выполнена!");
    }
  }

  private void toDoActionButtonOperation(String password) {
    if (isEncryptedFileSelected) {
      encryptFile(password);
    } else {
      decryptFile(password);
    }
    resetFilePath();
    if (!isExceptionCatch()) {
      showInfo("Операция выполнена успешно!");
    }
  }

  public void resetFilePath() {
    filePath.setText("Выберите файл или папку");
    actionButton.setVisible(false);
  }

  private void encryptFile(String password) {
    EncryptThread encryptThread = new EncryptThread(this);
    encryptThread.setFile(selectedFile);
    encryptThread.setPassword(password);
    encryptThread.start();
  }

  private void decryptFile(String password) {
    DecryptThread decryptThread = new DecryptThread(this);
    decryptThread.setFile(selectedFile);
    decryptThread.setPassword(password);
    decryptThread.start();
  }

  public void showWarning(String massage) {
    JOptionPane.showMessageDialog(rootPanel,
        massage,
        "Ошибка!",
        JOptionPane.WARNING_MESSAGE);
  }

  public void showInfo(String massage) {
    filePath.setText(massage);
    actionButton.setVisible(false);
  }

  private boolean isExceptionCatch() {
    return filePath.getText().equals("Операция не выполнена!");
  }
}


