package serve;

import cn.timelost.hr.Application;
import com.qcloud.scf.runtime.AbstractSpringHandler;

/**
 * @author: Jyf
 * @Date: 2021/3/26 20:01
 */
public class MyHandler extends AbstractSpringHandler {
    @Override
    public void startApp() {
        // 修改为项目的入口主函数
        Application.main(new String[]{""});
    }
}