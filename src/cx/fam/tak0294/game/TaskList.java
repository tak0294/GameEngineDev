package cx.fam.tak0294.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TaskList
{
	private GameManager game;
	private Class<? extends Task> taskClassRef;
	private Vector<Task> task = new Vector<Task>();
	public Task activeDummyTask;
	private Task freeDummyTask;
	private int TASK_NUM = 128;
	
	//----------------------------------------------------
	//	コンストラクタ.
	//----------------------------------------------------
	//タスク数初期値.
	public TaskList(GameManager game, Class<? extends Task> task)
	{
		this.game = game;
		this.taskClassRef = task;
		this.initTaskList();
	}
	
	//タスク数指定.
	public TaskList(GameManager game, Class<? extends Task> task, int TaskNum)
	{
		this.TASK_NUM = TaskNum;
		this.game = game;
		this.taskClassRef = task;
		this.initTaskList();
	}
	
	private void initTaskList()
	{
		Task tmp = null;
		
		Constructor<? extends Task> constructor;
		try {
			constructor = taskClassRef.getConstructor(GameManager.class);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

		
		try {
			tmp = constructor.newInstance(game);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}		
		
		//タスク配列初期化.
		this.task.add(tmp);
		
		this.activeDummyTask = tmp;
		this.activeDummyTask.setNext(this.activeDummyTask);
		this.activeDummyTask.setPrev(this.activeDummyTask);
		
		//フリータスクリストの初期化.
		for(int ii=1;ii<TASK_NUM+2;ii++)
		{
			try {
				this.task.add(constructor.newInstance(game));
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		
		this.freeDummyTask = this.task.get(1);
		
		for(int ii=1;ii<TASK_NUM+1;ii++)
		{
			this.task.get(ii).setNext(this.task.get(ii+1));
		}
		
		this.task.get(TASK_NUM+1).setNext(this.freeDummyTask);
	}

	//==============================================================
	//	タスク作成.
	//==============================================================
	public Object createTask()
	{
		//フリータスクの空きがなければ終了.
		if(this.freeDummyTask.getNext() == this.freeDummyTask)
		{
			return null;
		}
		
		//フリータスクを1つ取り出す.
		Task tmptask = this.freeDummyTask.getNext();

		this.freeDummyTask.setNext(tmptask.getNext());
				
		//処理関数と前後タスクへの参照を設定.
		//tmptask.setHitArea(T, R, B, L);
		//tmptask.setInitFunc();
		
		tmptask.setPrev(this.activeDummyTask.getPrev());
		tmptask.setNext(this.activeDummyTask);
		
		//新しいタスクの前後のタスクに関して.
		//前後タスクのポインタを変更.
		tmptask.getPrev().setNext(tmptask);
		tmptask.getNext().setPrev(tmptask);
		
		return tmptask;
	}

	//==============================================================
	//	タスク削除.
	//==============================================================
	public void deleteTask(Task t)
	{
		t.getPrev().setNext(t.getNext());
		t.getNext().setPrev(t.getPrev());
		t.initValue();

		t.setNext(freeDummyTask.getNext());
		this.freeDummyTask.setNext(t);
	}

	//==============================================================
	//	全てのタスクの移動関数を実行.
	//==============================================================
	public void moveTask()
	{
		Vector<Task> delArray = new Vector<Task>();
		for(TaskIterator t = new TaskIterator(this);t.hasNext();)
		{
			Task task = t.next();
			//移動関数の戻り値がfalseの場合、該当タスクを削除する.
			if(task.move.func() == false)
			{
				delArray.add(task);
			}
		}
		
		//削除リストに入ったタスクの削除処理.
		for(int ii=0;ii<delArray.size();ii++)
		{
			this.deleteTask(delArray.get(ii));
		}
	}

	//==============================================================
	//	全てのタスクの描画関数を実行.
	//==============================================================
	public void drawTask(Canvas canvas, Paint paint)
	{
		for(TaskIterator t = new TaskIterator(this);t.hasNext();)
		{
			Task task = t.next();
			task.draw.func(canvas, paint);
		}
	}

}
