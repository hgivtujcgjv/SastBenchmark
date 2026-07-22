/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/benchmark/fileupload-00/BenchmarkTest01274")
public class BenchmarkTest01274 extends HttpServlet {
    private static final Path UPLOAD_DIR = Paths.get(System.getProperty("java.io.tmpdir"), "benchmark-java-uploads");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part file = request.getPart("file");
        String folder = defaultValue(request.getParameter("folder"), "incoming");
        Path destinationDir = UPLOAD_DIR.resolve(folder);
        Files.createDirectories(destinationDir);
        Path destination = destinationDir.resolve(file.getSubmittedFileName());

        file.write(destination.toString());

        response.getWriter().println("stored=" + destination);
    }

    private static String defaultValue(String value, String fallback) {
        return value == null ? fallback : value;
    }
}

