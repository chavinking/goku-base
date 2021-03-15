package com.pgman.goku.dp23.singleton.type6;

/**
 * ˫�ؼ�飺�Ƽ�ʹ��
 */
public class SingletonType6 {

	public static void main(String[] args) {
		System.out.println("˫�ؼ��");
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
		
	}

}

class Singleton {
	private static volatile Singleton instance;
	
	private Singleton() {}

	public static synchronized Singleton getInstance() {
		if(instance == null) {
			synchronized (Singleton.class) {
				if(instance == null) {
					instance = new Singleton();
				}
			}
			
		}
		return instance;
	}
}