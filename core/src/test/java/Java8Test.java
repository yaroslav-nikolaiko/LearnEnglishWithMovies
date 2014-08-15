import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by yaroslav on 8/3/14.
 */
public class Java8Test {

    @Test
    public void $(){

        perform((a,b)->{Integer c=a+b; return c+b;});
    }

    void perform(BiFunction<Integer, Integer, Integer> method){
        Integer t =5;
        Integer u = 15;
        System.out.println(method.apply(t,u));
    }

}
