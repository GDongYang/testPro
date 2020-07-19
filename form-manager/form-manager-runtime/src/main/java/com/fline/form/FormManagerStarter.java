package com.fline.form;

/**
 * -Xms256m -Xmx1024m -XX:PermSize=256M -XX:MaxPermSize=256M
 */
public class FormManagerStarter {

	public static void main(String[] args) throws Exception {
		new RuntimeServer().start();
	}

}
