package Byzantische;

import Byzantische.Interface.Engine;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ByzGeneralTest extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ByzGeneralTest.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void xtestEngineOnce() {
        int n = 5;
        int m = 6;
        int source = 3;
        runEngine(n, m, source);
    }

    private void runEngine(int n, int m, int source) {
        System.out.println("Starting|" + m + "|" + n + "|" + source);
        long started = System.currentTimeMillis();
        Engine engine = new DefaultEngine(source, m, n);
        engine.start();
        engine.run();
        System.out.println("Finished|" + m + "|" + n + "|" + source + "|" + Process.totalMessages + "|" + (System.currentTimeMillis() - started));
        Process.totalMessages = 0;
    }

    public void testEngine() {
        for (int n = 5; n <= 20; n++) {
            for (int m = 10; m <= 1000; m += 100) {
                int source = n / 3;
                runEngine(n, m, source);
            }
        }
    }
}
