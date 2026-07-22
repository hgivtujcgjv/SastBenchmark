/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/benchmark/zipslip-00/BenchmarkTest04019")
public class BenchmarkTest04019 extends HttpServlet {

    private static final File EXTRACT_DIR =
            new File(System.getProperty("java.io.tmpdir"), "benchmark-java-bundles");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EXTRACT_DIR.mkdirs();
        int extracted = 0;

        try (ZipInputStream zip = new ZipInputStream(request.getPart("bundle").getInputStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                File destination = new File(EXTRACT_DIR, entry.getName());
                destination.getParentFile().mkdirs();

                try (OutputStream out = new FileOutputStream(destination)) {
                    zip.transferTo(out);
                }

                extracted++;
            }
        }

        response.getWriter().println("extracted=" + extracted);
    }
}
