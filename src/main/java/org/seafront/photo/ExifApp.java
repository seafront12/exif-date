package org.seafront.photo;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by alexj on 2016/8/25.
 */
public class ExifApp {

    public static void main(String[] args) throws ImageWriteException, ImageReadException, IOException {
        if (args.length < 2) {
            System.out.println("ExifApp [inputFolder] [outputFolder]");
        }
        ExifWriter exifWriter = new ExifWriter();
        String inputFolder = args[0];
        String outputFolder = args[1];
        for (File imageFile : Paths.get(inputFolder).toFile().listFiles(f -> (f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".jpeg")))) {
            String imageFileName = imageFile.getName();
            File outputImage = new File(outputFolder + File.separator + imageFileName);
            exifWriter.inferPhotoTakenDateFromFileName(imageFile, outputImage,
                    ImmutableMap.of(
                            "^IMG_(\\d{8}_\\d{6})\\.jp.+", "yyyyMMdd_HHmmss",
                            "^mmexport(\\d{13})\\.jp.+$", "TIMESTAMP",
                            "^img(\\d{13})\\.jp.+$", "TIMESTAMP",
                            "^pt(\\d{4}_\\d{2}_\\d{2}_\\d{2}_\\d{2}_\\d{2})_.*$", "yyyy_MM_dd_HH_mm_ss"

                    ));
        }

    }
}
