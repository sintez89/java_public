import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class EncryptThread extends Thread {

  private final WindowForm form;
  private File file;

  public EncryptThread(WindowForm form) {
    this.form = form;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public void setPassword(String password) {
    ZipUtils.getZipParameters().setPassword(password);
  }

  public void run() {
    try {
      ZipFile zipFile = new ZipFile(getArchiveName());
      if (file.isDirectory()) {
        zipFile.addFolder(file, ZipUtils.getZipParameters());
      } else {
        zipFile.addFile(file, ZipUtils.getZipParameters());
      }
    } catch (
        ZipException cryptException) {
      form.showWarning(cryptException.getMessage());
      form.showInfo("Операция не выполнена!");
    }
    setPassword("");
  }

  private String getArchiveName() {
    String archiveName = file
        .getAbsolutePath()
        .replaceAll(ZipUtils.NO_NEED_SYMBOLS, "") + ZipUtils.EXTENSION;
    int number = 1;
    while (new File(archiveName).exists()) {
      archiveName = file
          .getAbsolutePath()
          .replaceAll(ZipUtils.NO_NEED_SYMBOLS, "") + number + ZipUtils.EXTENSION;
      number++;
    }
    return archiveName;
  }
}
