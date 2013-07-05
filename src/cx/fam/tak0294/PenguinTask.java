package cx.fam.tak0294;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import cx.fam.tak0294.game.GameManager;
import cx.fam.tak0294.game.Task;
import cx.fam.tak0294.game.TaskDraw;
import cx.fam.tak0294.game.TaskMove;

public class PenguinTask extends Task
{
	private int dx = 0;
	private int dy = 0;
	private boolean isTouch;
	public PenguinTask(GameManager g)
	{
		super(g);
		setBitmap(BitmapFactory.decodeResource(game.app.getResources(), R.drawable.pen));
		vy = 2;
		vx = 2;
		
		draw = new TaskDraw()
		{
			@Override
			public void func(Canvas canvas, Paint paint)
			{
				canvas.drawBitmap(bmp, x, y, paint);
			}
		};
		
		move = new TaskMove()
		{
			@Override
			public boolean func()
			{
				
				if(game.isTouch)
				{
					dx = (int)game.touchX;
					dy = (int)game.touchY;
					x+=(dx-x)*0.08;
					y+=(dy-y)*0.08;
					isTouch = true;
				}
				else
				{
					if(isTouch)
					{
						vx = (int)(Math.random() * 10);
						vy = (int)(Math.random() * 10);
						isTouch = false;
					}
					x+=vx;
					y+=vy;

				}
				
				
				if(x>320 || x<0)
					vx *= -1;
				if(y>480 || y<0)
					vy *= -1;
				
				return true;
			}
		};
	}
}
