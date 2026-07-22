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
@WebServlet("/benchmark/zipslip-00/BenchmarkTest04020")
public class BenchmarkTest04020 extends HttpServlet {

    private static final String EXTRACT_DIR =
            System.getProperty("java.io.tmpdir") + "/benchmark-java-themes";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Files.createDirectories(Paths.get(EXTRACT_DIR));
        int restored = 0;

        try (ZipInputStream zip = new ZipInputStream(request.getPart("theme").getInputStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                Path target = Paths.get(EXTRACT_DIR).resolve(entry.getName());
                Files.createDirectories(target.getParent());

                Files.copy(zip, target, StandardCopyOption.REPLACE_EXISTING);

                restored++;
            }
        }

        response.getWriter().println("restored=" + restored);
    }
}
