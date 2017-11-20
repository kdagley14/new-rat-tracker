package team10x.cs2340.rattracker2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testRegistration() throws Exception {
        RegisterActivity test = new RegisterActivity();
        assertEquals(test.register("","","","", false, true),0);
        assertEquals(test.register("","yes","yes","yes", false, true),0);
        assertEquals(test.register("yes","","yes","yes", false, true),0);
        assertEquals(test.register("yes","yes","","yes", false, true),0);

        assertEquals(test.register("testN","testUN", "testPW","TestT", false, true),1);
        assertEquals(test.register("testN","testUN", "testPW","TestT", false, false),2);

    }
}