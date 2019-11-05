package com.example.snake.Views;

import android.app.Activity;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.snake.Controller.SnakeController;
import com.example.snake.Models.SnakeModel;

import java.util.Random;


public class SnakeView extends View {

    private static final String TAG = "SnakeView";

    //grid habilitado
    private boolean isGrid;

    private Paint paint, borderPaint, gridPaint;

    //larg, alt e espaco de pontuacao da tela para config inicial
    private int screenW, screenH, topGap;

    //num de blocos de larg na fase
    private final int NUM_BLOCK = 20;

    //tam em pixels de cada bloco da fase
    private int blockSize, numBlockW, numBlockH;

    //snake e ponto
    private Rect snakeHead, snakeBody, snakeTail, snakePoint;

    //coordenadas e infos dos objetos do jogo
    private int [] snakeX;
    private int [] snakeY;
    private int snakeL, pointX, pointY;

    //movimentacao do snake: 0 = cima, 1 = dir, 2 = baixo, 3= esq
    private int movDirection=0;

    SnakeModel model;
    SnakeController controller;

    public SnakeView(Context context) {
        super(context);

        //comprimento do snake
        snakeX = new int[1000];
        snakeY = new int[1000];

        paint = new Paint();
        borderPaint = new Paint();
        gridPaint = new Paint();

        model = new SnakeModel();
        controller = new SnakeController(model);
    }

    public void configDisplay(Context context, boolean grid){
        Log.d(TAG, "SnakeView CONFIGDISPLAY()");

        //recebe larg e alt da tela do dispositivo
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenW = size.x;
        screenH = size.y;
        topGap = screenH/14;//espaco para pontuacao
        isGrid = grid;//opcao fase com grid

        //tam de cada bloco da fase
        blockSize = screenW/NUM_BLOCK;

        numBlockW = NUM_BLOCK;//blocos de alt e larg da fase
        numBlockH = ((screenH - topGap ))/blockSize;//um bloco no topo para pontuacao

        //carregar snake, pontos e paint
        getSnake();
        getPoint();
        rectSnakePoint();
        configPaint();
    }

    public void draw(){
        invalidate();
        requestLayout();
    }

    protected void onDraw(Canvas canvas){
        //fundo da fase
        canvas.drawColor(Color.argb(255, 150, 210, 1));

        //pontuacao
        canvas.drawText("Score:" + controller.getSnakeScore(), screenW/3, topGap-60, paint);

        //limites da fase: TOPO
        canvas.drawLine(1,topGap,screenW-10,topGap,paint);

        //DIREITA
        canvas.drawLine(screenW-10,topGap,screenW-10,
                topGap+(numBlockH*blockSize),paint);
        //ESQUERDA
        canvas.drawLine(10,topGap, 10,topGap+(numBlockH*blockSize), paint);

        //BASE
        canvas.drawLine(screenW-10,topGap+(numBlockH*blockSize),
                1,topGap+(numBlockH*blockSize),paint);

        //grid horizontal e vertical se habilitado
        if(isGrid){
            for (int i = 1; i < numBlockH;i++){
                canvas.drawLine(1,topGap+blockSize*i,screenW-1,
                        topGap+blockSize*i, gridPaint);
                canvas.drawLine(1 + blockSize * i, topGap, 1 + blockSize * i,
                        topGap + (numBlockH * blockSize), gridPaint);
            }
        }

        //atualiza coordenadas de obj do jogo
        updateGameObjects();

        //desenha cabeca, corpo, cauda e pontos
        canvas.drawRect(snakeHead, paint);
        for(int i = 1; i < snakeL-1;i++){
            snakeBody.set(snakeX[i]*blockSize, (snakeY[i]*blockSize)+topGap,
                    snakeX[i]*blockSize+ blockSize, (snakeY[i]*blockSize)+topGap+ blockSize);
            canvas.drawRect(snakeBody, paint);
            canvas.drawRect(snakeBody, borderPaint);
        }
        canvas.drawRect(snakeBody, paint);
        canvas.drawRect(snakeBody, borderPaint);
        canvas.drawRect(snakePoint, paint);
        super.onDraw(canvas);
    }

    public void updateGame() {
        //Log.d(TAG, "SnakeView UPDATE_GAME()");

        checkPoint();//se snake capturar ponto

        moveSnake();//movimentar snake

        directSnake();//direcionar snake

        checkCollision();//checa colisao

        if(controller.getSnakeDead()){
            //reinicia
            controller.setSnakeScore(0);
            getSnake();
        }
    }

