package main;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

/**
 * This class represents the main window of the Oregon Trail game.
 * It allows the user to interact with different screens such as setup, travel conditions, and travel.
 * The setup screen enables the user to load the wagon with resources.
 * The travel conditions screen allows the user to adjust the travel speed and amount of food eaten per day.
 * The travel screen simulates the wagon journey and tracks days, distance traveled, and remaining food.
 * Written by Gabriel Parry, Julian Calvelage, Enzo Bordogna on 4/9/2024.
 */
public class mainWindow {

	//Set up the different windows that the user can interact with.
	//Inilalized here so the whole program can interact with them
	private JFrame frame;
	private JFrame frameTravelConditions;
	private JFrame frameTravel;
	private JFrame frameWagon;
	
	//Preloaded wagon configuration
	private Wagon wag = new Wagon(); // wagon object
	public int lbsOfFood = 500;
	public int lbsOfWater = 500;
	
	//Travel conditions the user can set
	public int foodEaten = 4;
	public int travelSpeed = 12;
	
	//These variables track movment and time of the wagon moving
	private int numDays;
	private int totalMoveCount = 0;
	private int wagonX = 0;
	private int treeOneX = 0;
	private int fortOneX = 0;
	private boolean reached = false;
	
	//These setup a label to be created after the window has been made
	// The fort label
	JLabel fort = new JLabel("");
	boolean thereFort = false;
	//Makes an ImageIcon that can be called multiple times
	ImageIcon imageIcon = new ImageIcon();
	
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Setup Screen");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(191, 6, 121, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnLoadWagon = new JButton("Load Wagon");
		btnLoadWagon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wagonScreen(); //When the button is click draw this window
			}
		});
		btnLoadWagon.setBounds(55, 66, 129, 44);
		frame.getContentPane().add(btnLoadWagon);
		
		JButton btnTravel = new JButton("Travel");
		btnTravel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				travelScreen(); //When the button is click draw this window
			}
		});
		btnTravel.setBounds(55, 220, 129, 44);
		frame.getContentPane().add(btnTravel);
		
		JButton btnTravelConditions = new JButton("Travel Conditions");
		btnTravelConditions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				travelConditionsScreen(); //When the button is click draw this window
			}
		});
		btnTravelConditions.setBounds(55, 143, 129, 44);
		frame.getContentPane().add(btnTravelConditions);
		
		
		
		
		
		
		
	}
	
	/**
	 * This method handles the GUI and funcinality of the travel conditions
	 * It allows the player to increase/decrease movement speed and food eaten
	 */
	public void travelConditionsScreen() {
		//Generates the new frame
		frameTravelConditions = new JFrame();
		frameTravelConditions.setBounds(300, 200, 500, 350);
		frameTravelConditions.getContentPane().setLayout(null);
		frameTravelConditions.setBackground(Color.RED);
		frameTravelConditions.setVisible(true);
		 
		//UI element for speed
		JLabel speedNum = new JLabel("12");
		speedNum.setBounds(105, 142, 100, 16);
		frameTravelConditions.getContentPane().add(speedNum);
		
		//UI element to tell what screen the player is on
		JLabel lblNewLabel = new JLabel("Travel Conditions Screen");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(171, 6, 300, 16);
		frameTravelConditions.getContentPane().add(lblNewLabel);
		
		//UI element to increase the speed
		JButton btnIncreaseSpeed = new JButton("Increase Speed");
		btnIncreaseSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = Integer.parseInt(speedNum.getText());
				if(temp < 24) {
					temp++;
					speedNum.setText("" + temp);
				}
				travelSpeed = Integer.parseInt(speedNum.getText());
			}
		});
		btnIncreaseSpeed.setBounds(55, 92, 129, 44);
		frameTravelConditions.getContentPane().add(btnIncreaseSpeed);
		
		//UI elemnt
		JLabel speed = new JLabel("Speed: ");
		speed.setBounds(55, 142, 100, 16);
		frameTravelConditions.getContentPane().add(speed);
		
		
		//UI element to decrease the speed
		JButton btnDecreaseSpeed = new JButton("Decrease Speed");
		btnDecreaseSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = Integer.parseInt(speedNum.getText());
				if(temp > 12) {
					temp--;
					speedNum.setText("" + temp);
				}
				travelSpeed = Integer.parseInt(speedNum.getText());
			}
		});
		btnDecreaseSpeed.setBounds(55, 166, 129, 44);
		frameTravelConditions.getContentPane().add(btnDecreaseSpeed);
		
		//UI element for how much food is eaten per day
		JLabel foodNum = new JLabel("4");
		foodNum.setBounds(355, 142, 100, 16);
		frameTravelConditions.getContentPane().add(foodNum);
		
		//UI element to increase the food eaten
		JButton btnIncreaseFood = new JButton("Increase Food Eaten");
		btnIncreaseFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = Integer.parseInt(foodNum.getText());
				if(temp < 6) {
					temp++;
					foodNum.setText("" + temp);
				}
				foodEaten = Integer.parseInt(foodNum.getText());
			}
		});
		btnIncreaseFood.setBounds(255, 92, 179, 44);
		frameTravelConditions.getContentPane().add(btnIncreaseFood);
		
		//UI element
		JLabel food = new JLabel("Amount Eaten: ");
		food.setBounds(255, 142, 100, 16);
		frameTravelConditions.getContentPane().add(food);
		
		
		//UI element to decrease the food eaten
		JButton btnDecreaseFood = new JButton("Decrease Food Eaten");
		btnDecreaseFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int temp = Integer.parseInt(foodNum.getText());
				if(temp > 3) {
					temp--;
					foodNum.setText("" + temp);
				}
				foodEaten = Integer.parseInt(foodNum.getText());
			}
		});
		btnDecreaseFood.setBounds(255, 166, 179, 44);
		frameTravelConditions.getContentPane().add(btnDecreaseFood);
		
	}
	/**
	 * This is the method that handels the "game" part
	 * The wagon is rendered to the screen and by clicing the move button the player can advance both position and time
	 * Once the wagon moves to about half way to the screen objects will move realitive to the player
	 * The days, ammount of food, and distance are tracked in the top right
	 */
	public void travelScreen() {
		
		//Generates the new frame
		frameTravel = new JFrame();
		frameTravel.setBounds(300, 200, 550, 350);
		frameTravel.getContentPane().setLayout(null);
		frameTravel.setBackground(Color.YELLOW);
		frameTravel.setVisible(true);
		
		//UI element to show what window the player is on
		JLabel lblNewLabel = new JLabel("Travel Screen");
		lblNewLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		lblNewLabel.setBounds(191, 6, 161, 28);
		frameTravel.getContentPane().add(lblNewLabel);
		
		//Creates the wagon
		JLabel wagon = new JLabel("");
		wagon.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		wagon.setBounds(400, 150, 64, 64);
		frameTravel.getContentPane().add(wagon);
		
		//Created the ox
		JLabel ox = new JLabel("");
		ox.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		ox.setBounds(350, 150, 64, 64);
		frameTravel.getContentPane().add(ox);
		
		//Creates the trail
		JLabel trail = new JLabel("--_----_---______---_---_----__-_------_____-_--_-----_____---___----______-------__---_______");
		trail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		trail.setBounds(0, 150, 500, 64);
		frameTravel.getContentPane().add(trail);
		
		//Creates a stylish tree
		//This represents Chehalem Valley
		JLabel tree = new JLabel("");
		tree.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		tree.setBounds(0, 100, 96, 96);
		frameTravel.getContentPane().add(tree);
		
		
		
		//UI elemnent to count the number of days
		JLabel days = new JLabel("Days: ");
		days.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		days.setBounds(400, 50, 100, 18);
		frameTravel.getContentPane().add(days);
		
		//UI elemnent to count the distance traveled
		JLabel distance = new JLabel("Distance: ");
		distance.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		distance.setBounds(400, 70, 150, 18);
		frameTravel.getContentPane().add(distance);
		
		//UI elemnent to count the ammount of food
		JLabel food = new JLabel("Lbs of Food: ");
		food.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		food.setBounds(400, 90, 150, 18);
		frameTravel.getContentPane().add(food);
		
		// Reads in the images and displays them on the JLabels
        imageIcon = new ImageIcon(setImage("/Users/gabeparry/eclipse-workspace/MVP/src/main/images/Wagon.png", 64, 64)); 
        wagon.setIcon(imageIcon);
        
        imageIcon = new ImageIcon(setImage("/Users/gabeparry/eclipse-workspace/MVP/src/main/images/tree01.png", 96, 96)); 
        tree.setIcon(imageIcon);
        
        imageIcon = new ImageIcon(setImage("/Users/gabeparry/eclipse-workspace/MVP/src/main/images/ox.png", 64, 64)); 
        ox.setIcon(imageIcon);
        
        fort.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		fort.setBounds(-50, 60, 128, 128);
		frameTravel.getContentPane().add(fort);
        
        
        /**
         * This button handles all the game logic
         * It moves the wagon until it gets about half way across the window then it moves the objects realtive to the wagon
         * This is where the days, distance, and food are updated
         */
		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				totalMoveCount++; //number of times the button is clicked
				boolean wagonStopped = false; //the state of the wagon moving across the screen
				
				//moves the wagon
				if(wagon.getX() > 230) {
					wagonX++;
					wagon.setBounds(400 + (wagonX * -travelSpeed), 150, 64, 64);
					ox.setBounds(350 + (wagonX * -travelSpeed), 150, 64, 64);
					
				} else {
					wagonStopped = true;
				}
				//moves the tree
				if(wagonStopped == true) {
					treeOneX++;
					tree.setBounds(0 + (treeOneX * travelSpeed), 100 , 96, 96);
					if(tree.getX() > 230) {
						if(reached != true) {
						JOptionPane.showMessageDialog(null, "You Have Reached Chehalem Valley. Congrats", null, JOptionPane.INFORMATION_MESSAGE);
						reached = true;
						}
					}
				}
				//tracks the days, distance and food
				// three clicks is one day
				numDays = totalMoveCount / 3;
				days.setText("Days: " + numDays);
				distance.setText("Distance: " + (totalMoveCount * travelSpeed));
				food.setText("Lbs of Food: " + (lbsOfFood - (numDays * foodEaten)));
				
				//creates the fort after 20 days of traveling and moves it
				if(numDays > 20) {
					if(thereFort == false) {
						
						
				        fort.setIcon(imageIcon);
						
						thereFort = true;
					}
					
					fort.setBounds(0 + (fortOneX * travelSpeed), 50 , 128, 128);
					fortOneX++;
				}
				//Once the player reaches the fort it alerts them
				if(fort.getX() > 230) {
					JOptionPane.showMessageDialog(null, "You Have Reached The Fort, Bridger.", null, JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
		btnMove.setBounds(400, 10, 115, 44);
		frameTravel.getContentPane().add(btnMove);
        
      //Creates the sky
		JLabel sky = new JLabel("");
		sky.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 20));
		sky.setBounds(0, 0, 600, 180);
		sky.setBackground(Color.BLUE);
		frameTravel.getContentPane().add(sky);
        
        imageIcon = new ImageIcon(setImage("/Users/gabeparry/eclipse-workspace/MVP/src/main/images/sky.png", 600, 500)); 
        sky.setIcon(imageIcon);
      
        imageIcon = new ImageIcon(setImage("/Users/gabeparry/eclipse-workspace/MVP/src/main/images/fort.png", 128, 128)); 
        
        
		
		
	}
	//This method creates the wagon settings. It is not interactiable 
	//Creates all the objects
	public void wagonScreen() {
		frameWagon = new JFrame();
		frameWagon.setBounds(300, 200, 550, 350);
		frameWagon.getContentPane().setLayout(null);
		frameWagon.setBackground(Color.BLUE);
		frameWagon.setVisible(true);
		
		
		
		JLabel food = new JLabel("Lbs of Food: 500");
		food.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		food.setBounds(15, 50, 150, 18);
		frameWagon.getContentPane().add(food);
		
		JLabel ox = new JLabel("Number of Ox: 1");
		ox.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		ox.setBounds(165, 50, 150, 18);
		frameWagon.getContentPane().add(ox);
		
		JLabel water = new JLabel("Lbs of Water: 500");
		water.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		water.setBounds(15, 70, 150, 18);
		frameWagon.getContentPane().add(water);
		
		wag.setWagonWeight(lbsOfFood + lbsOfWater); //sets the wagon weight
		wag.setOxNumber(1); //sets the number of ox
		
		JLabel item = new JLabel("Item: Teddy Bear");
		item.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		item.setBounds(15, 90, 150, 18);
		frameWagon.getContentPane().add(item);
		
		JLabel item2 = new JLabel("Item: Gun");
		item2.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 13));
		item2.setBounds(15, 110, 150, 18);
		frameWagon.getContentPane().add(item2);
	
		
		
	}
	/**
	 * Imports an image from the specified path and scales it to the given dimensions.
	 *
	 * @param imgPath The path to the image file.
	 * @param w       The desired width of the scaled image.
	 * @param h       The desired height of the scaled image.
	 * @return The scaled image as an instance of java.awt.Image.
	 */
	public Image setImage(String imgPath, int w, int h) {
		BufferedImage img = null;
        try {
        	System.out.println(new File(imgPath).exists());
            img = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Sets the imgage size to the size of the JLabel
        Image dimg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		return dimg;
	}
}

