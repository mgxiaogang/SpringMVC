import java.util.Map;

import com.liupeng.util.EnumUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author fengdao.lp
 * @date 2018/7/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:common-util.xml"})
public class EnumTest {
    @Autowired
    private EnumUtil enumUtil;

    @Test
    public void test() {
        Map<String,String> map1 = enumUtil.getEnumWithKey("tag_a");
        System.out.println(map1);

        Map<String,String> map2 = enumUtil.getEnumWithKeyAndTagValue("tag_a",1);
        System.out.println(map2);
    }
}
