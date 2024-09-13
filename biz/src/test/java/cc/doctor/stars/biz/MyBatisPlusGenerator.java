package cc.doctor.stars.biz;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        /**
         * 先配置数据源
         */
        MySqlQuery mySqlQuery = new MySqlQuery() {
            @Override
            public String[] fieldCustom() {
                return new String[]{"Default"};
            }
        };

        DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/stars?&useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai","dimp","12345678")
                .dbQuery(mySqlQuery).build();
        //通过datasourceConfig创建AutoGenerator
        AutoGenerator generator = new AutoGenerator(dsc);

        /**
         * 全局配置
         */
        String projectPath = System.getProperty("user.dir"); //获取项目路径
        String filePath = projectPath + "/biz/src/main/java";  //java下的文件路径
        GlobalConfig global = new GlobalConfig.Builder()
                .outputDir(filePath)//生成的输出路径
                .author("doctor")//生成的作者名字
                .dateType(DateType.TIME_PACK)//时间策略
                .commentDate("yyyy.MM.dd")//格式化时间格式
                .disableOpenDir()//禁止打开输出目录，默认false
                .build();

        /**
         * 包配置
         */
        PackageConfig packages = new PackageConfig.Builder()
                .parent("cc.doctor.stars.biz")//父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
                .entity("model")//实体类包名
                .mapper("mapper")//mapper层包名
                .xml("mapper")//数据访问层xml包名
                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/biz/src/main/resources/mapper")) //路径配置信息,就是配置各个文件模板的路径信息,这里以mapper.xml为例
                .build();

        /**
         * 策略配置开始
         */
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .enableCapitalMode()//开启全局大写命名
                //.likeTable()模糊表匹配
                .addInclude("feedback")//添加表匹配，指定要生成的数据表名，不写默认选定数据库所有表
                //.disableSqlFilter()禁用sql过滤:默认(不使用该方法）true
                //.enableSchema()启用schema:默认false

                .entityBuilder() //实体策略配置
                //.disableSerialVersionUID()禁用生成SerialVersionUID：默认true
                .enableChainModel()//开启链式模型
                .enableLombok()//开启lombok
                .enableRemoveIsPrefix()//开启 Boolean 类型字段移除 is 前缀
                .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                //.addTableFills()添加表字段填充
                .naming(NamingStrategy.underline_to_camel)//数据表映射实体命名策略：默认下划线转驼峰underline_to_camel
                .columnNaming(NamingStrategy.underline_to_camel)//表字段映射实体属性命名规则：默认null，不指定按照naming执行
                .idType(IdType.AUTO)//添加全局主键类型
                .formatFileName("%s")//格式化实体名称，%s取消首字母I
                .build()

                .mapperBuilder()//mapper文件策略
                .enableMapperAnnotation()//开启mapper注解
                .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
                .enableBaseColumnList()//启用xml文件中的BaseColumnList
                //.cache(缓存类.class)设置缓存实现类
                .formatMapperFileName("%sMapper")//格式化Dao类名称
                .formatXmlFileName("%sMapper")//格式化xml文件名称
                .build();
        /*至此，策略配置才算基本完成！*/

        /**
         * 将所有配置项整合到AutoGenerator中进行执行
         */
        generator.global(global)
                .packageInfo(packages)
                .strategy(strategyConfig)
                .template(new TemplateConfig.Builder().disable(TemplateType.CONTROLLER, TemplateType.SERVICE, TemplateType.SERVICE_IMPL).build())
                .execute();
    }

}
