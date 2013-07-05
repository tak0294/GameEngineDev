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
	//	�R���X�g���N�^.
	//----------------------------------------------------
	//�^�X�N�������l.
	public TaskList(GameManager game, Class<? extends Task> task)
	{
		this.game = game;
		this.taskClassRef = task;
		this.initTaskList();
	}
	
	//�^�X�N���w��.
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
		
		//�^�X�N�z�񏉊���.
		this.task.add(tmp);
		
		this.activeDummyTask = tmp;
		this.activeDummyTask.setNext(this.activeDummyTask);
		this.activeDummyTask.setPrev(this.activeDummyTask);
		
		//�t���[�^�X�N���X�g�̏�����.
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
	//	�^�X�N�쐬.
	//==============================================================
	public Object createTask()
	{
		//�t���[�^�X�N�̋󂫂��Ȃ���ΏI��.
		if(this.freeDummyTask.getNext() == this.freeDummyTask)
		{
			return null;
		}
		
		//�t���[�^�X�N��1���o��.
		Task tmptask = this.freeDummyTask.getNext();

		this.freeDummyTask.setNext(tmptask.getNext());
				
		//�����֐��ƑO��^�X�N�ւ̎Q�Ƃ�ݒ�.
		//tmptask.setHitArea(T, R, B, L);
		//tmptask.setInitFunc();
		
		tmptask.setPrev(this.activeDummyTask.getPrev());
		tmptask.setNext(this.activeDummyTask);
		
		//�V�����^�X�N�̑O��̃^�X�N�Ɋւ���.
		//�O��^�X�N�̃|�C���^��ύX.
		tmptask.getPrev().setNext(tmptask);
		tmptask.getNext().setPrev(tmptask);
		
		return tmptask;
	}

	//==============================================================
	//	�^�X�N�폜.
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
	//	�S�Ẵ^�X�N�̈ړ��֐������s.
	//==============================================================
	public void moveTask()
	{
		Vector<Task> delArray = new Vector<Task>();
		for(TaskIterator t = new TaskIterator(this);t.hasNext();)
		{
			Task task = t.next();
			//�ړ��֐��̖߂�l��false�̏ꍇ�A�Y���^�X�N���폜����.
			if(task.move.func() == false)
			{
				delArray.add(task);
			}
		}
		
		//�폜���X�g�ɓ������^�X�N�̍폜����.
		for(int ii=0;ii<delArray.size();ii++)
		{
			this.deleteTask(delArray.get(ii));
		}
	}

	//==============================================================
	//	�S�Ẵ^�X�N�̕`��֐������s.
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
