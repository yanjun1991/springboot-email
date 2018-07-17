package ${packageName}.service.impl;

import org.springframework.stereotype.Service;
import ${packageName}.service.${entity}Service;
import org.springframework.beans.factory.annotation.Autowired;

import ${packageName}.dao.extend.${entity}ExtendMapper;

@Service
public class ${entity}ServiceImpl implements ${entity}Service{

    @Autowired
    private ${entity}ExtendMapper ${entityLowerCase}ExtendMapper;

}
