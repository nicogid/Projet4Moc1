#include <Servo86.h>

Servo servo;
bool distribution = true;
const int buttonPin = 2; // broche du capteur PIR
int buttonState = 0; // etat de la sortie du capteur
int pinServo = 9;
int periode=20000;// période entre chaque début d'impulsion en microsecondes
int pos = 100;

void setup()
{
  servo.attach(pinServo);
  pinMode(buttonPin, INPUT); //la broche du capteur est mise en entree
}

void loop()
{
  buttonState = digitalRead(buttonPin);//lecture du capteur
  if (buttonState == HIGH) //si quelquechose est detecte
  {
    if(distribution == true){
      for(pos = 100; pos>=11; pos-=1)     // goes from 180 degrees to 0 degrees 
      {                                
        servo.write(pos);              // tell servo to go to position in variable 'pos' 
        delay(50);                       // waits 15ms for the servo to reach the position 
      } 
        delay(1000);
       for(pos = 10; pos < 100; pos += 1)  // goes from 0 degrees to 180 degrees 
      {                                  // in steps of 1 degree 
        servo.write(pos);              // tell servo to go to position in variable 'pos' 
        delay(15);                    // waits 15ms for the servo to reach the position 
      }
       distribution = false;                  
    }  
    if(distribution == false){
      delay(3600);
      distribution = true;
    }
      
  }
}