    private void getSnake(){
        controller.setSnakeDead(false);
        controller.setSnakeLength(3);
        snakeL = controller.getSnakeLength();
        //cabeca, corpo e cauda do snake ao centro da fase
        snakeX[0] = numBlockW/2;
        snakeY[0] = numBlockH /2;

        snakeX[1] = snakeX[0]-1;
        snakeY[1] = snakeY[0];

        snakeX[1] = snakeX[1]-1;
        snakeY[1] = snakeY[0];
    }

    private void getPoint(){
        //coord randomica dos pontos
        Random random = new Random();
        pointX = random.nextInt(numBlockW-1)+1;
        pointY = random.nextInt(numBlockH-1)+1;
    }

    private void rectSnakePoint(){
        //snake eh formado por um conjunto de retangulos
        snakeHead = new Rect(snakeX[0], snakeY[0],
                snakeX[0]+blockSize, snakeX[0]+blockSize);
        snakeBody = new Rect(snakeX[1], snakeY[1],
                snakeX[1]+blockSize, snakeX[1]+blockSize);
        snakeTail = new Rect(snakeX[1], snakeY[1],
                snakeX[1]+blockSize, snakeX[1]+blockSize);
        snakePoint = new Rect(pointX, pointY,
                pointX+blockSize, pointY+blockSize);
    }

    private void configPaint(){
        paint.setColor(Color.BLACK);//cor da tinta
        paint.setStrokeWidth(10);//borda em pixel
        paint.setTextSize(topGap/2);//tam pontuacao
        paint.setStyle(Paint.Style.FILL);//estilo preencher
        borderPaint.setColor(Color.argb(255, 150, 210, 1));
        borderPaint.setStrokeWidth(5);//borda em pixel
        borderPaint.setStyle(Paint.Style.STROKE);//estilo borda
        gridPaint.setColor(Color.argb(105, 0, 0, 0));//cor da tinta
        gridPaint.setStrokeWidth(5);//borda em pixel
    }

    private void updateGameObjects(){
        snakeHead.set(snakeX[0]*blockSize, (snakeY[0]*blockSize)+topGap,
                snakeX[0]*blockSize + blockSize, (snakeY[0]*blockSize)+topGap+blockSize);
        snakeBody.set(snakeX[snakeL-1]*blockSize, (snakeY[snakeL-1]*blockSize)+topGap,
                snakeX[snakeL-1]*blockSize+blockSize, (snakeY[snakeL-1]*blockSize)+topGap + blockSize);
        snakePoint.set(pointX*blockSize, (pointY*blockSize)+topGap, pointX*blockSize+blockSize,
                (pointY*blockSize)+topGap+blockSize);
    }

    private void checkPoint(){
        if(snakeX[0] == pointX && snakeY[0] == pointY){
            //crescer snake
            snakeL++;
            controller.setSnakeLength(snakeL);

            getPoint();//respawnar ponto

            controller.addSnakeScore();//incrementar pontuacao
        }
    }

    private void moveSnake(){
        for(int i=snakeL; i >0 ; i--){
            snakeX[i] = snakeX[i-1];
            snakeY[i] = snakeY[i-1];
        }
    }

    private void directSnake(){
        switch (movDirection){
            case 0://cima
                snakeY[0]  --;
                break;

            case 1://dir
                snakeX[0] ++;
                break;

            case 2://baixo
                snakeY[0] ++;
                break;

            case 3://esq
                snakeX[0] --;
                break;
        }
    }

    private void checkCollision(){
        //checa colisao com parede
        if(snakeX[0] == -1 || snakeX[0] >= numBlockW || snakeY[0] == -1 || snakeY[0] == numBlockH){
            controller.setSnakeDead(true);
        }
        //checa colisao com proprio corpo
        for (int i = snakeL-1; i > 0; i--) {
            if ((i > 4) && (snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                controller.setSnakeDead(true);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenW / 2) {
                    movDirection ++;//direita
                    if(movDirection == 4) {
                        movDirection = 0;
                    }
                } else {
                    movDirection--;//esquerda
                    if(movDirection == -1) {
                        movDirection = 3;
                    }
                }
        }
        return true;
    }
}