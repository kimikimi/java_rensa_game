//インポート
import gameCanvasUtil.*;
import java.util.Random;

/** ゲームクラス。
 *
 * 学生が編集すべきソースコードです。
 */
public class Game extends GameBase
{
    /********* 変数定義はこちらに(global変数) *********/
    // 型 変数名;　の順に書いて定義する
    public static int GAMEBALLS= 200;
    public static int GOALNUMBER= 70;
    public static int gameStateFlag[]=new int[10];
    public static int AAAAAAAA=1;

//    for(int sample=0;sample<10;sample++){
//        gameStateFlag[sample]=0;
//    }
    boolean flagon;
    int chance =2;
    // ボールのX座標
    int ball_x[]= new int[GAMEBALLS];
    // ボールのY座標
    int ball_y[]= new int[GAMEBALLS];
    // ボールのX方向の速度
    int ball_speed_x[]=new int [GAMEBALLS];
    // ボールのY方向の速度
    int ball_speed_y[]=new int [GAMEBALLS];
    int ball_state[]= new int [GAMEBALLS];//1,2,3
    int state2_timer[]= new int [GAMEBALLS];//新追加
    int state2_master[]= new int [GAMEBALLS];
    int ball_kill_num=0;
    // ゲームの状態
    int gameState=0;
    int master=1; //クリックの円の開始半径
    int masterx;
    int mastery;//クリックした位置の座標
    int timer=0; //クリックしてからの秒カウンター
    int endtimer=0;
    //配列で全部違う色にする
    int rcol1[]= new int [GAMEBALLS];
    int rcol2[]= new int [GAMEBALLS];
    int rcol3[]= new int [GAMEBALLS];
    
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    /********* 初期化の手順はこちらに *********/
    public void initGame()
    {
//        gameStateFlag[6] =1;
        flagon = false;
        chance =2;
        timer=0;
        endtimer=0;
        ball_kill_num=0;
        //gameState=0;
        for(int i=0;i<GAMEBALLS;i++)
        {
            rcol1[i] = (int)(Math.random()*255);
            rcol2[i] = (int)(Math.random()*255);
            rcol3[i] = (int)(Math.random()*255);
        
                // ボールの座標を初期化する
            int ran = (int)(Math.random()*400+6);
            int rand = (int)(Math.random()*550+6);
            ball_x[i] =ran;
            ball_y[i] =rand;
        
                // ボールの速度を初期化する
            ball_speed_x[i] = (int)(Math.random() *3+1);
            ball_speed_y[i] = (int)(Math.random() *2+1);
            ball_state[i]=1;
            state2_timer[i]=0;
            state2_master[i]=0;
                //ball_state[i] 1:nomal 2:effect 3:death
        }
        // ボール番号を初期化する
    }
    
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    
    
