/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

package vince.syshax.utils.files;

import java.io.*;

public class StreamUtils {
    public static void copy(File from, File to) {
        try {
            InputStream in = new FileInputStream(from);
            OutputStream out = new FileOutputStream(to);

            copy(in, out);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(InputStream in, File to) {
        try {
            OutputStream out = new FileOutputStream(to);

            copy(in, out);

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(InputStream in, OutputStream out) {
        byte[] bytes = new byte[512];
        int read;

        try {
            while ((read = in.read(bytes)) != -1) out.write(bytes, 0, read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
