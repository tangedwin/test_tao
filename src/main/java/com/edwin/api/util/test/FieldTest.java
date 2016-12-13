package com.edwin.api.util.test;

import java.lang.reflect.Field;

public class FieldTest {
	public static void main(String[] args){
		IdCheck id = new IdCheck();
		try {
			Field field = id.getClass().getDeclaredField("d");
			
			System.out.println(field.getName());
			System.out.println(field.getType());
//			field.setAccessible(true);
			field.set(id,"cccc");
			
			System.out.println(field.get(id));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
