package createCode;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class CreateCodeTest {

	// domain类名
	static String[] entitys = {"User"};
	// 工程路径
	static String project = "C:/Users/yanju/Desktop/demo-gen/";
	// 包名
	static String packageName = "com.example.demo";
	// 路径
	static String filePath = "com/example/demo";

	// 模板文件名
	static String[] templates = {
			"Service.java",
			"ServiceImpl.java",
			"Controller.java",
			"ExtendMapper.java",
			"ExtendMapper.xml"
	};

	// 生成文件的路径
	static String[] filePaths = {
			"src/main/java/" + filePath + "/service/",
			"src/main/java/" + filePath + "/service/impl/",
			"src/main/java/" + filePath + "/controller/",
			"src/main/java/" + filePath + "/dao/extend/",
			"src/main/resources/mapper/extend/"
	};

	public static void main(String[] args) throws Exception {
		VelocityContext ctx = new VelocityContext();
		ctx.put("packageName", packageName);
		for (int i = 0; i < entitys.length; i++) {
			// 向上下文压入数据
			ctx.put("entity", entitys[i]);
			String entityLowerCase = entitys[i].substring(0, 1).toLowerCase()
					+ entitys[i].substring(1);
			ctx.put("entityLowerCase", entityLowerCase);// entitys[i].toLowerCase());

			// 对模板进行循环
			for (int j = 0; j < templates.length; j++) {
				// 获取模板对象
				Template template = Velocity.getTemplate("template/"
						+ templates[j], "UTF-8");
				// 获取生成文件的路径:包括文件名
				String path = project + filePaths[j] + entitys[i]
						+ templates[j];

				// Dao 和 Service 前面加上一个I
				if ("Service.java".equals(templates[j])) {
//					path = project + filePaths[j] + "I" + entitys[i]+ templates[j];
					path = project + filePaths[j] +  entitys[i]+ templates[j];
				}
				File file = new File(path);
				// 如果当前文件父目录不存在，则创建
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				// 如果当前文件已经存在着不创建这个文件
				if (file.exists()) {
					continue;
				}
				System.out.println("path:" + path);

				FileOutputStream outputStream = new FileOutputStream(file);
				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(outputStream, "UTF-8"));

				template.merge(ctx, writer);
				writer.flush();
				writer.close();

			}
		}
	}
}
