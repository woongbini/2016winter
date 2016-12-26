
public class SyncEx {

	public static void main(String[] args) {
		SyncObject obj = new SyncObject();
		
		WorkerThread th1 = new WorkerThread("woong", obj);
		th1.start();
		WorkerThread th2 = new WorkerThread("mangchi", obj);
		//th1.start(); //->이렇게 하면 누가 먼저 시작할지 모른다.
		th2.start();
	}

}

//집계판이라고 하자
class SyncObject {
	int sum = 0;
	
	synchronized /*문을 잠근다 -> 동기화 (절대로 충돌이 안생김)*/ public void add() {
		int n = sum;
		Thread.yield();
		n += 10;
		sum = n;
		System.out.println(Thread.currentThread().getName() + " : " + sum);
	}
}

//일하는 스레드드드
class WorkerThread extends Thread {
	SyncObject sObj;
	
	public WorkerThread(String name, SyncObject sObj) {
		super(name);
		this.sObj = sObj;
	}
	public void run() {
		for(int i=0 ; i<100 ; i++) {
			sObj.add();
		}
	}
}
