package com.blink.domain;
import java.util.Date;

public class ${className} {
<#list fieldName as field>
	${field.COMMENT}
	private ${field.TYPE} ${field.NAME};
</#list>

<#list fieldName as field>
	public void set${field.NAME?cap_first}(${field.TYPE} ${field.NAME}) {
        this.${field.NAME} = ${field.NAME};
    }
    public ${field.TYPE} get${field.NAME?cap_first}() {
        return ${field.NAME};
    }
</#list>
}