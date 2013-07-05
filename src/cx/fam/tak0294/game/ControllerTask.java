package cx.fam.tak0294.game;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import cx.fam.tak0294.game.GameManager;
import cx.fam.tak0294.game.Task;
import cx.fam.tak0294.game.TaskDraw;
import cx.fam.tak0294.game.TaskMove;

public class ControllerTask extends Task
{
	private Rect LeftRect  = new Rect(10,690,90,780);
	private Rect RightRect = new Rect(130,690,210,780);
	
	public ControllerTask(GameManager g)
	{
		super(g);
		
		draw = new TaskDraw()
		{
			@Override
			public void func(Canvas canvas, Paint paint)
			{
				paint.setColor(Color.argb(120, 0, 0, 0));
				canvas.drawRect(new Rect(0,670,480,800), paint);
				
				paint.setColor(Color.argb(120, 255, 255, 255));
				canvas.drawCircle(420, 740, 40, paint);
				canvas.drawCircle(320, 740, 40, paint);
				canvas.drawRect(LeftRect, paint);
				canvas.drawRect(RightRect, paint);
				
				paint.setColor(Color.argb(120, 0, 0, 0));
				paint.setTextSize(64);
				canvas.drawText("A", 402, 760, paint);
				canvas.drawText("B", 302, 760, paint);
				canvas.drawText("©", 13, 760, paint);
				canvas.drawText("¨", 136, 760, paint);
				return;
			}
		};
		
		move = new TaskMove()
		{
			@Override
			public boolean func()
			{
				game.resetController();
				
				if(game.isTouch)
				{
					float touchX = game.touchX;
					float touchY = game.touchY;
					if(touchX > 0 && touchX < 90 && touchY > 690 && touchY < 780)
					{
						game.isLeft = true;
					}
					if(touchX > 130 && touchX < 210 && touchY > 690 && touchY < 780)
					{
						game.isRight = true;
					}
					
					if(touchX > 380 && touchX < 460 && touchY > 700 && touchY < 780)
					{
						game.isA = true;
					}
					
					if(touchX > 280 && touchX < 360 && touchY > 700 && touchY < 780)
					{
						game.isB = true;
					}
				}
				return true;
			}
		};
	}
}
