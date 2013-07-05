package cx.fam.tak0294.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceScreen extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
	public Bitmap mImage;
	public SurfaceHolder mHolder;
    private Thread mLooper;
    private GameManager game = null;
    
	public SurfaceScreen(Context context, GameManager game)
	{
	    super(context);
	    this.game = game;
	    getHolder().addCallback(this);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		//�X���b�h�������J�n
	    if(mLooper != null )
	    {
	        mLooper.start();
	    }
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
	    //�X���b�h�̐���
	    mHolder = holder;
	    mLooper = new Thread(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		//�X���b�h�̍폜.
		mLooper = null;
	}

	@Override
	public void run()
	{
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		while (mLooper != null) {
			 
	        //�`�揈��
	        game.tick();
	 
	    }
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		game.touchX = event.getX();
		game.touchY = event.getY();
		
		switch(event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				game.isTouch = true;
			break;
			case MotionEvent.ACTION_MOVE:
				game.touchX = event.getX();
				game.touchY = event.getY();
			break;
			case MotionEvent.ACTION_UP:
				game.isTouch = false;
			break;
			case MotionEvent.ACTION_CANCEL:
				game.isTouch = false;
			break;
		}
		
		invalidate();
		
		return true;
	}
}
