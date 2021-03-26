package org.pngquant;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
//        if (args.length != 2) {
//            System.out.println("missing args");
//            return;
//        }
        PngQuant pngQuant = new PngQuant();
//        if (!Files.probeContentType(Paths.get(args[0])).toLowerCase().contains("image/png")) {
//            throw new RuntimeException("not png file");
//        }
        File in = new File("/Users/moonie/java/jcef-macos/src/test/resources/input.png");
        File out = new File("/Users/moonie/java/jcef-macos/src/test/resources/output2.png");
        try {
            ImageIO.write(pngQuant.getRemapped(ImageIO.read(in)), "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
