package pl.szkolenie.projekty.animacja;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    public static MainActivity This;
    CustomView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        This=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button StartBtn=(Button)findViewById(R.id.StartBtn);
        gameView=(CustomView)findViewById(R.id.GameView);

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.Start();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void PauseClick(View view) {
        gameView.SetPause();
    }
}
