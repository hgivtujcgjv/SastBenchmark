/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet("/benchmark/zipslip-00/BenchmarkTest04022")
public class BenchmarkTest04022 extends HttpServlet {

    private static final Path EXTRACT_DIR =
            Paths.get(System.getProperty("java.io.tmpdir"), "benchmark-java-reports");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Files.createDirectories(EXTRACT_DIR);
        Path baseDir = EXTRACT_DIR.toRealPath();
        int restored = 0;
        int skipped = 0;

        try (ZipInputStream zip = new ZipInputStream(request.getPart("report").getInputStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                Path target = baseDir.resolve(entry.getName()).normalize();
                if (!target.startsWith(baseDir)) {
                    skipped++;
                    continue;
                }

                Files.createDirectories(target.getParent());

                Files.copy(zip, target, StandardCopyOption.REPLACE_EXISTING);

                restored++;
            }
        }

        response.getWriter().println("restored=" + restored + " skipped=" + skipped);
    }
}
