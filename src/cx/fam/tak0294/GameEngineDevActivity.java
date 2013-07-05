package cx.fam.tak0294;

import cx.fam.tak0294.game.GameManager;
import cx.fam.tak0294.game.Task;
import android.app.Activity;
import android.os.Bundle;

public class GameEngineDevActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //GameManager.
        GameManager game = new GameManager(this);
        game.createTaskList(PenguinTask.class);		//0;
        
        for(int ii=0;ii<50;ii++)
        {
            Task t = (Task)game.tl.get(0).createTask();
            t.x = 10*ii;
            t.y = 10*ii;        	
        }
        
        
        game.show();        
    }
}