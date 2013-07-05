package cx.fam.tak0294.game;

import java.util.Vector;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Window;

public class GameManager
{
	public Activity app = null;
	private SurfaceScreen screen = null;
	public Vector<TaskList> tl = new Vector<TaskList>();
	
	public float touchX = 0.0f;
	public float touchY = 0.0f;
	public boolean isTouch = false;
	
	//--------------------------------------------
	//	コントローラ.
	//--------------------------------------------
	private TaskList controllerTaskList = null;
	public boolean isA = false;
	public boolean isB = false;
	public boolean isUp 	= false;
	public boolean isDown 	= false;
	public boolean isLeft 	= false;
	public boolean isRight 	= false;
	public boolean isStart 	= false;
	public boolean isSelect = false;
	
	//--------------------------------------------
	//	コントローラフラグのリセット.
	//--------------------------------------------
	public void resetController()
	{
		isA = false;
		isB = false;
		isUp = false;
		isDown = false;
		isLeft = false;
		isRight = false;
		isStart = false;
		isSelect = false;
	}
	
	//--------------------------------------------
	//	コンストラクタ.
	//--------------------------------------------
	public GameManager(Activity con)
	{
		app = con;
		screen = new SurfaceScreen(con, this);
		
		//コントローラ.
		controllerTaskList = new TaskList(this, ControllerTask.class);
		controllerTaskList.createTask();
	}

	public void createTaskList(Class<? extends Task> task)
	{
		this.tl.add(new TaskList(this, task));
	}
	
	public void createTaskList(Class<? extends Task> task, int TaskNum)
	{
		this.tl.add(new TaskList(this, task, TaskNum));
	}
	private void move()
	{
		//コントローラの挙動.
		controllerTaskList.moveTask();

		for(int ii=0;ii<tl.size();ii++)
		{
			tl.get(ii).moveTask();
		}
	}
	
	private void draw()
	{
	    //Canvasの取得(マルチスレッド環境対応のためLock)
	    Canvas canvas = screen.mHolder.lockCanvas();
	    Paint paint = new Paint();

	    //描画処理(Lock中なのでなるべく早く)
	    canvas.drawColor(Color.BLACK);

		for(int ii=0;ii<tl.size();ii++)
		{
			tl.get(ii).drawTask(canvas, paint);
		}

		//コントローラの描画.
		controllerTaskList.drawTask(canvas, paint);
		
		//LockしたCanvasを解放、ほかの描画処理スレッドがあればそちらに。
	    screen.mHolder.unlockCanvasAndPost(canvas);
	}
	
	//--------------------------------------------
	//	画面表示.
	//--------------------------------------------
	public void show()
	{
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
		app.setContentView(screen);
	}
	
	public void tick()
	{
		//移動関数実行.
		move();
		
	    //描画関数実行.
	    draw();
	}
	
	//--------------------------------------------
	//	スクリーンオブジェクトを返す.
	//--------------------------------------------
	public SurfaceScreen getScreen()
	{
		return screen;
	}	
}
