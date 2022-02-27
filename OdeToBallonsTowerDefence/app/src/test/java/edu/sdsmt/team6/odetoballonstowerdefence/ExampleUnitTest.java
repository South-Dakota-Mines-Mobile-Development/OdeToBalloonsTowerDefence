package edu.sdsmt.team6.odetoballonstowerdefence;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team6.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void balloonInCircle() {
        CollectionCircle test = new CollectionCircle(10,10,20,20,600,800);
        Balloon b = new Balloon(9,9);

        test.checkBalloon(b);
        assertEquals(true, b.isPopped());
    }

    @Test
    public void balloonOutCircle() {
        CollectionCircle test = new CollectionCircle(10,10,20,20,600,800);
        Balloon b = new Balloon(50,50);

        test.checkBalloon(b);
        assertEquals(false, b.isPopped());
    }
}