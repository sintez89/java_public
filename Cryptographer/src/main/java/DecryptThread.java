import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class DecryptThread extends Thread {

  private final WindowForm form;
  private File file;
  private String password;

  public DecryptThread(WindowForm form) {
    this.form = form;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public void run() {
    try {
      ZipFile zipFile = new ZipFile(file);
      zipFile.setPassword(password);
      zipFile.extractAll(getOutputPath());
    } catch (ZipException decryptException) {
      form.resetFilePath();
      form.showWarning(decryptException.getMessage());
      form.showInfo("Операция не выполнена!");
    }
    setPassword("");
  }

  private String getOutputPath() {
    String path = file.getAbsolutePath()
        .replaceAll(ZipUtils.NO_NEED_SYMBOLS, "/");
    int number = 1;
    while (new File(path).exists()) {
      path = file.getAbsolutePath()
          .replaceAll(ZipUtils.NO_NEED_SYMBOLS, number++ + "/");
    }
    return path;
  }
}
