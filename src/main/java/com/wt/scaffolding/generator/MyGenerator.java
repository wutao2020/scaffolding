package com.wt.scaffolding.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.util.Collections;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName 代码生成器
 * @Description TODO
 * @createTime 2021年11月17日 17:21:00
 */
public class MyGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create(
                //数据源配置，url需要修改
                new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/boot_security?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC","root","root")
                        .dbQuery(new MySqlQuery())
                        .schema("mybatis-plus")
                        .typeConvert(new MySqlTypeConvert())
                        .keyWordsHandler(new MySqlKeyWordsHandler())
        )

                //全局配置
                .globalConfig(builder -> {
                    builder.author("吴涛") // 设置作者
                            .disableOpenDir()//禁止打开输出目录
                            .enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir")+"/src/main/java"); // 指定输出目录
                })

                //包配置
                .packageConfig(builder -> {
                    builder.parent("com.wt.scaffolding") // 设置父包名，根据实制项目路径修改
                            //.moduleName("sys")
                            .entity("model")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .xml("mapper.xml")
                            .controller("controller")
                            //.other("other")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir")+"/src/main/resources/mapper"));
                })

                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("t_notice") // 设置需要生成的表名
                            .addTablePrefix("sys_", "t_") // 设置过滤表前缀
                            .entityBuilder() //实体类配置
                            .enableLombok() //使用lombok

                            .enableTableFieldAnnotation()//实体类字段注解
                            .controllerBuilder()//controller配置
                            .enableRestStyle()//开启restcontroller
                            .mapperBuilder()
                            .enableMapperAnnotation()//开启mapper注解
                            .enableBaseResultMap()//启用 BaseResultMap 生成
                            .enableBaseColumnList();//启用 BaseColumnList
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
