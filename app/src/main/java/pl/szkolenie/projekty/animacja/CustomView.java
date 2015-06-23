package pl.szkolenie.projekty.animacja;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CustomView extends View{
    static Bitmap  g_d_bmp, c_pion_bmp, c_poziom_bmp, c_skret_dl_bmp, c_skret_dp_bmp, c_skret_lg,
            c_skret_pg_bmp, g_g_bmp, g_l_bmp, g_p_bmp, o_d_bmp, o_g_bmp, o_l_bmp, o_p_bmp;

    static String TAG="Animacja";
    PartOfBodySnake Head;
    ArrayList<PartOfBodySnake> SnakeBody=new ArrayList<PartOfBodySnake>();
    PartOfBodySnake Food=new PartOfBodySnake(true);
    boolean isGameOver=false, isPause=false;
    int ptk=0;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init()
    {
        if(c_pion_bmp==null)
        {
            c_pion_bmp = LoadBitmap( R.drawable.c_pion);
            c_poziom_bmp = LoadBitmap( R.drawable.c_poziom);
            c_skret_dl_bmp = LoadBitmap( R.drawable.c_skret_dl);
            c_skret_dp_bmp = LoadBitmap( R.drawable.c_skret_dp);
            c_skret_lg = LoadBitmap( R.drawable.c_skret_lg);
            c_skret_pg_bmp = LoadBitmap( R.drawable.c_skret_pg);
            g_d_bmp = LoadBitmap( R.drawable.g_d);
            g_g_bmp = LoadBitmap( R.drawable.g_g);
            g_l_bmp = LoadBitmap( R.drawable.g_l);
            g_p_bmp= LoadBitmap( R.drawable.g_p);
            o_d_bmp= LoadBitmap( R.drawable.o_d);
            o_g_bmp= LoadBitmap( R.drawable.o_g);
            o_l_bmp= LoadBitmap( R.drawable.o_l);
            o_p_bmp= LoadBitmap( R.drawable.o_p);
        }
        if(Head !=null) {
            SnakeBody.clear();
            Head.Close();
        }

        Food.x=500;
        Food.y=500;
        Food.r=5;

        Head = new PartOfBodySnake(false);
        this.SnakeBody.add(Head);
        Head.color=Color.GREEN;
        Head.x=200;
        Head.Add().Add().Add().Add().Add().Add().Add().Add().Add().Add();

        isGameOver=false;
        ptk=0;


    }

    Bitmap LoadBitmap(int resource)
    {
        try {
            return BitmapFactory.decodeResource(MainActivity.This.getResources(), resource);
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public void GameOver()
    {
        isGameOver=true;
    }

    public void SetPause()
    {
        this.isPause=!this.isPause;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float tx= event.getX(), ty=event.getY();
        float rx=tx- Head.x;
        float ry=ty- Head.y;
        LOG_Kulka(rx, ry);
        switch(Head.kierunek)
        {
            case Prawa: {
                if (ry > Head.r)
                    Down();
                else
                    if (ry < -Head.r)
                        Up();

                break;
            }
            case Lewa:
                {
                    if (ry > Head.r)
                        Down();
                    else if (ry < -Head.r)
                        Up();

                    break;
                }
            case Gora:
                {
                    if (rx > Head.r)
                        Right();
                    else if (rx < -Head.r)
                        Left();
                    break;
                }
            case Dol: {

                if (rx > Head.r)
                    Right();
                else if (rx < -Head.r)
                    Left();

                break;
            }
        }

        //return super.onTouchEvent(event);
        return true;
    }

    private void LOG_Kulka(float rx, float ry)
    {
        //Log.d("Animacja", Head.kierunek+" rx="+rx+"; ry="+ry);
    }

    //int red, green, blue;

    @Override
    protected void onDraw(Canvas c) {

        //rysowanie pokarmu
        Food.Paint(c);

        //itmap(bitmap,  );
        //rysowanie węża na ekranie
        try {
            for (PartOfBodySnake p : SnakeBody)
                p.Paint(c);
        }
        catch(Exception ex)
        {

        }
        if(isGameOver) {
            Random rand=new Random();
            Paint p = new Paint();
            p.setColor(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
            p.setTextSize(60);
            p.setTextAlign(Paint.Align.CENTER);
            c.drawText("Game Over\n zdobyłeś(aś) "+ptk+" punktów", getWidth()/2, getHeight()/2, p);
        }else
        if(isPause) {
            Paint p = new Paint();
            p.setColor(Color.GRAY);
            p.setTextSize(60);
            p.setTextAlign(Paint.Align.CENTER);
            c.drawText("Pause", getWidth()/2, getHeight()/2, p);
        }else
        {
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(30);
            p.setTextAlign(Paint.Align.LEFT);
            c.drawText("Punkty: "+ptk, 25, 50, p);
        }
        invalidate();
    }

    public void Start() {
        if(!Head.isAlive())
            Head.start();
        else
        {
            init();
            Head.start();
        }
    }

    public void Left() {
        PartOfBodySnake b1=SnakeBody.get(1);
        float ry=Math.max(b1.y,Head.y)-Math.min(b1.y,Head.y);

        if (ry>=Head.r+b1.r)
            Head.Left();
    }

    public void Down() {
        PartOfBodySnake b1=SnakeBody.get(1);
        float rx=Math.max(b1.x,Head.x)-Math.min(b1.x,Head.x);

        if (rx>=Head.r+b1.r)
            Head.Down();
    }

    public void Right() {
        PartOfBodySnake b1=SnakeBody.get(1);
        float ry=Math.max(b1.y,Head.y)-Math.min(b1.y,Head.y);

        if (ry>=Head.r+b1.r)
            Head.Right();
    }

    public void Up() {
        PartOfBodySnake b1=SnakeBody.get(1);
        float rx=Math.max(b1.x,Head.x)-Math.min(b1.x,Head.x);

        if (rx>=Head.r+b1.r)
            Head.Up();
    }


    public class PartOfBodySnake extends Thread {

        PartOfBodySnake parent=null;

        boolean isFood=false, colisionDetect=false, runGame=true;
        float x=50,y=75, r=15, v=r/3;
        int color=Color.parseColor("#0000FF");
        private EKierunek kierunek= EKierunek.Prawa;
        boolean isParent=false;

        public boolean activ = true;
        public boolean Enabled = true;
        int sleepValue = 20;

        //constructor
        public PartOfBodySnake(boolean isFood) {
            this.isFood=isFood;
        }

        @Override
        public void run() {

            while (runGame)
            {
                if (!isGameOver && !isPause) {
                    if (parent == null) {
                        switch (kierunek) {
                            case Prawa:
                                if (x > getWidth() - r)
                                    GameOver();
                                else
                                    x += v;
                                break;
                            case Lewa:
                                if (x < r)
                                    GameOver();
                                else
                                    x -= v;
                                break;
                            case Gora:
                                if (y < r)
                                    GameOver();
                                else
                                    y -= v;
                                break;
                            case Dol:
                                if (y > getHeight() - r)
                                    GameOver();
                                else
                                    y += v;
                                break;
                        }
                    }

                    int i = 0;
                    //ustawianie pozostalych elemenow weza
                    for (PartOfBodySnake p : SnakeBody) {
                        if (p.parent != null)
                            p.refreshPosition();
                        if (i > 1)
                            if (isColision(p, v)) {
                                GameOver();

                            }
                        i++;

                    }

                    if (isColision(Food, 0) && !Food.colisionDetect) {

                        Log.d(TAG, "Kolizja");
                        Food.colisionDetect = true;//bloakada aby nie mozna bylo wykryc kolizji w kolejnej petli

                        PartOfBodySnake last = SnakeBody.get(SnakeBody.size() - 1);//pobieramy ogon
                        last.Add();
                        Random g = new Random();
                        Food.x = g.nextInt(500);
                        Food.y = g.nextInt(500);
                        Random r=new Random();
                        ptk+=r.nextInt(25);

                    } else if (!isColision(Food, 0))
                        Food.colisionDetect = false;
                }
                try {
                    Thread.sleep(sleepValue);
                } catch (InterruptedException e) {

                }
            }
        }

        public PartOfBodySnake Add() {

            PartOfBodySnake n=new PartOfBodySnake(false);
            n.x=this.x;
            n.y=this.y;
            n.r=10;
            n.kierunek=kierunek;
            switch(kierunek)
            {
                case Prawa:
                    n.x-=n.r+this.r;
                    break;
                case Lewa:
                    n.x+=n.r+this.r;
                    break;
                case Gora:
                    n.y+=n.r+this.r;
                    break;
                case Dol:
                    n.y-=n.r+this.r;
                    break;
            }
            n.parent=this;
            SnakeBody.add(n);
            return n;
        }

        public void refreshPosition()
        {
            float rx,ry;
            rx=x-parent.x;
            ry=y-parent.y;

            switch (kierunek) {
                case Prawa:
                    x += v;
                    if(parent.kierunek!=kierunek)
                    {
                        if(x>=parent.x ) {
                            kierunek = parent.kierunek;
                            x=parent.x;
                            if(parent.kierunek==EKierunek.Gora)
                            {
                                y=parent.y+(parent.r+r);
                            }else
                                if(parent.kierunek==EKierunek.Dol) {
                                    y = parent.y - (parent.r + r);
                                }
                        }
                    }
                    break;
                case Lewa:
                    x -= v;
                    if(parent.kierunek!=kierunek)
                    {
                        if(x<=parent.x )
                        {
                            kierunek = parent.kierunek;
                            x=parent.x;
                            if(parent.kierunek==EKierunek.Gora)
                            {
                                y=parent.y+(parent.r+r);
                            }else
                                if(parent.kierunek==EKierunek.Dol)
                                    y=parent.y-(parent.r+r);
                        }
                    }
                    break;
                case Gora:
                    y -= v;
                    if(parent.kierunek!=kierunek)
                    {
                        if(y<=parent.y ) {
                            kierunek = parent.kierunek;
                            y=parent.y;
                            if(parent.kierunek==EKierunek.Lewa)
                            {
                                x=parent.x+(parent.r+r);
                            }else
                            if(parent.kierunek==EKierunek.Prawa)
                                x=parent.x-(parent.r+r);
                        }
                    }
                    break;
                case Dol:
                    y += v;
                    if(parent.kierunek!=kierunek)
                    {
                        if(y>=parent.y ) {
                            kierunek = parent.kierunek;
                            y=parent.y;
                            if(parent.kierunek==EKierunek.Lewa)
                            {
                                x=parent.x+(parent.r+r);
                            }else
                                if(parent.kierunek==EKierunek.Prawa)
                                    x=parent.x-(parent.r+r);
                        }
                    }
                    break;
            }

        }

        public void SetSleepValue(int newValue) {
            this.sleepValue = newValue;
        }

        public void Paint(Canvas c)
        {
            if(isFood)
            {
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setAntiAlias(true);
                c.drawCircle(x, y, v, paint);
            }else {
                Paint paint = new Paint();
                paint.setColor(color);
                paint.setAntiAlias(true);
               // c.drawCircle(x, y, r, paint);
               // Rect scr=new Rect(0,0,0,0 );
              //  RectF dsc=new RectF(x,y, 0, 0);

                c.drawBitmap(g_p_bmp, x+50, y+50, null);
            }
        }

        public void Left() {
            if(kierunek!=EKierunek.Prawa) {
                kierunek = EKierunek.Lewa;
                Log.d(TAG, "LEWA");
            }
        }

        public void Down() {
            if(kierunek!=EKierunek.Gora) {
                kierunek = EKierunek.Dol;
                Log.d(TAG, "DOL" );
            }
        }

        public void Right() {
            if(kierunek!=EKierunek.Lewa) {
                kierunek = EKierunek.Prawa;
                Log.d(TAG, "PRAWA" );
            }
        }

        public void Up() {
            if(kierunek!=EKierunek.Dol) {
                kierunek = EKierunek.Gora;
                Log.d(TAG, "GORA" );
            }
        }

        public boolean isColision(PartOfBodySnake p, float tolerancja)
        {
            if(p==null)
                return false;

            if (this==p)
                return false;

            float py_g=y-(r-tolerancja),
                  py_d=y+(r-tolerancja);

            float px_l=x-(r-tolerancja),
                  px_p=x+(r-tolerancja);



            boolean colision= p.x>px_l && p.x<px_p && p.y>py_g && p.y<py_d;


            return colision;
        }

        public void Close() {
            runGame=false;
        }
    }

    enum  EKierunek{ Prawa, Lewa, Gora, Dol}

}