    /********* 物体の移動等の更新処理はこちらに *********/   
    public void updateGame()
    {
//----------------gamestateの場合分け--------------------
        if(gameState == 0)
        {
            // Enterキーが押されたらゲーム開始
            if(gc.isKeyPushed(gc.KEY_ENTER))
            {
                // ゲーム画面に状態を変更
                gameState = 999;
                // ゲーム開始なので、ゲーム画面の音楽を鳴らす（未実装）
                // gc.playBGM(0);
            }
        }
//--------------ゲームが始まる前のシーンgamestate999-----------------      
        else if(gameState == 999)
        {
            if(gc.isKeyPushed(gc.KEY_ENTER))
            {
                // ゲーム画面に状態を変更
            gameState = 1;
            }
        }
        
//-------------ゲーム画面(最初のステージ)------------------------------------
        else if(gameState == 1)
        {
            // ENTERキーが押されたらポーズ
            if(gc.isKeyPushed(gc.KEY_ENTER))
            {
                // ポーズ画面に状態を変更
                gameState = 2;
                // ポーズ中は曲を停止（未実装）
              //  gc.stopBGM();
               
            }
            if(gc.isMousePushed() && chance >0){
                timer=0;
                flagon=true;
                masterx=gc.getMouseX();
                mastery=gc.getMouseY();
                chance-=1;
            }
            timer++;
            
            if(timer<30)
            {
                master ++;
                
            }
            else{
                    master=0;
                flagon=false;
           }
            if(chance==0)
            {
                endtimer++;
                if(endtimer==150){
                
                    if (ball_kill_num>=GOALNUMBER){gameState=4;}
                    //change gamestate here
                    else {gameState=3;}
                
                }
            }
               // ボールが画面の下を越えるか、画面の上を越えた場合
            for (int i=0;i<GAMEBALLS;i++)
            {
        //-----------------ここからFOR文(壁に反射)-------------------
            ball_y[i] = ball_y[i] + ball_speed_y[i];
                // X方向に ball_speed_x ずつ進める
            ball_x[i] = ball_x[i] + ball_speed_x[i];

                if(ball_y[i] <= 5 && ball_speed_y[i]<0)
                {
                    ball_speed_y[i] = -ball_speed_y[i];
                }
                if(ball_y[i]> 475 && ball_speed_y[i]>0)
                {
                    ball_speed_y[i] = -ball_speed_y[i];
                }
                if(ball_x[i] <= 5 && ball_speed_x[i]<0)
                {
                    ball_speed_x[i] = -ball_speed_x[i];
                }
                if(ball_x[i] > 635 && ball_speed_x[i]>0)
                {
                    ball_speed_x[i] = -ball_speed_x[i];
                }
                
               //----------当たり判定→未実装------
                if(ball_state[i]==1)
                {
                    if(flagon)
                    {
                        
                    if( gc.checkHitCircle(ball_x[i], ball_y[i],7,masterx , mastery, master))
                        {
                                ball_state[i] =2;
                            
                                ball_kill_num += 1;
                            }
                        }
                    
                }
                if(ball_state[i]==2){
                    state2_timer[i]++;
                    if(state2_timer[i]<30){
                        state2_master[i]++;
                    }else {ball_state[i]=3;}
                    
                    for(int jj=0;jj<GAMEBALLS;jj++){
                        if( gc.checkHitCircle(ball_x[jj], ball_y[jj],7,ball_x[i] , ball_y[i], state2_timer[i]) && ball_state[jj]==1)
                        {
                            ball_state[jj] =2;
                            
                            ball_kill_num += 1;
                        }

                    
                    }
                }
                else if (ball_state[i]==3){
                
                }
            }
        //----------------for文の終わり------------------------
           
        }
//---------------------- gamestate1(index)終了----------------------------------------
        
        
        else if( gameState == 2)
        {
            // Enterキーが押されたらゲーム再開
            if(gc.isKeyPushed(gc.KEY_ENTER))
            {
                // ゲーム画面に状態を変更
                gameState = 1;
                // ゲーム開始なので、再び曲を再生(未実装)
               // gc.playBGM(0);
            }
        }
    //-------------- ゲームオーバー画面-----------------
        else if(gameState == 3)
        {
            if(gc.isKeyPushed(gc.KEY_ENTER))
            {
                // ゲーム画面に状態を変更
                gameState = 0;
                // ゲーム開始なので、再び曲を再生(未実装)
                // gc.playBGM(0);
            }
        }
        
    // --------------クリアー画面----------------------
        else if(gameState == 4)
        {
        }
    //--------------クリア出来なかったけど復活画面---------
    }
    
    
    
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    
    
    
    
    
    /********* 画像の描画はこちらに *********/
    public void drawGame()
    {
        // 画面を白で塗りつぶします
        gc.clearScreen();
        
//-----------------------gamestate0(start)----------------------------------
        
        if(gameState == 0)
        {
            // タイトル画面の文字を描画
            gc.setColor(0, 0, 200);
            gc.drawString("Pandemic", 200, 150);
            gc.setColor(0, 0, 0);
            gc.drawString("Push Enter" , 260, 250);
        }
//------------------------gamestate999(zenza)------------------------
        else if (gameState==999)
        {
            gc.drawImage(0, 100, -10);
            gc.setColor(0, 0, 0);
            //黒い背景を下200px
            gc.fillRect(0,280,680,200);
            gc.setColor(255, 255, 255);
            gc.drawString("ClickでTrendを作れるのじゃぞ！", 100, 330);
            gc.drawString("チャンスは2回までじゃ！", 200, 380);
            gc.drawString("75Trendを獲得してブームを巻き起こせ！",170, 430);
        }

//-----------------------gamestate1(index1)----------------------------------
        
        else if(gameState==1){
        int bb;
            for (bb=0;bb<GAMEBALLS;bb++){
        // ball_num の数字の画像を、(ball_x, ball_y)に描画します
                if(ball_state[bb] != 3){
                    gc.setColor(rcol1[bb],rcol2[bb],rcol3[bb]);
                    gc.fillCircle(ball_x[bb], ball_y[bb],7);
                    if(ball_state[bb] ==2){
                        gc.setColor(rcol1[bb],rcol2[bb],rcol3[bb]);
                        gc.fillCircle(ball_x[bb], ball_y[bb],state2_master[bb]);
                    }
                }
            }

        //gc.drawImage(0, ball_x[i], ball_y[i]);
    
           // gc.setColor(0,0,0);
            
            gc.fillCircle(masterx,mastery,master);
            gc.setColor(0,0,0);
            gc.drawString("Trend"+ball_kill_num,10,10);
            gc.drawString("Chance"+chance,10,30);

        
        }
//-----------------------gamestate2(pause)----------------------------------
        else if(gameState == 2)
        {
            // ポーズ画面の文字を描画
            gc.setColor(0, 0, 0);
            gc.drawString("PAUSE", 280, 200);
        }
    
//-----------------------gamestate3(gameover)----------------------------------
        else if(gameState == 3)
        {gc.drawImage(0, 150, -10);
            gc.setColor(0, 0, 0);
            gc.drawString("Trend"+ball_kill_num,10,10);
            gc.drawString("Chance"+chance,10,30);
            gc.fillRect(0,280,680,200);
            gc.setColor(255, 255, 255);
            gc.drawString("何をやっておる！！出直してこい！", 100, 330);
            initGame();
                    }
//-----------------------gamestate4(clear)----------------------------------
        else if(gameState == 4)
        { gc.drawImage(0, 150, -10);
            gc.setColor(0, 0, 0);
            gc.drawString("Trend"+ball_kill_num,10,10);
            gc.drawString("Chance"+chance,10,30);
            gc.fillRect(0,280,680,200);
            gc.setColor(255, 255, 255);
            gc.drawString("よくやった！1人前だ！", 100, 330);
            if(gc.isKeyPushed(gc.KEY_ENTER))
            {
                if(gameStateFlag[6] != 1)
                {
                 gameState=6;
                }
            }
        }
//-----------------------gamestate5(false)----------------------------------
        else if(gameState == 5)
        {
        
        }
//-----------------------gamestate6(stage2)----------------------------------
        else if(gameState == 6)
        {
//            AAAAAAAA++;
            initGame();
            GAMEBALLS-=60;
            gameStateFlag[gameState]=1;
//            if(){}
            gameState=1;
        }
//-----------------------gamestate7(stage3)----------------------------------
        else if(gameState == 7)
        {
            initGame();
            GOALNUMBER+=20;
            gameStateFlag[gameState]=1;
            gameState=1;
            
        }
//-----------------------gamestate8(stage4)----------------------------------
    }
    /********* 終了時の処理はこちらに *********/
    public void finalGame() {}
}