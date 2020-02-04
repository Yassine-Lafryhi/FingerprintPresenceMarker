 #include <LiquidCrystal.h>
#include <SPI.h>
#include <TimeLib.h>
#include <SD.h>
#include <Adafruit_Fingerprint.h>
#include <Wire.h>
#include <DS1307RTC.h>
File myFile;
 #include <SoftwareSerial.h>
SoftwareSerial mySerial(2, 3);

Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);

bool sp[80];//student_present
uint8_t id;
const int rs = 9, en = 10, d4 = 7, d5 = 8, d6 = 5, d7 = 14;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);


void setup()
{
  lcd.begin(16, 2);

  mettez_doigt();

  Serial.begin(9600);

  while (!Serial);  // For Yun/Leo/Micro/Zero/...
  setSyncProvider(RTC.get);
  delay(100);
  if (!SD.begin(4)) {
    while (1);
  }
 

  finger.begin(57600);
 }
uint8_t readnumber(void) {
  uint8_t num = 0;
  
  while (num == 0) {
    while (! Serial.available());
    num = Serial.parseInt();
  }
  return num;
}
void loop()                     // run over and over again
{
  String T = String(year()) + "/" + String(month()) + "/" + String(day());
  if (Serial.readString() == T) {
    myFile = SD.open(T + "/l");

    if (myFile) {

      while (myFile.available()) {
 
        Serial.write(myFile.read());
      }

      myFile.close();
    }







  }












  getFingerprintIDez();
  delay(50);            //don't ned to run this at full speed.
}

uint8_t getFingerprintID() {
  uint8_t p = finger.getImage();
  switch (p) {
    case FINGERPRINT_OK:
      //Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      // Serial.println("No finger detected");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      // Serial.println("Communication error");
      return p;
    case FINGERPRINT_IMAGEFAIL:
      // Serial.println("Imaging error");
      return p;
    default:
      //Serial.println("Unknown error");
      return p;
  }

  // OK success!

  p = finger.image2Tz();
  switch (p) {
    case FINGERPRINT_OK:
      // Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      //Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      // Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      // Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      // Serial.println("Could not find fingerprint features");
      return p;
    default:
      //Serial.println("Unknown error");
      return p;
  }

  // OK converted!
  p = finger.fingerFastSearch();
  if (p == FINGERPRINT_OK) {
    //  Serial.println("Found a print match!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    // Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_NOTFOUND) {
    // Serial.println("Did not find a match");
    return p;
  } else {
    // Serial.println("Unknown error");
    return p;
  }

  // found a match!


  /*Serial.print("Found ID #"); Serial.print(finger.fingerID);
    Serial.print(" with confidence of "); Serial.println(finger.confidence); */

  return finger.fingerID;
}

// returns -1 if failed, otherwise returns ID #
int getFingerprintIDez() {
  uint8_t p = finger.getImage();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.image2Tz();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -1;


  process_student(finger.fingerID);

















  // found a match!
  /*Serial.print("Found ID #"); Serial.print(finger.fingerID);
    Serial.print(" with confidence of "); Serial.println(finger.confidence);*/
  return finger.fingerID;
}








void presence_marked()
{
  lcd.clear();
  lcd.print( "Presence Marked");

}



void mettez_doigt()
{
  lcd.clear();
  lcd.print( "Put Your Finger");

}


void  save_on_memory()
{

  lcd.clear();
  lcd.print( " Processing...");
  delay(1000);
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }




  if (!SD.begin(4)) {

    while (1);
  }



  String file = String(hour()) + ":" + String(minute());
  //String F=String(day())+"/"+String(month())+"/"+String(year());

  String T = String(year()) + "/" + String(month()) + "/" + String(day());
  SD.mkdir(T);


  //SD.remove("ESTSB.PRS");
  myFile = SD.open(T + "/l", FILE_WRITE);

  // if the file opened okay, write to it:
  if (myFile) {



    myFile.println(T + "-" + file);


    for (int y = 0; y < 80; y++)
    {

      if (sp[y] == false)
      {
        myFile.println(String((y + 1)) );

        
      }

      



    }


    // close the file:
    myFile.close();
    //Serial.println("Done");
  } else {

  }

  // re-open the file for reading:
  myFile = SD.open("l");
  if (myFile) {
    // Serial.println("file :");

    // read from the file until there's nothing else in it:
    while (myFile.available()) {
     //Serial.write(myFile.read());
    }

    myFile.close();
  } else {
    // if the file didn't open, print an error:
    // Serial.println("error opening file");
  }



  lcd.clear();
  lcd.print( "Processing End");
  delay(1500);
  mettez_doigt();
}

void deja_present() {

  lcd.clear();
  lcd.print( "Already Marked");

}




void process_student(int i)
{
  if (i >= 1 && i <= 80)
  {
    if (sp[i - 1] == false)
    {



      sp[i - 1] = true;
      presence_marked();




    } else {
      deja_present();
    }


  }

  delay(1000);
  mettez_doigt();





  if (i == 115 || i == 116 )
  {
    save_on_memory();

  }
}
