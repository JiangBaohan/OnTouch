package com.example.demo06_;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.R.attr.bitmap;

/*
画板canvas,画笔paint,手势识别器
* */
public class MainActivity extends AppCompatActivity {

    private Bitmap bmcopy;
    private int startY;
    private int startX;
    private Paint paint;
    private Bitmap bitmap;
    private ImageView iv;
    private Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载原图
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.aa);
        //创建白纸,宽高图片的参数
        bmcopy = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //创建画板
        canvas = new Canvas(bmcopy);
        //创建画笔
        paint = new Paint();
        //在纸上作画
        canvas.drawBitmap(bitmap, new Matrix(), paint);

        iv = (ImageView) findViewById(R.id.iv);
        /*给控件设置手势适配器,可以得到用户再这个控件上所做的手势
        * */
        iv.setOnTouchListener(new View.OnTouchListener() {


            //当用户手再这个控件时,自动回调
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下调用一次
                        Log.d("jbh", "ACTION_DOWN");
                        //获取手指按下时的坐标
                        startX = (int) motionEvent.getX();
                        startY = (int) motionEvent.getY();
                        break;

                    case MotionEvent.ACTION_MOVE://移动反复调用
                        Log.d("jbh", "ACTION_MOVE");
                        int newX = (int) motionEvent.getX();
                        int newY = (int) motionEvent.getY();
                        //再背景图画线
                        canvas.drawLine(startX,startY,newX,newY, paint);
                       //重新赋值,把终点的位置赋值给起点
                        startX=newX;
                        startY=newY;

                        iv.setImageBitmap(bmcopy);
                        break;

                    case MotionEvent.ACTION_UP://松手时的回调
                        Log.d("jbh", "ACTION_UP");
                        break;

                    default:
                        break;
                }
                //事件分发机制
                //true:iv 处理该触摸事件
                //false:iv 不处理触摸事件,事件传递给上一级
                return true;
            }
        });
    }
    public  void red(View view){
        paint.setColor(Color.RED);
    }
    public void green (View view){
        paint.setColor(Color.GREEN);
    }
    //笔刷  创建画笔
    public void brush(View view){
        paint.setStrokeWidth(15);
    }
    public void save(View view){
        canvas.save(Canvas.ALL_SAVE_FLAG);
        File f = new File("mnt/sdcard/0.png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            bmcopy.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
