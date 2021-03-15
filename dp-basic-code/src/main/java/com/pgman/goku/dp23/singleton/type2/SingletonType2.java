package com.pgman.goku.dp23.singleton.type2;

/**
 * ����ʽ����̬����飩������ʹ��
 */
public class SingletonType2 {

	public static void main(String[] args) {
		// ����
		Singleton instance = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		System.out.println(instance == instance2); // true
		System.out.println("instance.hashCode=" + instance.hashCode());
		System.out.println("instance2.hashCode=" + instance2.hashCode());
	}

}

class Singleton {
	
	// 1. ������˽�л�, �ⲿ��new
	private Singleton() {
		
	}
	

	// 2.�����ڲ���������ʵ��
	private  static Singleton instance;
	
	static { // �ھ�̬������У�������������
		instance = new Singleton();
	}
	
	//3. �ṩһ�����еľ�̬����������ʵ������
	public static Singleton getInstance() {
		return instance;
	}
	
}