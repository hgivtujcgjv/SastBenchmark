/*
 * OWASP Benchmark-style Java pilot case.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/benchmark/racecond-00/BenchmarkTest01258")
public class BenchmarkTest01258 extends HttpServlet {
    private static final Object PROFILE_LOCK = new Object();
    private static final Object QUOTA_LOCK = new Object();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Thread first = new Thread(() -> {
            synchronized (PROFILE_LOCK) {
                sleepQuietly();
                synchronized (QUOTA_LOCK) {
                    update("profile");
                }
            }
        });

        Thread second = new Thread(() -> {
            synchronized (QUOTA_LOCK) {
                sleepQuietly();
                synchronized (PROFILE_LOCK) {
                    update("quota");
                }
            }
        });

        first.start();
        second.start();
        joinQuietly(first);
        joinQuietly(second);

        response.getWriter().println("profileDone=" + !first.isAlive() + ",quotaDone=" + !second.isAlive());
    }

    private static void update(String ignored) {
    }

    private static void sleepQuietly() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    private static void joinQuietly(Thread thread) {
        try {
            thread.join(50);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}

