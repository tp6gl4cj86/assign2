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

int die_timer;

void setup() 
{
	size(480, 800);
	textFont(createFont("fonts/Square_One.ttf", 20));
	initImage();
	initObject();
}

void initImage()
{
	imgFrog = loadImage("data/frog.png");
}

void initObject()
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

void draw()
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

void runGAME_START()
{
	background(0x33, 0x6f, 0x04);	
	fill(0xff, 0xff, 0xff);
	textAlign(CENTER, CENTER);
	textSize(32);
	text("PRESS ENTER", width/2, height/2);
}

void runGAME_RUN()
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
 	if(froggy.isAlive && isDead)
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
 	// if(froggy.isAlive)
 	// {
 	// 	for(int i=0; i<car.length; i++)
	 // 	{
	 // 		if(froggy.checkIsDead(car[i].center_x, car[i].center_y, car[i].car_w, car[i].car_h, car[i].speed))
	 // 		{
	 // 			goDead();
		// 		break;
	 // 		}
	 // 	}
 	// }

 	// FROG_DIE timer
	if(STATE == FROG_DIE && die_timer + 1000 < millis())
	{
		froggy.initPosition();
		STATE = GAME_RUN;
	}
}

void goDead()
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

void runGAME_LOSE()
{
	background(0x00, 0x00, 0x00);	
	fill(0xff, 0xff, 0xff);
	textAlign(CENTER, CENTER);
	textSize(32);
	text("YOU LOSE", width/2, 100);
	PImage img = loadImage("data/lose.png");
	image(img, (width-262)/2, (height-149)/2);
}

void runGAME_WIN()
{
	background(0x00, 0x00, 0x00);	
	fill(0xff, 0xff, 0xff);
	textAlign(CENTER, CENTER);
	textSize(32);
	text("YOU WIN!!", width/2, 100);
	PImage img = loadImage("data/win.png"); 
	image(img, (width-226)/2, (height-185)/2);
}

void keyPressed() 
{
	if (keyCode == ENTER)
	{
		switch (STATE) 
		{
			case GAME_START : STATE = GAME_RUN;   break;
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
