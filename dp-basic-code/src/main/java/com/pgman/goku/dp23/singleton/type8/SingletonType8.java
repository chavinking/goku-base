package com.pgman.goku.dp23.singleton.type8;

/**
 * ʹ��ö��ʵ�ֵ��� �� �Ƽ�ʹ��
 */
public class SingletonType8 {
	public static void main(String[] args) {
		Singleton instance = Singleton.INSTANCE;
		Singleton instance2 = Singleton.INSTANCE;
		System.out.println(instance == instance2);
		System.out.println(instance.hashCode());
		System.out.println(instance2.hashCode());
		instance.sayOK();
	}
}

//ʹ��ö�٣�����ʵ�ֵ���, �Ƽ�
enum Singleton {
	INSTANCE; // ����
	public void sayOK() {
		System.out.println("ok~");
	}
}