package org.pngquant;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

abstract class LiqObject {
    static {
        try {
            loadLibrary();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadLibrary() throws IOException {
        File directory = new File(System.getProperty("java.io.tmpdir"), "libimagequant_extracted_from_classpath");
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("create libimagequant library dir failed!");
            }
        }
        FileUtils.cleanDirectory(directory);



        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            FileUtils.copyInputStreamToFile(
                    getResourceAsStream(File.separator + "libimagequant" + File.separator + "libimagequant.jnilib"),
                    new File(directory, "libimagequant.jnilib"));
            System.load(directory.getPath() + File.separator + "libimagequant.jnilib");
        } else if (System.getProperty("os.name").toLowerCase().contains("win")) {
            FileUtils.copyInputStreamToFile(
                    getResourceAsStream(File.separator + "libimagequant" + File.separator + "libimagequant.dll"),
                    new File(directory, "libimagequant.dll"));
            System.load(directory.getPath() + File.separator + "libimagequant.dll");
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            FileUtils.copyInputStreamToFile(
                    getResourceAsStream(File.separator + "libimagequant" + File.separator + "libimagequant.so"),
                    new File(directory, "libimagequant.so"));
            System.load(directory.getPath() + File.separator + "libimagequant.so");
        } else {
            throw new RuntimeException("unsupported os for libimagequant");
        }
    }

    private static InputStream getResourceAsStream(String resource) {
        final InputStream in
                = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? LiqObject.class.getResourceAsStream(resource) : in;
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    long handle;

    /**
     * Free memory used by the library. The object must not be used after this call.
     */
    abstract public void close();

    @Override
    protected void finalize() throws Throwable {
        close();
    }
}
