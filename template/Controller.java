package ${packageName}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



import ${packageName}.service.${entity}Service;

@Controller
@RequestMapping(value = "/admin/${entityLowerCase}")
public class ${entity}Controller {

	@Autowired
	private ${entity}Service ${entityLowerCase}Service;



}
