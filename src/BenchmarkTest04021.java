/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/benchmark/zipslip-00/BenchmarkTest04021")
public class BenchmarkTest04021 extends HttpServlet {

    private static final String EXTRACT_DIR =
            System.getProperty("java.io.tmpdir") + File.separator + "benchmark-java-plugins";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Path staged = Files.createTempFile("plugin-", ".zip");
        try (InputStream upload = request.getPart("plugin").getInputStream()) {
            Files.copy(upload, staged, StandardCopyOption.REPLACE_EXISTING);
        }

        new File(EXTRACT_DIR).mkdirs();
        int installed = 0;

        try (ZipFile archive = new ZipFile(staged.toFile())) {
            Enumeration<? extends ZipEntry> entries = archive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                String destination = EXTRACT_DIR + "/" + entry.getName();
                new File(destination).getParentFile().mkdirs();

                try (InputStream in = archive.getInputStream(entry);
                        OutputStream out = new FileOutputStream(destination)) {
                    in.transferTo(out);
                }

                installed++;
            }
        } finally {
            Files.deleteIfExists(staged);
        }

        response.getWriter().println("installed=" + installed);
    }
}
