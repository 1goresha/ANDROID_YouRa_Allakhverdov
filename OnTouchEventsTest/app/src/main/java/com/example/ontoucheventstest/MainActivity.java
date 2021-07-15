package com.example.ontoucheventstest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageView;
    private ViewGroup mMoveLayout;
    private int mX;
    private int mY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Связываемся с нашими объектами, определяя изображение через заданный ViewGroup:
        mMoveLayout = (ViewGroup) findViewById(R.id.move);
        mImageView = (ImageView) mMoveLayout.findViewById(R.id.ImageView);

        //Создаем программно RelativeLayout с параметрами 100*100:
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);

        //Применяем эти параметры к нашему изображению:
        mImageView.setLayoutParams(layoutParams);
        //И настраиваем ему слушателя (обработчик) прикосновений:
        mImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //Определение координат через getRawX() и getRawY() дает
        //координаты по отношению к размерам экрана устройства:
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            //ACTION_DOWN срабатывает при прикосновении к экрану,
            //здесь определяется начальное стартовое положение объекта:
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                mX = X - lParams.leftMargin;
                mY = Y - lParams.topMargin;
                break;

            //ACTION_MOVE обрабатывает случившиеся в процессе прикосновения изменения, здесь
            //содержится информация о последней точке, где находится объект после окончания действия прикосновения ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                        .getLayoutParams();
                layoutParams.leftMargin = X - mX;
                layoutParams.topMargin = Y - mY;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                v.setLayoutParams(layoutParams);
                break;
        }
        return true;
    }

    //Обрабатываем прикосновения к объекту:

}