package cx.fam.tak0294.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Task
{
	//ゲームマネージャへの参照.
	public GameManager game;
	
	//関数オブジェクト.
	public TaskDraw draw;
	public TaskMove move;
	
	//前後タスクへの参照.
	public Task next;
	public Task prev;
	
	//ワークエリア.
	public Object userData;
	
	//Bitmap.
	public Bitmap bmp = null;
	
	//座標.
	public int x = 0;
	public int y = 0;
	
	//移動量.
	public int vx = 0;
	public int vy = 0;
	
	//角度.
	public int rotation = 0;
	
	//透明度.
	public int alpha = 255;
	
	//------------------------------------------------
	//	コンストラクタ.
	//------------------------------------------------
	public Task(GameManager game)
	{
		this.game = game;
		this.initFunc();
	}

	public void initFunc()
	{
		draw = new TaskDraw()
		{
			@Override
			public void func(Canvas canvas, Paint paint)
			{
				
			}
		};
		
		move = new TaskMove()
		{
			@Override
			public boolean func()
			{
				return false;
			}
		};
	}

	public void initValue()
	{
		alpha = 255;
		bmp = null;
	}
	
	//------------------------------------------------
	//	表示用ビットマップ..
	//------------------------------------------------
	public void setBitmap(Bitmap bmp)
	{
		if(this.bmp == null)
			this.bmp = bmp;
	}
	
	//------------------------------------------------
	//	次、前への参照をセット.
	//------------------------------------------------
	public void setNext(Task t)
	{
		this.next = t;
	}
	public void setPrev(Task t)
	{
		this.prev = t;
	}

	//------------------------------------------------
	//	次、前への参照をゲット.
	//------------------------------------------------
	public Task getNext()
	{
		return this.next;
	}
	public Task getPrev()
	{
		return this.prev;
	}
}
