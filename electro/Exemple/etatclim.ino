/*
  test de service AZURE clim
  
  Envoi sur http://climsecours.azure-mobile.net
     - etatclim
	 - roomtemp
*/

#include <Ethernet.h>   // Pour l'IOT
#include <Time86.h>     // Pour le timestamp des messages

// constantes
#define UNE_SECONDE              1000
#define NBR_SECONDES_1_MINUTE    60
#define NBR_SECONDES_5_MINUTE    300
#define TRACE_PERIOD             3  // une trace toutes les heures

#define WDWAIT      20000 
#define WDREAD      20000 

// variables utiles à l'IOT
const char *server = "xxxxxxxxe.net";
const char *table_temp = "roomtemp";
const char *table_etat = "activity";
const char *ams_key = "xxxxxxxxxxxxxxxxxxxxxxxxx";
int port = 80;
char buffer[64];
EthernetClient client;

float temperature_courante = 0;   // valeur courante de temperature
boolean workcycle = true;
int tracecycle = 12;
char EtatClim[20];

unsigned long Debut_tempoRecord = 0;
unsigned long Now_tempoRecord = 0;

//////////////////// SETUP ////////////////////////////
void setup() {
  Serial.begin(9600);
  Ethernet.begin();   // Configuration automatique des parametres IP de la carte (DHCP) 
  delay(8000);
  
  // trace status to AZURE
  sprintf(EtatClim,"SETUP");
  Trace_etat_clim(EtatClim);
  
  Debut_tempoRecord = millis();
}

////////////////////////////// LOOP /////////////////////////////
void loop() { 
  
  // un cycle de travail tous les 5 minutes
  if (workcycle)
  {
    // prepare le prochain cycle de travail
    workcycle = false;
    Debut_tempoRecord = millis();
	
    // Envoie la temperature au serveur AZURE 
    Trace_valeur_temp();
    temperature_courante += 10;
    
	// une trace toutes les 3*10 sec = 30 sec
    if (tracecycle >= TRACE_PERIOD)
    {
      tracecycle = 0;
      //Vérification de l'état de fontionnement de la climatisation
      sprintf(EtatClim,"DEMARREE");
      Trace_etat_clim(EtatClim);
      delay(UNE_SECONDE);
      sprintf(EtatClim,"TIMEOUT-START");
      Trace_etat_clim(EtatClim);
      delay(UNE_SECONDE);
       sprintf(EtatClim,"SURCHAUFFE");
      Trace_etat_clim(EtatClim);
      delay(UNE_SECONDE);
      sprintf(EtatClim,"REGLEE");
      Trace_etat_clim(EtatClim);
      delay(UNE_SECONDE);
        
    }
    tracecycle++;
    // un cycle de mesure toutes les 10 sec
  }
  else {
    Now_tempoRecord = millis();
    if ( Now_tempoRecord - Debut_tempoRecord > UNE_SECONDE * 10 )  
    {
      workcycle = true;
    }
    delay(UNE_SECONDE * 5);  // pause 10 sec
  }
}

////////////////////////////// CAPTEURS /////////////////////////////

//////////////// FONCTIONS IOT \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Envoi de la temperature au serveur AZURE
void Trace_valeur_temp()
{
  connection_server();
  send_request_temperature(temperature_courante);
  if( wait_response() == false )
  {  
    read_response();
  }
  end_request();
}

// Envoi d'une notification de l'état de la climatisation au serveur AZURE
void Trace_etat_clim(char notification[])
{
  connection_server();
  send_request_clim(notification);
  if( wait_response() == false )
  {  
    read_response();
  }
  end_request();
}

// Connexion au service mobile AZURE
boolean connection_server(void)
{
  Serial.println("connecting...");
  if (client.connect(server, port))    // Tentative de connection
  {
    Serial.println("connected");
    return true;
   
    } else {
    Serial.println("connection failed");
    return false;
  }
}

// Ajout d'une valeur de temperature au service mobile AZURE
void send_request_temperature(float value)
{  
  // POST URI
  sprintf(buffer, "POST /tables/%s HTTP/1.1", table_temp);
  client.println(buffer);
  Serial.println(buffer);
    
  // Host header
  sprintf(buffer, "Host: %s", server);
  client.println(buffer);
  Serial.println(buffer);
    
  // Azure Mobile Services application key
  sprintf(buffer, "X-ZUMO-APPLICATION: %s", ams_key);
  client.println(buffer); 
  Serial.println(buffer);

  // JSON content type
  client.println("Content-Type: application/json");
  Serial.println("Content-Type: application/json");
    
  // POST body
  char val[5];
  int d1 = value;
  float f2 = value - d1;
  int d2 = trunc(f2 * 10);
  sprintf (val, "%d.%01d", d1, d2);
  sprintf(buffer, "{\"sensor\": \"temp\",\"value\": \"%s\"}", val);

  // Content length
  client.print("Content-Length: ");
  client.println(strlen(buffer));
  Serial.print("Content-Length: ");
  Serial.println(strlen(buffer));

  // End of headers
  client.println();
  Serial.println();
    
  // Request body
  client.println(buffer);
  Serial.println(buffer); 
}

// Ajout d'une notification au service mobile AZURE
void send_request_clim(char notification[])
{  
  // POST URI
  sprintf(buffer, "POST /tables/%s HTTP/1.1", table_etat);
  client.println(buffer);
  Serial.println(buffer);

  // Host header
  sprintf(buffer, "Host: %s", server);
  client.println(buffer);
  Serial.println(buffer);
    
  // Azure Mobile Services application key
  sprintf(buffer, "X-ZUMO-APPLICATION: %s", ams_key);
  client.println(buffer); 
  Serial.println(buffer);

  // JSON content type
  client.println("Content-Type: application/json");
  Serial.println("Content-Type: application/json");
    
  // POST body
  char timestamp[30];
  sprintf (timestamp, "M:%dD:%dH:%dm:%ds%d", month(), day(), hour(), minute(), second());
  sprintf(buffer, "{\"timeStamp\": \"%s\",\"status\": \"%s\"}", timestamp, notification);

  // Content length
  client.print("Content-Length: ");
  client.println(strlen(buffer));
  Serial.print("Content-Length: ");
  Serial.println(strlen(buffer));

  // End of headers
  client.println();
  Serial.println();
    
  // Request body
  client.println(buffer);
  Serial.println(buffer); 
}

// Attente de réponse du serveur
boolean wait_response()
{
  boolean wait_timeout = false;
  unsigned long Now = 0;
  unsigned long Debut = millis();
  
  do
  {
    if (!client.connected()) { 
      return false;
    } 
    Now = millis();
  // Tant qu'il n'y a pas de données à lire et que le timeout n'est pas atteind
  }while (!client.available()&& (Now - Debut < WDWAIT )); 
  
  if(Now - Debut > WDWAIT )
  {
    wait_timeout = true;
  }
 
  return wait_timeout;
}

// Lecture de la réponse et affichage sur la console série
void read_response()
{ 
  unsigned long Debut = 0;
  unsigned long Now = 0;
  
  Debut = millis();
  do{
    char c = client.read();
    Serial.print(c);
    
    Now = millis();
  // Tant q'u'il y a des données à lire et que le timeout n'est pas atteind
  } while (client.available() && (Now - Debut < WDREAD )); 
  
  Serial.print("\n");
}

// fermeture connexion
void end_request()
{
  client.stop();
}

