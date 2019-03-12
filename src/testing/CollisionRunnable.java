package testing;

public class CollisionRunnable implements Runnable {
	
	private Block block;
	private Ball ball;
	
	public CollisionRunnable(Block b, Ball ba){
		block = b;
		ball = ba;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		block.collidedWith(ball);
	}

}
