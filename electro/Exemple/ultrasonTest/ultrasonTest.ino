/* sketch Arduino utilisation d'un module hc-sr04
Autheur : Guillaume FLOCH
site web : www.osdclic.fr
tuto : www.memorandum.ovh
membre de : www.goldorhack.org
*/

#define trigPin 5      //Trig 
#define echoPin 4      //Echo 
#define avertisseur 13 //Led
int mindist = 12; //On indique la distance au dessus de laquelle nous souhaitons voir la led s'allumer

void setup() {
  Serial.begin (9600);  
  pinMode(trigPin, OUTPUT);  //On défini Trig comme une sortie
  pinMode(echoPin, INPUT);   //On défini Echo comme une entrée
  pinMode(avertisseur, OUTPUT); //On défini la led comme une sortie
}

void loop() {
  long duree, distance;
  digitalWrite(trigPin, LOW);  
  delayMicroseconds(2); 
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10); //Trig envois pendant 10ms 
  digitalWrite(trigPin, LOW);
  
  // On calcul le temps pour l'aller retour du signal
  duree = pulseIn(echoPin, HIGH);
  distance = duree*340/(2*10000);  
   
  if (distance > mindist) {  // On allume la led si on est moins loin que "mindist", mindist étant défini en début de programme
     digitalWrite(avertisseur,HIGH);
}
  else {   //sinon on éteind la led
    digitalWrite(avertisseur,LOW);
  }
  //Pour le moniteur série
  if ( distance <= 0){  
    Serial.println("Hors de portee");
  }
  else {
    Serial.print(distance);
    Serial.print(" cm ");
    Serial.print(duree);
    Serial.println(" ms");
  }
  delay(100);
}
