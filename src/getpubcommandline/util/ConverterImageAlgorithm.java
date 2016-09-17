package getpubcommandline.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import getpublication.util.convert.ConverterAlgorithm;
import getpublication.util.convert.ImageFormats;

public class ConverterImageAlgorithm implements ConverterAlgorithm {

    @Override
    public boolean convert(File file, ImageFormats outputFormat) throws IOException {
        BufferedImage image = ImageIO.read(file);

        String outputFileName = FilenameUtils.removeExtension(file.getPath())
                + "." + outputFormat;

        return ImageIO.write(image, outputFormat.toString(), new File(outputFileName));
    }

}
