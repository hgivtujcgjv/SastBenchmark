/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/benchmark/fileupload-00/BenchmarkTest04008")
public class BenchmarkTest04008 extends HttpServlet {
    private static final Path UPLOAD_DIR = Paths.get(System.getProperty("java.io.tmpdir"), "benchmark-java-uploads");
    private static final Map<String, String> ALLOWED_EXTENSIONS = Map.of(
            "jpg", ".jpg",
            "jpeg", ".jpg",
            "png", ".png",
            "gif", ".gif",
            "pdf", ".pdf");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part file = request.getPart("file");
        String storedExtension = ALLOWED_EXTENSIONS.get(extensionKey(file.getSubmittedFileName()));

        if (storedExtension == null) {
            response.getWriter().println("rejected");
            return;
        }

        Files.createDirectories(UPLOAD_DIR);
        Path destination = UPLOAD_DIR.resolve(UUID.randomUUID() + storedExtension);

        file.write(destination.toString());

        response.getWriter().println("saved=" + destination.getFileName());
    }

    private static String extensionKey(String submittedName) {
        if (submittedName == null) {
            return "";
        }
        int lastDot = submittedName.lastIndexOf('.');
        return lastDot < 0 ? "" : submittedName.substring(lastDot + 1).toLowerCase(Locale.ROOT);
    }
}
