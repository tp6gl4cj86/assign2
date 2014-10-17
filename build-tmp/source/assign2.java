import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class assign2 extends PApplet {

Froggy froggy;
Car [] car;
Wood [] wood;
Turtle[] turtle;

PImage imgFrog;
int LIFE  = 3;
int STATE = 0;

final int GAME_START = 0;
final int GAME_RUN   = 1;
final int FROG_DIE   = 2;
final int GAME_LOSE  = 3;
final int GAME_WIN   = 4;

int game_timer;
int die_timer;

public void setup() 
{
	size(480, 800);
	textFont(createFont("fonts/Square_One.ttf", 20));
	initImage();
	initObject();
}

public void initImage()
{
	imgFrog = loadImage("data/frog.png");
}

public void initObject()
{
	froggy = new Froggy();

	car = new Car[8];
	car[0] = new Car(Car.MODE_LTruck   , 4, 0);
	car[1] = new Car(Car.MODE_RightCar1, 5, 1);
	car[2] = new Car(Car.MODE_LeftCar1 , 2, 2);
	car[3] = new Car(Car.MODE_LeftCar1 , 2, 2, -300);
	car[4] = new Car(Car.MODE_RTruck   , 6, 3);
	car[5] = new Car(Car.MODE_LeftCar2 , 4, 4);
	car[6] = new Car(Car.MODE_RightCar2, 3, 5);
	car[7] = new Car(Car.MODE_RightCar2, 3, 5, 300);

	wood = new Wood[7];
	wood[0] = new Wood(Wood.MODE_Log_116x20, 3, 0);
	wood[1] = new Wood(Wood.MODE_Log_116x20, 3, 0, 250);
	wood[2] = new Wood(Wood.MODE_Log_180x20, 1, 2);
	wood[3] = new Wood(Wood.MODE_Log_180x20, 1, 2, 400);
	wood[4] = new Wood(Wood.MODE_Log_84x20 , 2, 4);
	wood[5] = new Wood(Wood.MODE_Log_84x20 , 2, 4, 300);
	wood[6] = new Wood(Wood.MODE_Log_84x20 , 2, 4, 600);

	turtle = new Turtle[17];
	turtle[0] = new Turtle(2, 5, 1000);
	turtle[1] = new Turtle(2, 5, 1000, 30);
	turtle[2] = new Turtle(2, 5, 1500, 300);
	turtle[3] = new Turtle(2, 5, 1500, 330);
	turtle[4] = new Turtle(2, 5, 1500, 360);
	turtle[5] = new Turtle(4, 3, 1300);
	turtle[6] = new Turtle(4, 3, 1300, 30);
	turtle[7] = new Turtle(4, 3, 1300, 60);
	turtle[8] = new Turtle(4, 3, 1300, 90);
	turtle[9] = new Turtle(4, 3, 1300, 230);
	turtle[10] = new Turtle(4, 3, 1300, 260);
	turtle[11] = new Turtle(3, 1, 1600);
	turtle[12] = new Turtle(3, 1, 1600, 30);
	turtle[13] = new Turtle(3, 1, 1600, 100);
	turtle[14] = new Turtle(3, 1, 1600, 130);
	turtle[15] = new Turtle(3, 1, 1600, 250);
	turtle[16] = new Turtle(3, 1, 1600, 280);
}

public void draw()
{
	switch (STATE)
	{
		case GAME_START : runGAME_START(); break;	
		case GAME_RUN   :
		case FROG_DIE   : runGAME_RUN();   break;
		case GAME_LOSE  : runGAME_LOSE();  break;
		case GAME_WIN   : runGAME_WIN();   break;
	}
}

public void runGAME_START()
{
	background(0x33, 0x6f, 0x04);	
	fill(0xff, 0xff, 0xff);
	textAlign(CENTER, CENTER);
	textSize(32);
	text("PRESS ENTER", width/2, height/2);
}

public void runGAME_RUN()
{
	background(0x33, 0x6f, 0x04);	

	// pond
	fill(0x06, 0x0c, 0x3f);
 	rect(0, 32, width, 32 + 45 * 7);				

 	// car back
 	fill(0);
 	rect(0, height - 60 - 45 * 7 + 0 * 45, width, 45 * 6);	

 	// time layout
 	fill(0);
 	rect(0, height-60, width, 60);	
 	// time
 	fill(0xff, 0xe6, 0x6f);println();
 	rect(100 + ((width-280)*(millis()-game_timer))/60000, height-30, width-180, 30);	
 	fill(0);
 	rect(width-80, height-30, 80, 30);
	fill(0xff, 0xe6, 0x6f);
	textSize(16);
	text("TIME", width-80, height-30, 80, 30);

 	// LIFE
 	for(int i=LIFE-1; i>=0; i--)
 	{
 		image(imgFrog, 32 + 48 * i, 32);
 	}

 	// display
 	for(int i=0; i<wood.length; i++)
 	{
 		wood[i].display();
 	}
 	for(int i=0; i<car.length; i++)
 	{
 		car[i].display();
 	}
 	for(int i=0; i<turtle.length; i++)
 	{
 		turtle[i].display();
 	}
 	froggy.display();

 	// check win
 	if(froggy.checkIsWin())
 	{
 		STATE = GAME_WIN;
 	}

 	// check dead
 	if(froggy.isAlive && millis() - game_timer > 6000)
 	{
 		goDead();
 	}

 	boolean isDead = true;
 	if(froggy.isAlive)
 	{
 		for(int i=0; i<wood.length; i++)
	 	{
	 		if(!froggy.checkIsDead(wood[i].center_x, wood[i].center_y, wood[i].wood_w, wood[i].wood_h + 20, wood[i].speed))
	 		{
	 			isDead = false;
	 			break;
	 		}
	 	}
 	}
 	if(froggy.isAlive)
 	{
 		for(int i=0; i<turtle.length; i++)
	 	{
	 		if(!froggy.checkIsDead(turtle[i].center_x, turtle[i].center_y, turtle[i].turtle_w, turtle[i].turtle_h + 15, turtle[i].speed))
	 		{
	 			isDead = false;
	 			break;
	 		}
	 	}
 	}
 	if(froggy.isAlive && isDead)
 	{
 		goDead();
 	}
 	if(froggy.isAlive && !froggy.isInPond())
 	{
 		for(int i=0; i<car.length; i++)
	 	{
	 		if(froggy.checkIsDead(car[i].center_x, car[i].center_y, car[i].car_w, car[i].car_h, car[i].speed))
	 		{
	 			goDead();
				break;
	 		}
	 	}
 	}

 	// FROG_DIE timer
	if(STATE == FROG_DIE && die_timer + 1000 < millis())
	{
		froggy.initPosition();
		game_timer = millis();
		STATE = GAME_RUN;
	}
}

public void goDead()
{
	froggy.goDead();
	LIFE--;
	die_timer = millis();
	STATE = FROG_DIE;

	if(LIFE <= 0)
	{
		STATE = GAME_LOSE;
	}
}

public void runGAME_LOSE()
{
	background(0x00, 0x00, 0x00);	
	fill(0xff, 0xff, 0xff);
	textAlign(CENTER, CENTER);
	textSize(32);
	text("YOU LOSE", width/2, 250);
	PImage img = loadImage("data/lose.png");
	image(img, (width-262)/2, (height-149)/2);
}

public void runGAME_WIN()
{
	background(0x00, 0x00, 0x00);	
	fill(0xff, 0xff, 0xff);
	textAlign(CENTER, CENTER);
	textSize(32);
	text("YOU WIN!!", width/2, 250);
	PImage img = loadImage("data/win.png"); 
	image(img, (width-226)/2, (height-185)/2);
}

public void keyPressed() 
{
	if (keyCode == ENTER)
	{
		switch (STATE) 
		{
			case GAME_START : 
				game_timer = millis();
				STATE = GAME_RUN;   
				break;

			case GAME_LOSE  : 
			case GAME_WIN   : 
				LIFE = 3;
				froggy.initPosition();
				STATE = GAME_START; 
				break;
		}
	}

  	if (key == CODED) 
  	{
  		if(STATE == GAME_RUN && froggy.isAlive)
  		{
  			switch (keyCode) 
	  		{
	  			case LEFT  : froggy.move_left();  break;
				case UP    : froggy.move_up();    break;
				case RIGHT : froggy.move_right(); break;
				case DOWN  : froggy.move_down();  break;
	  		}
  		}
  	} 
}
class Car
{
	PImage img;
	public static final int MODE_LeftCar1  = 0; 
	public static final int MODE_RightCar1 = 1; 
	public static final int MODE_LeftCar2  = 2; 
	public static final int MODE_RightCar2 = 3; 
	public static final int MODE_LTruck    = 4; 
	public static final int MODE_RTruck    = 5; 
	int block;
	int speed;
	int car_w = 32;
	int car_h = 32;
	int center_x;
	int center_y;

	Car(int mode, int speed, int block, int x_offset)
	{
		initCar(mode, speed, block);
		center_x += x_offset;
	}

	Car(int mode, int speed, int block)
	{
		initCar(mode, speed, block);
	}

	public void initCar(int mode, int speed, int block)
	{
		switch (mode) 
		{
			case MODE_LeftCar1  : 
				img = loadImage("data/LCar1.png");  
				this.speed = speed;
				center_x = -car_w/2;
				break;

			case MODE_RightCar1 : 
				img = loadImage("data/RCar1.png"); 
				this.speed = -speed;
				center_x = width + car_w/2;
				break;

			case MODE_LeftCar2  :
				 img = loadImage("data/LCar2.png");  
				 this.speed = speed;
				 center_x = -car_w/2;
				 break;

			case MODE_RightCar2 :
				 img = loadImage("data/RCar2.png"); 
				 this.speed = -speed;
				 center_x = width + car_w/2;
				 break;

			case MODE_LTruck : 
			 	 img = loadImage("data/LTruck.png"); 
			 	 this.speed = speed;
			 	 car_w = 64;
			 	 center_x = -car_w/2;
			 	 break;

		 	case MODE_RTruck : 
		 	 	 img = loadImage("data/RTruck.png"); 
		 	 	 this.speed = -speed;
		 	 	 car_w = 64;
		 	 	 center_x = width + car_w/2;
			 	 break;
		}

		center_y = height - 60 - 45 * 7 + block * 45 + car_h/2;
	}

	public void display()
	{
		center_x += speed;

		if(speed > 0)
		{
			if(center_x - car_w/2 > width)
			{
				center_x = -car_w/2;
			}
		}
		else if(center_x + car_w/2 < 0)
		{
			center_x = width + car_w/2;
		}

		image(img, center_x - car_w/2, center_y - car_h/2, car_w, car_h);
	}
}
class Froggy
{
	PImage imgFrog; 	
	PImage deadFrog; 
	boolean isAlive = true;
	int speed = 20;
	int froggy_w = 32;
	int froggy_h = 32;
  	int center_x;
	int center_y;

	Froggy()
	{
	  	imgFrog  = loadImage("data/frog.png"); 
	  	deadFrog = loadImage("data/deadFrog.png");
	  	initPosition();
	}	

	public void initPosition()
	{
		isAlive = true;
		speed = 20;
	  	center_x = width / 2;
  		center_y = height - froggy_h / 2 - 66;
	}

	public void display()
	{
		if(isAlive)
		{
			image(imgFrog, center_x - froggy_w/2, center_y - froggy_h/2);
		}
		else
		{
      		image(deadFrog, center_x - 31/2, center_y - 32/2);	
		}
	}

	public void move_left()
	{
		center_x -= speed;
		adjustX();
	}

  	public void move_right()
	{
		center_x += speed;
		adjustX();
	}

	public void move_up()
	{
		center_y -= speed;
		adjustY();
	}

	public void move_down()
	{
		center_y += speed;
		adjustY();
	}

	public void adjustX()
	{
		if(center_x - froggy_w/2 < 0)
		{
			center_x = froggy_w/2;
		}
		else if(center_x + froggy_w/2 > width)
		{
			center_x = width - froggy_w/2;
		}
	}

	public void adjustY()
	{
		if(center_y - froggy_h/2 < 0)
		{
			center_y = froggy_h/2;
		}
		else if(center_y + froggy_h/2 > height - 60)
		{
			center_y = height - 60 - froggy_h/2;
		}
	}

	public boolean checkIsDead(int obj_center_x, int obj_center_y, int obj_w, int obj_h, int obj_speed)
	{
		if(!isAlive) 
		{
			return false;
		}

		if(isInPond()) 
		{
			speed = 40;
			boolean isCollisioned = isCollision(obj_center_x, obj_center_y, obj_w, obj_h);
			if(isCollisioned)
			{
				center_x += obj_speed;
				center_y = obj_center_y;
			}
			return !isCollisioned;
		}
		else
		{
			speed = 20;
			return isCollision(obj_center_x, obj_center_y, obj_w, obj_h);
		}
	}

	public boolean isInPond()
	{
		return (center_y - froggy_h/2) < (32 + 32 + 45 * 7);
	}

	public boolean isCollision(int obj_center_x, int obj_center_y, int obj_w, int obj_h)
	{
		int x1 = center_x - froggy_w/2;
		int y1 = center_y - froggy_h/2;
		int w1 = froggy_w;
		int h1 = froggy_h;
		int x2 = obj_center_x - obj_w/2;
		int y2 = obj_center_y - obj_h/2;
		int w2 = obj_w;
		int h2 = obj_h;

		if ((x1 < x2) && ((x2 - x1) >= w1)) return false;
		if ((x1 > x2) && ((x1 - x2) >= w2)) return false;
		if ((y1 < y2) && ((y2 - y1) >= h1)) return false;
		if ((y1 > y2) && ((y1 - y2) >= h2)) return false;

		return true;
	}

	public void goDead()
	{
		isAlive = false;
	}

	public boolean checkIsWin()
	{
		return center_y - froggy_h/2 <= 100;
	}

}
class Turtle
{
	PImage imgTurtle_Partial;
	PImage imgTurtle_Half;
	PImage imgTurtle_Full;

	int state = 0;
	int state_speed;
	int state_timer;
	final int State_Partial = 0;
	final int State_Half    = 1;
	final int State_Full    = 2;
	final int State_Half2   = 3;

	int block;
	int speed;
	int turtle_w = 30;
	int turtle_h = 30;
	int center_x;
	int center_y;

	Turtle(int speed, int block, int state_speed, int x_offset)
	{
		initTurtle(speed, block, state_speed);
		center_x += x_offset;
	}

	Turtle(int speed, int block, int state_speed)
	{
		initTurtle(speed, block, state_speed);
	}

	public void initTurtle(int speed, int block, int state_speed)
	{
		imgTurtle_Partial = loadImage("data/Turtle_Partial.png");
		imgTurtle_Half 	  = loadImage("data/Turtle_Half.png");
		imgTurtle_Full 	  = loadImage("data/Turtle_Full.png");

		this.state_speed = state_speed;
		state_timer = millis();

		this.speed = -speed;
		center_x = width + turtle_w/2;
		center_y = 60 + 55 + block * 45 + turtle_h/2;
	}

	public void display()
	{
		if(state_timer + state_speed < millis())
		{
			state_timer = millis();
			goNextState();
		}

		center_x += speed;

		if(center_x + turtle_w/2 < 0)
		{
			center_x = width + turtle_w/2;
		}

		PImage img;
		switch (state)
		{
			case State_Partial : img = imgTurtle_Partial; break;
			case State_Half    :
			case State_Half2   : img = imgTurtle_Half;	  break;
			case State_Full    : img = imgTurtle_Full;	  break;
			default 		   : img = imgTurtle_Partial; break;
		}
		image(img, center_x - turtle_w/2, center_y - turtle_h/2, turtle_w, turtle_h);
	}

	public void goNextState()
	{
		state++;
		if(state > State_Half2)
		{
			state = State_Partial;
		}
	}

}
class Wood
{
	PImage img;
	public static final int MODE_Log_84x20  = 0; 
	public static final int MODE_Log_116x20 = 1; 
	public static final int MODE_Log_180x20 = 2; 
	int block;
	int speed;
	int wood_w;
	int wood_h = 20;
	int center_x;
	int center_y;

	Wood(int mode, int speed, int block, int x_offset)
	{
		initWood(mode, speed, block);
		center_x += x_offset;
	}

	Wood(int mode, int speed, int block)
	{
		initWood(mode, speed, block);
	}

	public void initWood(int mode, int speed, int block)
	{
		switch (mode) 
		{
			case MODE_Log_84x20  : 
				img = loadImage("data/Log_84x20.png");  
				wood_w = 84;
				break;

			case MODE_Log_116x20 : 
				img = loadImage("data/Log_116x20.png"); 
				wood_w = 116;
				break;

			case MODE_Log_180x20  :
				 img = loadImage("data/Log_180x20.png");  
				 wood_w = 180;
				 break;
		}

		// this.speed = -speed;
		// center_x = width + wood_w/2;
		this.speed = speed;
		center_x = -wood_w/2;
		center_y = 60 + 55 + block * 45 + wood_h/2;
	}

	public void display()
	{
		center_x += speed;

		if(center_x + wood_w/2 < 0)
		{
			center_x = width + wood_w/2;
		}
		else if (center_x - wood_w/2 > width)
		{
			center_x = -wood_w/2;
		}

		image(img, center_x - wood_w/2, center_y - wood_h/2, wood_w, wood_h);
	}
}
/* @pjs pauseOnBlur="true";
        font="fonts/Square_One.ttf";
        preload= "data/deadFrog.png,
                  data/frog.png,
                  data/LCar1.png,
                  data/LCar2.png,
                  data/lose.png,
                  data/RCar1.png,
                  data/RCar2.png,
                  data/win.png,
                  data/Log_84x20.png,
                  data/Log_116x20.png,
                  data/Log_180x20.png,
                  data/LTruck.png,
                  data/RTruck.png,
                  data/Turtle_Full.png,
                  data/Turtle_Half.png,
                  data/Turtle_Partial.png"; */

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "assign2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
