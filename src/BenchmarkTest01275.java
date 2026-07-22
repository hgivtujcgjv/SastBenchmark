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
@WebServlet("/benchmark/fileupload-00/BenchmarkTest01275")
public class BenchmarkTest01275 extends HttpServlet {
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
        String filename = file.getSubmittedFileName();
        Files.createDirectories(UPLOAD_DIR);

        if (filename.contains(".jpg") || filename.contains(".png")) {
            file.write(UPLOAD_DIR.resolve(filename).toString());
            response.getWriter().println("stored");
            return;
        }

        response.getWriter().println("rejected");
    }
}

