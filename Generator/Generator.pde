

int canvasSize = 500;
int centerX = canvasSize/2;
int centerY = canvasSize/2;


int pattern;
int radius = canvasSize/2;

int saveCounter = 0;
boolean saveMode = false;



void setup() {
    size(canvasSize,canvasSize);
  
    //noLoop();
    frameRate(1);
    pattern = 2;
    smooth();

}



void draw() {

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
void patternWave() {

    noStroke();
    ellipseMode(RADIUS);
    
   
    float h = random(0, 255);
    
    //int amplitude = 30;
    //coloe c = color(abs(py-i)*255/amplitude);
    float inc = TWO_PI/90.0;
    float a = 0.0;
    color c;
    
    float frequency = 100.0;
    
    // scan through circle outside-in
    for (int r = radius; r > 0; --r) {
      
      frequency = frequency / 1.009;
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
void patternRadialLines() {
 

    // long and thin, starts anywhere and ends at radius
    radialLines(int(random(20,radius)), radius, 12, int(random(5,10))); 

    // long and thin, starts at zero and ends anywhere
    radialLines(0, int(random(20,radius)), 12, int(random(5,10)));    


}


void radialLines(int _beginLine, int _endLine, int _numberOfLines, int _lineThickness) {


    int beginLine = _beginLine;
    int numberOfLines = _numberOfLines;
    int lineThickness = _lineThickness;
    int endLine = _endLine;    
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
      line(0,-beginLine,0,-endLine); 
     }
    popMatrix();

}



/////// PATTERN 3 - LINES FROM CENTER - SWEEP ///////
void patternRadialSweep() {
 
    int[] angleRatios = {1,2,3,4};

    // stubby patches, starts and ends in the middle, few of them
    radialSweep(int(random(50,100)), int(random(150,200)), 450, 900); 

    // long, thin patches, few of them
    //radialSweep(int(random(10,50)), radius, 360/angleRatios[int(random(0,angleRatios.length))]); 
    radialSweep(int(random(10,50)), radius, 225, 450); 

}

void radialSweep(int _beginLine, int _endLine, int _gapCounterLimit, int _gapCounterSpace) {


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

    float sweepAngle = 0.1;
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
void savePattern() {
  
  if (saveMode==true) {
    saveCounter++;
    save("images/radial-"+pattern+"-"+saveCounter+".png");
  }
}




////////////     CONTROL KEYS      ////////////
void keyPressed() {
  if (key == 'S' || key == 's') { 
    saveCounter++;
    save("images/radial-"+pattern+"-"+saveCounter+".png");
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




