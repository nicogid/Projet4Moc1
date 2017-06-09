#include <Servo.h>

Servo servo;
const int buttonPin = 2; // broche du capteur PIR
int buttonState = 0; // etat de la sortie du capteur
int pinServo = 9;
int periode=20000;// période entre chaque début d'impulsion en microsecondes

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
    for (int angle=0;angle<=180;angle+=20){ //on fait varier l'angle de 0 à 180° par tranche de 20°
      setAngle(angle);// on appelle la fonction setAngle définie plus bas
    }   
  }
}

//fonction setAngle pour envoyer les impulsions
void setAngle(int a){
  int duree=map(a,0,179,1000,2000);// on transforme l'angle en microsecondes et on stocke dans la variable duree
  digitalWrite(pinServo,LOW);//on met le pin à l'état bas
  
  // la boucle qui suit est nécessaire 
  // pour laisser le temps au servo d'aller à sa position
  for (int t=0;t<300;t++){ 
    digitalWrite(pinServo,HIGH);// on envoie l'impulsion
    delayMicroseconds(duree); // pendant la bonne durée
    digitalWrite(pinServo,LOW); // on stoppe l'impulsion
    delayMicroseconds(periode-duree); // on attend le temps restant pour atteindre la période
  }
}
