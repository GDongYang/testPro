package com.fline.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xuhuan
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Column {
	public String name() default "fieldName";
	public String column() default "fieldName";
    public String setFuncName() default "setField";
    public String getFuncName() default "getField";
    public boolean defaultDBValue() default false;
    public String javaType() default "String";
    public String columnType() default "varchar";
    public boolean splitColumn() default false;
    public boolean hidden() default false;
    public int width() default 0;
    public boolean searchEnable() default false;
    public boolean export() default false;
}
