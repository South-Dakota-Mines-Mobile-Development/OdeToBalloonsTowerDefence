package edu.sdsmt.team4.odetoballonstowerdefence;

import org.junit.Test;

import static org.junit.Assert.*;

import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.Balloon;
import edu.sdsmt.team4.odetoballonstowerdefence.ModelDataTypes.CollectionCircle;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void balloonInCircle() {
        CollectionCircle test = new CollectionCircle(10,10, 600,800);
        test.updateSecondaryPoint(5,10);
        Balloon b = new Balloon(9,9);

        test.checkBalloon(b);
        assertTrue( b.isPopped());
    }

    @Test
    public void balloonOutCircle() {
        CollectionCircle test = new CollectionCircle(10,10,600,800);
        test.updateSecondaryPoint(5,10);
        Balloon b = new Balloon(50,50);

        test.checkBalloon(b);
        assertFalse(b.isPopped());
    }
}