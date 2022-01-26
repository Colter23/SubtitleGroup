package top.colter.subtitlegroup.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class ObjectUtils {

    public static boolean objCheckIsNull(Object object){
        Class clazz = (Class)object.getClass();
        Field fields[] = clazz.getDeclaredFields();
        boolean flag = true;
        for(Field field : fields){
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
                Type fieldType =field.getGenericType();
                String fieldName = field.getName();
                //System.out.println("属性类型："+fieldType+",属性名："+fieldName+",属性值："+fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fieldValue != null){
                flag = false;
                break;
            }
        }
        return flag;
    }

}
