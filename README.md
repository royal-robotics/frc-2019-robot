# Setting up your Development Environment
Follow the instructions below:  
[Installing C++ and Java Development Tools for FRC](https://wpilib.screenstepslive.com/s/currentCS/m/java/l/1027503-installing-c-and-java-development-tools-for-frc)  

# How to Build and Deploy from FRC VS Code 2019
Clone this repository and open the folder in FRC VS Code 2019  
Click on the 'W' in the red circle to open the WPILib commands  
To Build - Click the WPILib: Build Robot Code command  
To Deploy - Click the WPILib: Deploy Robot Code command  

# How to Build and Deploy from Command Line
Clone this repository, open command prompt, and `cd` to the folder  
To Build - Run the command `gradlew build`  
To Deploy - Run the command `gradlew deploy`  
  
Note:  
You must be connected to the robot for the deploy command to succeed  
If the command line gives you an error about the JAVA_HOME variable not being set, follow the instructions below:  
[How to set the JAVA_HOME variable](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux?fbclid=IwAR2y4fWi_p3ZGQfnldtQ7uXSmYH-8auKvJ_HJWvYzVwRGQiay9pbJLUq060)  
The path to Java should be at \<Users Directory\>/Public/frc2019/jdk  
