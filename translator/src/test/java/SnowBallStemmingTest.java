import org.junit.Ignore;
import org.junit.Test;
import org.tartarus.snowball.SnowballProgram;
import org.tartarus.snowball.ext.EnglishStemmer;

/**
 * Created by yaroslav on 8/16/14.
 */
public class SnowBallStemmingTest {

    @Test
    @Ignore
    public void $(){
        SnowballProgram program = new EnglishStemmer();
        program.setCurrent("nicely");
        boolean result = program.stem();
        System.out.println(program.getCurrent()+"   and result = "+result);  //seems like result is always true
    }
}
