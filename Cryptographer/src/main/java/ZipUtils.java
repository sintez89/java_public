import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.model.ZipParameters;

public abstract class ZipUtils {

  private static final ZipParameters ZIP_PARAMETERS = new ZipParameters();
  public static final String EXTENSION = ".ivn";
  public static final String NO_NEED_SYMBOLS = "\\.[a-z]{3,4}$";

  public static ZipParameters getZipParameters() {
    ZIP_PARAMETERS.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    ZIP_PARAMETERS.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
    ZIP_PARAMETERS.setEncryptFiles(true);
    ZIP_PARAMETERS.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
    ZIP_PARAMETERS.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
    return ZIP_PARAMETERS;
  }
}
