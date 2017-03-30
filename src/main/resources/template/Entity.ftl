package com.ott.domain;
import java.util.Date;
import lombok.Data;

@Data
public class ${className} {


<#list fieldName as field>
	<#if domainWithoutNot == 'use' >
	${field.COMMENT}
	</#if>
	private ${field.TYPE} ${field.BEANNAME};
</#list>

}