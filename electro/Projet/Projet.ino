#include <Servo.h>

Servo servo;
const int buttonPin = 2; // broche du capteur PIR
int buttonState = 0; // etat de la sortie du capteur

void setup()
{
  servo.attach(9);
  pinMode(buttonPin, INPUT); //la broche du capteur est mise en entree
}

void loop()
{
  buttonState = digitalRead(buttonPin);//lecture du capteur
  if (buttonState == HIGH) //si quelquechose est detecte
  {
    for (int position = 0; position <= 180; position++) {
    servo.write(position);
    delay(15);
    }
  delay(1000);

  // Fait bouger le bras de 180° à 10°
  for (int position = 180; position >= 0; position--) {
    servo.write(position);
    delay(15);
    }
  delay(1000);
  }
}
