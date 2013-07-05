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
	//	�R���g���[��.
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
	//	�R���g���[���t���O�̃��Z�b�g.
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
	//	�R���X�g���N�^.
	//--------------------------------------------
	public GameManager(Activity con)
	{
		app = con;
		screen = new SurfaceScreen(con, this);
		
		//�R���g���[��.
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
		//�R���g���[���̋���.
		controllerTaskList.moveTask();

		for(int ii=0;ii<tl.size();ii++)
		{
			tl.get(ii).moveTask();
		}
	}
	
	private void draw()
	{
	    //Canvas�̎擾(�}���`�X���b�h���Ή��̂���Lock)
	    Canvas canvas = screen.mHolder.lockCanvas();
	    Paint paint = new Paint();

	    //�`�揈��(Lock���Ȃ̂łȂ�ׂ�����)
	    canvas.drawColor(Color.BLACK);

		for(int ii=0;ii<tl.size();ii++)
		{
			tl.get(ii).drawTask(canvas, paint);
		}

		//�R���g���[���̕`��.
		controllerTaskList.drawTask(canvas, paint);
		
		//Lock����Canvas������A�ق��̕`�揈���X���b�h������΂�����ɁB
	    screen.mHolder.unlockCanvasAndPost(canvas);
	}
	
	//--------------------------------------------
	//	��ʕ\��.
	//--------------------------------------------
	public void show()
	{
		app.requestWindowFeature(Window.FEATURE_NO_TITLE);
		app.setContentView(screen);
	}
	
	public void tick()
	{
		//�ړ��֐����s.
		move();
		
	    //�`��֐����s.
	    draw();
	}
	
	//--------------------------------------------
	//	�X�N���[���I�u�W�F�N�g��Ԃ�.
	//--------------------------------------------
	public SurfaceScreen getScreen()
	{
		return screen;
	}	
}
