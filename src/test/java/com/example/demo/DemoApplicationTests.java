package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	@Autowired
	private TemplateEngine templateEngine;

	private String to = "yangyj@tide-holdings.com";
	@Test
	public void sendSimpleMail() {
		mailService.sendSimpleMail(to, "主题：简单邮件", "测试邮件内容");
	}



	@Test
	public void sendHtmlMail() {
		//是导这个包
		List<User> userList = userService.getList(1,5,"id DESC");

		Context context = new Context();
		context.setVariable("username","老杨");
		context.setVariable("message","你好");
		context.setVariable("list",userList);

		//获取thymeleaf的html模板
		String emailContent= templateEngine.process("mailTemplate", context);
		mailService.sendHtmlMail(to,"这是thymeleaf模板邮件",emailContent);
		mailService.sendAttachmentsMail(to, "主题：带附件的邮件", "有附件，请查收！", "C:\\Users\\yanju\\Desktop\\关于四高统计\\天地健康城健康管理数据统计报告\\天地健康城健康管理数据统计报告.xlsx");
	}

	@Test
	public void sendAttachmentsMail() throws FileNotFoundException {
		File file = new File("C:\\Users\\yanju\\Desktop\\关于四高统计\\天地健康城健康管理数据统计报告\\天地健康城健康管理数据统计报告.xlsx");
		InputStream in = new FileInputStream(file);
		mailService.sendMail("测试excel邮件",to,"nihao","天地健康城健康管理数据统计报告123.xlsx",in,null);
	}

	@Test
	public void sendInlineResourceMail() {
		String rscId = "rscId001";
		mailService.sendInlineResourceMail(to,
				"主题：嵌入静态资源的邮件",
				"<html><body>这是有嵌入静态资源：<img src=\'cid:" + rscId + "\' ></body></html>",
				"C:\\Users\\Xu\\Desktop\\csdn\\1.png",
				rscId);
	}


}
