package org.tirose.starter.generator.util;


import org.tirose.core.tool.exception.ServiceException;
import org.tirose.core.tool.util.ApplicationContextUtil;
import org.tirose.core.tool.util.DateUtil;
import org.tirose.starter.generator.bean.ColumnDO;
import org.tirose.starter.generator.bean.TableDO;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.tirose.starter.generator.constant.GeneratorConstant;
import org.tirose.starter.generator.constant.GeneratorEnum;
import org.tirose.starter.generator.mapper.GeneratorMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GeneratorUtil {

	private static GeneratorMapper generatorMapper;

	{
		generatorMapper = ApplicationContextUtil.getBean(GeneratorMapper.class);
	}

	/**
	 * 生成代码
	 * @param tableNames
	 * @return
	 */
	public static byte[] generatorCode(GeneratorEnum type, List<String> tableNames) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = generatorMapper.get(tableName);
			//查询列信息
			List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
			//生成代码
			doGeneratorCode(table, columns, type, zip);
		}
		IOUtils.closeQuietly(zip);

		return outputStream.toByteArray();

	}

	/**
	 * 生成代码
	 * @param tableNames
	 * @return
	 */
	public static byte[] generatorCode (GeneratorEnum type, String... tableNames) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = generatorMapper.get(tableName);
			//查询列信息
			List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
			//生成代码
			doGeneratorCode(table, columns, type, zip);
		}
		IOUtils.closeQuietly(zip);

		return outputStream.toByteArray();

	}



    /**
     * 生成代码
     */
    public static void doGeneratorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, GeneratorEnum type, ZipOutputStream zip) {
        //获取配置信息
        Configuration config = getConfig();
        //表信息
        TableDO tableDO = new TableDO();
        //表名
        tableDO.setTableName(table.get("tableName"));
        //表注释
        tableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableDO.getTableName(), config.getString("tablePrefix"), config.getString("autoRemovePre"));
        //类名，第一个字母大写
        tableDO.setClassName(className);
        //类名，第一个字母小写
        tableDO.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {


            ColumnDO columnDO = new ColumnDO();
            //列名
            columnDO.setColumnName(column.get("columnName"));
            //列名类型
            columnDO.setDataType(column.get("dataType"));
            //列名备注
            columnDO.setComments(column.get("columnComment"));
            columnDO.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
			// 属性名称(第一个字母大写)，如：user_name => UserName
            columnDO.setAttrName(attrName);
			// 属性名称(第一个字母小写)，如：user_name => userName
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnDO.getDataType(), "unknowType");
			// 属性类型
            columnDO.setAttrType(attrType);

            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableDO.getPk() == null) {
                tableDO.setPk(columnDO);
            }

            columsList.add(columnDO);
        }
        //将列信息放到表信息中
        tableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableDO.getPk() == null) {
            tableDO.setPk(tableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableDO.getTableName());
        map.put("comments", tableDO.getComments());
        map.put("pk", tableDO.getPk());
        map.put("className", tableDO.getClassName());
        map.put("classname", tableDO.getClassname());
        map.put("pathName", config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1));
        map.put("columns", tableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtil.formatDateTime(new Date()));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表

        List<String> templates = getTemplates(type);
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableDO.getClassname(), tableDO.getClassName(), config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new ServiceException("渲染模板失败");
            }
        }
    }
	/**
	 * 獲取所有的模板文件
	 * @return
	 */
	private static List<String> getTemplates(GeneratorEnum type) {
		//返回所有模板文件的地址
		List<String> templates = new ArrayList<String>();
		templates.add("templates/" + type.getName() + "/entity.java.vm");
		templates.add("templates/" + type.getName() + "/entityVO.java.vm");
		templates.add("templates/" + type.getName() + "/entityDTO.java.vm");
		templates.add("templates/" + type.getName() + "/mapper.java.vm");
		templates.add("templates/" + type.getName() + "/mapper.xml.vm");
		templates.add("templates/" + type.getName() + "/service.java.vm");
		templates.add("templates/" + type.getName() + "/serviceImpl.java.vm");
		templates.add("templates/" + type.getName() + "/controller.java.vm");
		return templates;
	}

    /**
     * 列名转换成Java属性名
     */
	private static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
	private static String tableToJava(String tableName, String tablePrefix, String autoRemovePre) {
        if (GeneratorConstant.AUTO_REOMVE_PRE.equals(autoRemovePre)) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
	private static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new ServiceException("获取配置文件失败");
        }
    }

    /**
     * 获取文件名
     */
    private static String getFileName(String template, String classname, String className, String packageName) {
        String packagePath = "main" + File.separator + "java" + File.separator;
        //String modulesname=config.getString("packageName");
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("entity.java.vm")) {
			return packagePath + "entity" + File.separator + className + ".java";
		}

		if (template.contains("entityVO.java.vm")) {
			return packagePath + "entity" + File.separator + className + "VO.java";
		}

		if (template.contains("entityDTO.java.vm")) {
			return packagePath + "entity" + File.separator + className + "DTO.java";
		}

		if(template.contains("mapper.java.vm")){
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}

		if (template.contains("mapper.xml.vm")) {
			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + packageName + File.separator + className + "Mapper.xml";
		}

        if (template.contains("service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("serviceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }




//		if(template.contains("menu.sql.vm")){
//			return className.toLowerCase() + "_menu.sql";
//		}

        return null;
    }
}
