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

	void initCar(int mode, int speed, int block)
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

	void display()
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