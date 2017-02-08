import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouzm5
 * @version v1.0.0
 * @Copyright Copyright (c) 2017 AsiaInfo
 * @ClassName
 * @Description
 * @date 2017/2/8 <br>
 * Modification History:<br>
 * Date Author Version Description<br>
 * ---------------------------------------------------------*<br>
 * 2017/2/8 zhouzm5 v1.0.0 <br>
 */
public class MyBatisGeneratorRun {
    public static void main(String[] args) throws IOException, XMLParserException, SQLException, InterruptedException, InvalidConfigurationException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //加载generatorConfig文件
        File configFile = new File(MyBatisGeneratorRun.class.getClassLoader().getResource("generatorConfig.xml").getPath());
        //加载数据库信息，例如driverClassName，username,password,url等
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        if(!warnings.isEmpty()){
            for (String warn : warnings) {
                System.out.println(warn);
            }
        }
        System.out.println("生成成功！");
    }
}
