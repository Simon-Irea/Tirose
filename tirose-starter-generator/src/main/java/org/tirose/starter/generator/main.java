package org.tirose.starter.generator;

import org.tirose.starter.generator.util.GeneratorUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class main {
	public static void main(String[] args) throws FileNotFoundException {
		Map<String, String> table = new HashMap<String,String>();
		List<Map<String, String>> columns = new ArrayList<Map<String, String>>();

		ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("D:\\test.zip"));
		GeneratorUtil.generatorCode(table, columns, zip);

	}
}
