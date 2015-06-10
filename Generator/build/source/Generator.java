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

public class Generator extends PApplet {



int canvasSize = 500;
int centerX = canvasSize/2;
int centerY = canvasSize/2;


int pattern;
int buffer = 20;
int radius = canvasSize/2-buffer;

int saveCounter = 0;
boolean saveMode = false;


public void setup() {
    size(canvasSize,canvasSize);
    //noLoop();
    frameRate(1);
    pattern = 2;
    smooth();
}

public void draw() {

  background(255);

  switch(pattern) {
    
    case 1:
      patternWave();
      break;
    case 2:
      patternRadialLines();
      break;
    case 3:
      patternRadialSweep();
      break;  
  }

  savePattern();
  
}



/////// PATTERN 1 - CONCENTRIC WAVES ///////
public void patternWave() {

    noStroke();
    ellipseMode(RADIUS);
    
    float h = random(0, 255);
    //random(0, 255);
    
    //int amplitude = 30;
    //coloe c = color(abs(py-i)*255/amplitude);
    float inc = TWO_PI/180.0f;
    float a = 0.0f;
    int c;
    
    float frequency = random(10.0f, 100.0f);
    
    // scan through circle outside-in
    for (int r = radius; r > 0; --r) {
      
      frequency = frequency / 1.007f;
      inc = TWO_PI/frequency;
      c = color((sin(a)*125)+125);
      println((sin(a)*125)+125);
      //inc = inc +5;
      a = a + inc;
      
      fill(c);
      ellipse(centerX, centerY, r, r);
      //h = (h + 1) % 255;
    }  
  
}


/////// PATTERN 2 - LINES FROM CENTER ///////
public void patternRadialLines() {
    pushMatrix();
    radialLines(PApplet.parseInt(random(0,radius)), radius, 6, PApplet.parseInt(random(8,12))); 
    popMatrix();
    pushMatrix();
    radialLines(0, PApplet.parseInt(random(0,radius)), 12, PApplet.parseInt(random(12,16)));    
    popMatrix();
    pushMatrix();
    radialLines(PApplet.parseInt(random(radius/2,radius)), PApplet.parseInt(random(0,radius)), 24, PApplet.parseInt(random(6,12))); //line thickness should not be smaller than 6
    popMatrix();
    filter(BLUR, 2);

    // pushMatrix();
    // radialLines(int(random(0,radius)), radius, 6, int(random(8,12))); 
    // filter(BLUR, 3);
    // popMatrix();
    // pushMatrix();
    // radialLines(0, int(random(0,radius)), 12, int(random(12,16)));    
    // filter(BLUR, 2);
    // popMatrix();
    // pushMatrix();
    // radialLines(0, int(random(0,radius)), 24, int(random(6,12))); //line thickness should not be smaller than 6
    // filter(BLUR, 1);
    // popMatrix();

}


public void radialLines(int _beginLine, int _endLine, int _numberOfLines, int _lineThickness) {

    int beginLine = _beginLine;
    int numberOfLines = _numberOfLines;
    int lineThickness = _lineThickness;
    int endLine = _endLine;   
   // int strokeColor = _strokeColor; 
    //float shiftAngle = _shiftAngle;
    stroke(0);
    strokeWeight(lineThickness);
    strokeCap(ROUND);
    strokeJoin(ROUND);

    pushMatrix();
    translate(centerX,centerY);
    rotate(radians(360/numberOfLines)); // shift rotation    
    for(int i=0; i < numberOfLines; i++) {
      rotate(radians(360/numberOfLines));
      line(0,beginLine,0,endLine); 
     }
    popMatrix();

}



/////// PATTERN 3 - LINES FROM CENTER - SWEEP ///////
public void patternRadialSweep() {
 
    int[] angleRatios = {1,2,3,4};

    // stubby patches, starts and ends in the middle, few of them
    radialSweep(PApplet.parseInt(random(50,100)), PApplet.parseInt(random(100,150)), 450, 900); 

    // long, thin patches, few of them
    //radialSweep(int(random(10,50)), radius, 360/angleRatios[int(random(0,angleRatios.length))]); 
    radialSweep(PApplet.parseInt(random(10,150)), radius, 225, 450); 
    filter(BLUR, 2);

}

public void radialSweep(int _beginLine, int _endLine, int _gapCounterLimit, int _gapCounterSpace) {


    int beginLine = _beginLine;
    int endLine = _endLine;  
    int gapCounterLimit = _gapCounterLimit;   
    int gapCounterSpace = _gapCounterSpace;
    int gapCounter = 0;
    //float shiftAngle = _shiftAngle;
    stroke(0);
    strokeWeight(2);
    strokeCap(SQUARE);
    strokeJoin(ROUND);

    pushMatrix();
    translate(centerX,centerY);
    //rotate(radians(1); // shift rotation    

    float sweepAngle = 0.1f;
    for(int i=0; i < 3600; i++) {

      rotate(radians(sweepAngle));

      if (gapCounter < gapCounterLimit) {
        line(0,-beginLine,0,-endLine); 

      } else if (gapCounter > gapCounterSpace) {
        gapCounter = 0;
      }
      gapCounter++;
     }


    popMatrix();

}






////////////     SAVE ROUTINE      ////////////
public void savePattern() {
  
  if (saveMode==true) {
    saveCounter++;
    save("images"+pattern+"/"+saveCounter+".png");
  }
}




////////////     CONTROL KEYS      ////////////
public void keyPressed() {
  if (key == 'S' || key == 's') { 
    saveCounter++;
    save("images"+pattern+"/"+random(0,1000)+saveCounter+".png");
  }

  if (key == 'D' || key == 'd') { 
    saveMode = !saveMode;
  }

  
  //if (key == 'G' || key == 'g') { 
  //  patternWave();
  //}

  if (key == '1') { 
    pattern = 1;
  }

  if (key == '2') { 
    pattern = 2;
  }

 if (key == '3') { 
    pattern = 3;
  }

}




  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "Generator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
