package com.example.loginregistration_web.MyCanvasDrawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyCanvas extends View {

    Context context;
    Paint mPaint;
    Path mPath;

    //new
    Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    //


    Bitmap mBitmap;

    Canvas mCanvas;

    //ArrayList<Draw>

    List<Point> points = new ArrayList() ;

    //new
    public static final int DEFAULT_BG_COLOR= Color.WHITE;
    public static final int DEFAULT_COLOR = Color.BLUE;
    public static final float TOUCH_TOLERANCE = 4;
    public static int BRUSH_SIZE = 10;
    int backgroundColor = DEFAULT_BG_COLOR;
    int currentColor;
    int strokeWidth;
    float mX,mY;


    ArrayList<Draw> paths = new ArrayList<>();






    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        mPaint = new Paint() ;
        //mPath= new Path() ;
        mPaint.setAntiAlias(true) ;
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR );
        mPaint.setStrokeJoin(Paint.Join .ROUND ) ;
        mPaint.setStyle(Paint.Style.STROKE );
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);



    }


    //new
    public void initialize (DisplayMetrics displayMetrics){

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor= DEFAULT_COLOR;
        strokeWidth= BRUSH_SIZE;


        //colour and stroke width
    }




    @Override
    protected void onDraw(Canvas canvas) {

        //new
        canvas.save();
        //

        //super.onDraw(canvas);
        //
        // mcanvas = canvas;

        mCanvas.drawColor(backgroundColor);

        for(Draw draw: paths) {
            mPaint.setColor(draw.color);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setMaskFilter(null);
            mCanvas.drawPath(draw.path, mPaint);
        }



//        mcanvas = canvas;
//        if(cc){
//            mcanvas.drawColor(Color.WHITE);
//
//        }else{
//            mcanvas.drawPath(path,paint);
//
//            //  mcanvas.drawRect(50,50,400,250, paint);
//            cc = false;
//        }


        //new
        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y){

        mPath = new Path();

        Draw draw = new Draw(currentColor, strokeWidth, mPath);
        paths.add(draw);

        mPath.reset();
        mPath.moveTo(x,y);

        mX=x;
        mY=y;
    }

    private void touchMove(float x, float y){
        float dx= Math.abs(x- mX);
        float dy= Math.abs(y - mY);

        if (dx>=TOUCH_TOLERANCE|| dy>= TOUCH_TOLERANCE){
            mPath.quadTo(mX,mY, (x + mX) / 2, (y + mY) / 2);

            mX=x;
            mY=y;
        }

    }

    private void touchUp(){

        mPath.lineTo(mX,mY);

    }






    public void showArray(){

        Log.d("Values","Array: "+ points) ;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos=  event.getX() ;
        float yPos=   event.getY();


        Log.d("Values","X: "+ xPos+ " ,Y: "+ yPos) ;


        points.add(new Point((int)xPos, (int) yPos) ) ;







        // Checks for the event that occurs
        switch (event.getAction() )
        {
            case MotionEvent.ACTION_DOWN:
                // Starts a new line in the path
                touchStart(xPos,yPos);
                invalidate();
                break;
            //return true;
            case MotionEvent .ACTION_MOVE :
                // Draws line between last point and this point
                // mPath.lineTo(xPos,yPos) ;
                touchMove(xPos, yPos);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
            default:
                return false;
        }
        // invalidate() ;    // Indicate view should be redrawn
        return true;    // Indicate we've consumed the touch


        //return super.onTouchEvent(event);
    }

    public void clearCanvas(){
//        cc = true;
//        invalidate();
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));


       /* this worked previously
        mcanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        */

        // clear previous touch coordinates points
        points.clear();
        //clear canvas
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        invalidate();
    }

    public void setStrokeWidth (int width){
        strokeWidth=width;
    }

    public void setColor(int color){
        currentColor = color;
    }


    public void saveImage(){
        int count = 0;

        File sdDirectory = Environment.getExternalStorageDirectory();

        String dirName = "My Data";
        File subDirectory = new File(sdDirectory, dirName);

        if (subDirectory.exists()) {

            Toast.makeText(getContext(), "dir already exists", Toast.LENGTH_LONG).show();



            File[] existing = subDirectory.listFiles();

            for (File file : existing) {

                if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {

                    count++;

                }

            }

        } else {

            subDirectory.mkdir();
            Toast.makeText(getContext(), "dir creating", Toast.LENGTH_LONG).show();



        }

        if (subDirectory.exists()) {

            Toast.makeText(getContext(), "saving file", Toast.LENGTH_LONG).show();



            File image = new File(subDirectory, "/drawing_" + (count + 1) + ".png");
            FileOutputStream fileOutputStream;
            Toast.makeText(getContext(), sdDirectory.getAbsolutePath(), Toast.LENGTH_LONG).show();


            try {

                fileOutputStream = new FileOutputStream(image);

                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();

                Toast.makeText(getContext(), "saved", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {


            } catch (IOException e) {


            }

        }

    }


//    String message= String.format("Coordinates:(%.2f, %.2f)", xPos,yPos);

}