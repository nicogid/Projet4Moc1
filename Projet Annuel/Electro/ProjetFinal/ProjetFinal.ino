/* MyDistributor
 by Nicolas Gidon
 This example code is in the public domain.

*/
#include <Ethernet.h>
#include <SPI.h>
#include <Servo.h>
#include <Twitter.h>

Twitter twitter("882710885034328064-PSWUbuIwERLaUtbhlKJtgvBUBC509pI");

#define trigPin 5      //Trig 
#define echoPin 4      //Echo 
#define avertisseur 13 //Led
int mindist = 10; //On indique la distance au dessus de laquelle nous souhaitons voir la led s'allumer
//int mindist = 20; //On indique la distance au dessus de laquelle nous souhaitons voir la led s'allumer

Servo myservo;  // create servo object to control a servo

void servo_utilisation();
void request_post();
void state_pir(boolean pirState);
void ultrason_reservoir();
//void request_post_ultra();

int inputPin = 2;               // choose the input pin (for PIR sensor)
int pirState = LOW;             // we start, assuming no motion detected
int val = 0;                    // variable for reading the pin status

boolean request = true; // pour eviter d'envoyer 15 requete post 
boolean state = true;

int pos = 100;    // variable to store the servo position

// Enter a MAC address for your controller below.
// Newer Ethernet shields have a MAC address printed on a sticker on the shield
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
IPAddress server(192,168,1,23);  // numeric IP for Google (no DNS)
String host = "Host: 192.168.1.23";

// Set the static IP address to use if the DHCP fails to assign
IPAddress ip(192, 168, 0, 177);

String data = "{\"name\":\"PIR\"}";
String data_ultra = "{\"name\":\"Ultrason\"}";
int nb_msg = 1;

char buf[100];

// Initialize the Ethernet client library
// with the IP address and port of the server
// that you want to connect to (port 80 is default for HTTP):
EthernetClient client;


void setup() {
  pinMode(inputPin, INPUT);     // declare sensor as input
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // start the Ethernet connection:
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // try to congifure using IP address instead of DHCP:
    Ethernet.begin(mac, ip);
  }
  // give the Ethernet shield a second to initialize:
  delay(1000);
  Serial.println("connecting...");

  pinMode(trigPin, OUTPUT);  //On défini Trig comme une sortie
  pinMode(echoPin, INPUT);   //On défini Echo comme une entrée
  pinMode(avertisseur, OUTPUT); //On défini la led comme une sortie
}

void loop() {


  ultrason_reservoir();
  
  val = digitalRead(inputPin);  // read input value
  if (val == HIGH && state == true) {// check if the input is HIGH
    servo_utilisation();
    state_pir(pirState);

    //if(request == true){
      // if you get a connection, report back via serial:
      request_post(data);

      
    //}
    //state == false;
  } else {
    pos = 100;
    //request = true;
    //state = false;
  }

  delay(5000);
}


/* ************************** Fonction **************************** */
void servo_utilisation(){
  for (pos = 180; pos >= 0; pos -= 1) { // goes from 180 degrees to 0 degrees
      myservo.write(pos);              // tell servo to go to position in variable 'pos'
      delay(15);                       // waits 15ms for the servo to reach the position
    }
    delay(1000);
    for (pos = 0; pos <= 180; pos += 1) { // goes from 0 degrees to 180 degrees
      // in steps of 1 degree
      myservo.write(pos);              // tell servo to go to position in variable 'pos'
      delay(15);                       // waits 15ms for the servo to reach the position
    }
}

void request_post(String data){
  // close any connection before send a new request.
  // This will free the socket on the WiFi shield
  client.stop();
  
  if (client.connect(server, 8000)) {
      Serial.println("connected");
      // Make a HTTP request:

      
      client.println("POST /api/addsensor HTTP/1.1");                    
      //client.println(host);
      client.println("Host: 192.168.1.23");
      client.println("Content-Type: application/x-www-form-urlencoded");
      client.println("Connection: close");
      client.println("User-Agent: Arduino/1.0");
      client.print("Content-Length: ");
      client.println(data.length());
      client.println();
      client.print(data);
      client.println(); 

      
    } else {
      // if you didn't get a connection to the server:
      Serial.println("connection failed");
    }

    if(data.equals("{\"name\":\"PIR\"}"))
           sprintf(buf, "Message  TestTest %d : Je mange des croquette", nb_msg);
      else
           sprintf(buf, "Message TestTest %d : Je veux des croquette", nb_msg);
      tweet(buf);
      nb_msg++;

}

void state_pir(boolean pirState){
   if (pirState == LOW) {
      // we have just turned on
      Serial.println("Motion detected!");
      // We only want to print on the output change, not state
      pirState = HIGH;
    }else{
       // we have just turned on
      Serial.println("Motion ended");
      // We only want to print on the output change, not state
      pirState = LOW;
    }
}


void ultrason_reservoir(){
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
     if(state)
        request_post(data_ultra);
     state = false;
  }
  else {   //sinon on éteind la led
    digitalWrite(avertisseur,LOW);
    state = true;
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

// Write Twitter request with msg send in param
void tweet(char msg[]) {
  Serial.println("connecting twitter...");
  
  //client.stop();
  if (twitter.post(msg)) {
    int status = twitter.wait(&Serial);
    if (status == 200) {
      Serial.println("OK.");
    } else {
      Serial.print("failed : code ");
      Serial.println(status);
    }
  } else {
    Serial.println("connection failed Twitter");
  }
}


