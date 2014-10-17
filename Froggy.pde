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

	void initPosition()
	{
		isAlive = true;
		speed = 20;
	  	center_x = width / 2;
  		center_y = height - froggy_h / 2 - 66;
	}

	void display()
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

	void move_left()
	{
		center_x -= speed;
		adjustX();
	}

  	void move_right()
	{
		center_x += speed;
		adjustX();
	}

	void move_up()
	{
		center_y -= speed;
		adjustY();
	}

	void move_down()
	{
		center_y += speed;
		adjustY();
	}

	void adjustX()
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

	void adjustY()
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

	boolean checkIsDead(int obj_center_x, int obj_center_y, int obj_w, int obj_h, int obj_speed)
	{
		if(!isAlive) 
		{
			return false;
		}

		// if(isInPond()) 
		// {
		// 	speed = 40;
		// 	boolean isCollision = isCollision(obj_center_x, obj_center_y, obj_w, obj_h);
		// 	if(isCollision)
		// 	{
		// 		center_x += obj_speed;
		// 		center_y = obj_center_y;
		// 	}
		// 	return !isCollision;
		// }
		// else
		{
			speed = 20;
			return isCollision(obj_center_x, obj_center_y, obj_w, obj_h);
		}
	}

	boolean isInPond()
	{
		return center_y - froggy_h/2 < 32 + 32 + 45 * 7;
	}

	boolean isCollision(int obj_center_x, int obj_center_y, int obj_w, int obj_h)
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

	void goDead()
	{
		isAlive = false;
	}

	boolean checkIsWin()
	{
		return center_y - froggy_h/2 <= 100;
	}

}