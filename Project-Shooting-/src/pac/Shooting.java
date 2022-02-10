package pac;

import java.io.File;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class Shooting extends Application {

    GraphicsContext g;
    int x, y;
    List<Bullet> bullets;
    long bulletCoolTimeFirst = 0;
    long bulletCoolTimeEnd = 0;
    List<Enemy> enemys;
    Color enemyColor;
    int level;
    int score=0;
    int finalScore;
    drawMode mode;
    Color charaColor;
    AnimationTimer timer;
    boolean isKeyPress[]={false,false,false,false};
    int moveSpeed;
    int radias;

    MediaPlayer se_mps[] = new MediaPlayer[6];

    MediaPlayer bgm_mp = null;

    @Override
    public void start(Stage stage) {
        enemyColor=Color.RED;
        Group root = new Group();
        stage.setTitle("Shooting");
        stage.setResizable(false);
        this.x = 10;
        this.radias=5;
        this.y = 150;
        this.level = 1;
        this.score = 0;
        this.mode = drawMode.GAME;
        this.moveSpeed=2;
        charaColor=Color.GOLD;
        this.bullets = new ArrayList<Bullet>();
        this.enemys = new ArrayList<Enemy>();
        Canvas cvs = new Canvas(400, 300);
        root.getChildren().add(cvs);
        this.g = cvs.getGraphicsContext2D();

        Scene scene = new Scene(root, 400, 300, Color.WHITE);
        stage.setScene(scene);
        scene.setOnKeyPressed(this::keyPressed);
        scene.setOnKeyReleased(this::keyReleased);

        bgm_mp = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/BGM/ShootingBGM.wav").toURI().toString()));

        bgm_mp.setCycleCount(MediaPlayer.INDEFINITE);

        bgm_mp.setAutoPlay(true);

        se_mps[0] = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/SE/se_shooting.wav").toURI().toString()));
        se_mps[1] = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/SE/爆発2.mp3").toURI().toString()));
        se_mps[2] = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/SE/回復魔法1.mp3").toURI().toString()));
        se_mps[3] = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/SE/レベルアップ.mp3").toURI().toString()));
        se_mps[4] = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/SE/呪いの旋律.mp3").toURI().toString()));
        se_mps[5] = new MediaPlayer(new Media(new File("C:/javas/Project-Shooting-/SE/重力魔法1.mp3").toURI().toString()));

        stage.show();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                if (score > getLevel() * 400) {
                    setLevel(getLevel() + 1);
                    if(getLevel()>3){
                        enemyColor=Color.GREEN;
                        if(radias<6)radias+=5;
                        if(getLevel()>5){
                            enemyColor=Color.CYAN;
                            if(radias<11)radias+=5;
                            if(getLevel()>8){
                                enemyColor=Color.WHITE;
                                radias++;
                            }
                        }
                    }

                    se_mps[2].play();
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            System.out.print("HE");
                            e.printStackTrace();
                        }
                        se_mps[2].stop();
                    }).start();
                }
                if (getLevel() > 10) {
                    if(score>-1)finalScore=score;
                    score=-100;
                    bgm_mp.stop();
                    setIsLoop(false, winOrLose.WIN);
                    se_mps[3].play();
                }
                if(isKeyPress[0]){
                    x -= moveSpeed;
                    if(x<0){
                        x=0;
                    }
                }
                if(isKeyPress[1]){
                    x += moveSpeed;
                    if(x>390){
                        x=390;
                    }
                }
                if(isKeyPress[2]){
                    y -= moveSpeed;
                    if(y<0){
                        y=0;
                    }
                }
                if(isKeyPress[3]){
                    y += moveSpeed;
                    if(y>290){
                        y=290;
                    }
                }

                List<Integer> iBox = new ArrayList<>();
                if (enemys.size() < getLevel() * 3 - 2) {
                    enemys.add(new Enemy(new java.util.Random().nextInt(40) + 350,
                            new java.util.Random().nextInt(250) + 10, getLevel()*3));
                }
                for (int i = 0; i < enemys.size(); i++) {
                    if (checkHitBody(enemys.get(i))) {
                        if(!(charaColor==Color.BLACK)){
                            charaColor=Color.BLACK;
                            if(score>-1)finalScore=score;
                            score=-100;
                            bgm_mp.stop();
                            se_mps[1].play();
                            new Thread(() -> {
                                try {
                                    Thread.sleep(300);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                                se_mps[1].stop();
                                setIsLoop(false, winOrLose.LOSE);
                                se_mps[4].setVolume(300);
                                se_mps[4].play();
    
                            }).start();
                        }        
                    } else {
                        enemys.get(i).x -= 1;
                    }
                    if (checkHitBullet(enemys.get(i), bullets)) {
                        enemys.get(i).hp--;
                        se_mps[0].play();
                        new Thread(() -> {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            se_mps[0].stop();
                        }).start();
                        score += 10;
                        if (enemys.get(i).hp-- < 0) {
                            iBox.add(i);
                        }
                    }
                    if (enemys.get(i).x < 0) {
                        iBox.add(i);
                    }
                }
                if(!iBox.isEmpty()){
                    for (Integer i : iBox) {
                        enemys.remove(enemys.get(i));
                    }    
                }
                draw(getMode());
            }
        };
        timer.start();
    }

    public static void main(String[] args) {

        launch();
    }

    public void draw(drawMode mode){

        g.setFill(Color.BLACK);
        g.fillRect(0,0,400,300);
        switch(mode){
            case GAME:
                g.setFill(Color.WHITE);
                g.setFont(new Font(10));
                g.fillText("LEVEL:"+this.level, 360, 20);
                List<Integer> iBox = new ArrayList<>();
                List<Integer> jBox = new ArrayList<>();
                g.setFill(charaColor);
                g.fillRect(this.x,this.y,10,10);
                for(int i=0;i<this.bullets.size();i++){
                    g.fillOval(bullets.get(i).x, bullets.get(i).y, bullets.get(i).radius, bullets.get(i).radius);
                    System.out.println("i="+i+":bullet.x="+bullets.get(i).x);
                    if(bullets.get(i).x<400){
                        bullets.get(i).x += 3;
                    }else{                     
                        iBox.add(i);
                    }
                }
                if(!iBox.isEmpty()){
                    for(Integer i:iBox){
                        if(bullets.size()<i)System.out.println(bullets.size());
                        bullets.remove(bullets.get(i));
                    }
                }   
                
                g.setFill(enemyColor);
                for(int j=0;j<this.enemys.size();j++){
                    g.fillRect(enemys.get(j).x, enemys.get(j).y,10,10);
                    if(enemys.get(j).x>0){
                        enemys.get(j).x -= 2;
                    }else{                     
                        iBox.add(j);
                    } 
                }
                if(!jBox.isEmpty()){
                    for(Integer j:jBox){
                        bullets.remove(enemys.get(j));
                    }
                }  
                this.bulletCoolTimeEnd=System.currentTimeMillis();
                break;
            case CLEAR:
                enemys.clear();
                bullets.clear();
                this.level=1;
                g.setFill(Color.YELLOW);
                g.setFont(new Font(50));
                g.fillText("GAMECLEAR", 80, 100);
                g.fillText("SCORE:"+this.finalScore,80,200);
                g.setFont(new Font(25));
                g.fillText("Press Esc key to close the Window",18,290);
                timer.stop();
                break;
            case GAMEOVER:
                enemys.clear();
                bullets.clear();
                this.level=1;
                g.setFill(Color.RED);
                g.setFont(new Font(50));
                g.fillText("GAMEOVER", 80, 100);
                g.fillText("SCORE:"+this.finalScore,80,200);
                g.setFont(new Font(25));
                g.fillText("Press Esc key to close the Window",18,290);
                timer.stop();
                break;
        }
        
    }

    private void keyPressed(KeyEvent e) {

        switch (e.getCode()) {
            case A:
                if (this.x > 0)this.isKeyPress[0]=true;
                break;
            case D:
                if (this.x < 390)this.isKeyPress[1]=true;
                break;
            case W:
                if (this.y > 0)this.isKeyPress[2]=true;
                break;
            case S:
                if (this.y < 290)this.isKeyPress[3]=true;
                break;
            case ENTER:

                this.x = 10;
                this.y = 150;
                break;
            case P:
            case E:
                if (this.bulletCoolTimeEnd - this.bulletCoolTimeFirst > 100) {
                    this.bullets.add(new Bullet(this.x + 11, this.y + 3, radias));
                    }
                    this.bulletCoolTimeFirst = System.currentTimeMillis();
                break;
            //ここから下は、テスト用
            case C:
                //弾の数を表示する
                System.out.println(bullets.size());
                break;
            case ESCAPE:
                System.exit(0);
            case L:
                this.level++;
                break;
            case X:
            //すべての敵を一掃する。ただしスコアに入らない
                se_mps[5].play();
                try {
                    Thread.sleep(1000);
                } catch (Exception el) {
                    el.getMessage();
                }
                se_mps[5].stop();
                this.enemys.clear();
                break;
            case U:
                score += 100;
                break;
            default:
                break;
        }
    }
    private void keyReleased(KeyEvent e) {
		switch(e.getCode()) {
		    case A:
			    isKeyPress[0] = false;
			    break;
            case D:
			    isKeyPress[1] = false;
			    break;
            case W:
			    isKeyPress[2] = false;
			    break;
			case S:
			    isKeyPress[3] = false;
			    break;
		    default:
			    break;
		}
	}

    private int getLevel() {
        return this.level;
    }

    private int getX() {
        return this.x;
    }

    private int getY() {
        return this.y;
    }

    private void setLevel(int lev) {
        this.level = lev;
    }

    private void setIsLoop(boolean isLoop, winOrLose result) {
        if (!isLoop) {
            switch (result) {
                case WIN:
                    this.mode = drawMode.CLEAR;
                    break;
                case LOSE:
                    this.mode = drawMode.GAMEOVER;
                    break;
                default:
                    break;
            }
        }
    }

    private drawMode getMode() {
        return this.mode;
    }

    private boolean checkHitBody(Enemy ene) {
        if ((getX() + 5 - (ene.x + 5)) * (getX() + 5 - (ene.x + 5))
                + (getY() + 5 - (ene.y + 5)) * (getY() + 5 - (ene.y + 5)) < 100) {
            return true;
        }
        return false;
    }

    private boolean checkHitBullet(Enemy ene, List<Bullet> buls) {
        for (Bullet bu : buls) {
            if ((bu.x - (ene.x + radias)) * (bu.x - (ene.x + radias)) + (bu.y - (ene.y + radias)) * (bu.y - (ene.y + radias)) < 105+(radias*radias)) {
                return true;
            }
        }
        return false;
    }
}

class Bullet {
    int x, y;
    int radius;

    Bullet(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.radius = r;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.radius);
    }
}

class Enemy {
    int x, y;
    int hp;

    Enemy(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.hp);
    }

}

enum drawMode {
    GAME,
    GAMEOVER,
    CLEAR,
}

enum winOrLose {
    WIN,
    LOSE,
    NONE,
}
//end