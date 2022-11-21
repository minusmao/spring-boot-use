package com.example.spring.boot.use.mybatis.plus;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 使用mybatis-plus生成代码
 * 旧版本代码生成器：mybatis-plus3.5.1版本以下使用
 * 新版本代码生成器：mybatis-plus3.5.1版本及以上使用
 * 注意：使用前先创建两个公共类（包名.entity.BaseEntity、包名.entity.BaseController），如果不需要可以在配置中去掉
 *
 * @author minus
 * @since 2022/11/21 0:07
 */
@SpringBootTest
public class MybatisPlusGenerator {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    /**
     * 旧版本代码生成器
     * 参考链接：https://baomidou.com/pages/d357af/
     */
    @Test
    public void oldGenerator() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");    // 获得当前项目的路径
        gc.setOutputDir(projectPath + "/src/main/java");        // 生成的代码的位置
        gc.setAuthor("minus");                                  // 生成的代码中注释中的用户名
        gc.setOpen(false);                                      // 生成完代码后是否打开该文件夹
        gc.setServiceName("%sService");                         // 生成的Service层的名称
        gc.setSwagger2(true);                                   // 生成swagger文档
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataSourceProperties.getUrl());                       // 访问数据库的 URL
        dsc.setDriverName(dataSourceProperties.getDriverClassName());    // 驱动名称
        dsc.setUsername(dataSourceProperties.getUsername());             // 用户名称
        dsc.setPassword(dataSourceProperties.getPassword());             // 用户密码
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));                           // 模块名（控制台输入，项目没有分模块开发，所以不设置）
        pc.setParent("com.example.spring.boot.use.mybatis.plus");       // 生成的代码的包名
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
//        String templatePath = "/templates/mapper.xml.vm";   // 模板引擎是velocity（默认，需引入velocity-engine-core依赖）
        String templatePath = "/templates/mapper.xml.ftl";    // 模板引擎是freemarker（需引入freemarker依赖）
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名，如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        // 配置自定义模板，需在指定位置创建相关模板文件（可参考mybatis-plus-generator包的templates目录中的模板文件 ）
//        templateConfig.setEntity("templates/entity2.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);          // 设置表名和实体类名的映射：下划线命名_to_驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);    // 设置表中字段和实体类属性的映射：下划线命名_to_驼峰命名
        strategy.setSuperEntityClass("com.example.spring.boot.use.mybatis.plus.entity.BaseEntity");    // 设置 entity 层的公共父类
        strategy.setEntityLombokModel(true);                            // 设置自动生成的代码支持Lombok
        strategy.setRestControllerStyle(true);                          // 设置controller层为Restful风格
        strategy.setSuperControllerClass("com.example.spring.boot.use.mybatis.plus.controller.BaseController");    // 设置controller层的公共父类
        strategy.setSuperEntityColumns("id", "created", "updated", "statu");                // 写于公共父类中的公共字段
        strategy.setInclude(scanner("表名，多个请用英文逗号分割").split(","));         // 匹配的表名，控制台输入
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");                                // 忽略表的前缀（一般为模块名），暂不设置
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());          // 设置模板引擎为freemarker

        // 运行
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * 注意：测试程序默认不支持控制台输入
     *      需添加VM Options（IDEA软件 -> Help -> Edit Custom VM Options -> 添加；-Deditable.java.test.console=true）
     *      参考文章：https://blog.csdn.net/qq_33406883/article/details/116305990
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            // 读取控制台
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
