package cx.fam.tak0294.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Task
{
	//�Q�[���}�l�[�W���ւ̎Q��.
	public GameManager game;
	
	//�֐��I�u�W�F�N�g.
	public TaskDraw draw;
	public TaskMove move;
	
	//�O��^�X�N�ւ̎Q��.
	public Task next;
	public Task prev;
	
	//���[�N�G���A.
	public Object userData;
	
	//Bitmap.
	public Bitmap bmp = null;
	
	//���W.
	public int x = 0;
	public int y = 0;
	
	//�ړ���.
	public int vx = 0;
	public int vy = 0;
	
	//�p�x.
	public int rotation = 0;
	
	//�����x.
	public int alpha = 255;
	
	//------------------------------------------------
	//	�R���X�g���N�^.
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
	//	�\���p�r�b�g�}�b�v..
	//------------------------------------------------
	public void setBitmap(Bitmap bmp)
	{
		if(this.bmp == null)
			this.bmp = bmp;
	}
	
	//------------------------------------------------
	//	���A�O�ւ̎Q�Ƃ��Z�b�g.
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
	//	���A�O�ւ̎Q�Ƃ��Q�b�g.
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
