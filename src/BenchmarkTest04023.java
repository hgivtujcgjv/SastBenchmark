/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/benchmark/zipslip-00/BenchmarkTest04023")
public class BenchmarkTest04023 extends HttpServlet {

    private static final File EXTRACT_DIR =
            new File(System.getProperty("java.io.tmpdir"), "benchmark-java-imports");

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
        int rejected = 0;

        try (ZipInputStream zip = new ZipInputStream(request.getPart("archive").getInputStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                String name = entry.getName();
                if (name.contains("..") || name.startsWith("/") || name.startsWith("\\")
                        || new File(name).isAbsolute()) {
                    rejected++;
                    continue;
                }

                Path bareName = Paths.get(name).getFileName();
                if (bareName == null) {
                    rejected++;
                    continue;
                }

                File destination = new File(EXTRACT_DIR, bareName.toString());

                try (OutputStream out = new FileOutputStream(destination)) {
                    zip.transferTo(out);
                }

                extracted++;
            }
        }

        response.getWriter().println("extracted=" + extracted + " rejected=" + rejected);
    }
}
