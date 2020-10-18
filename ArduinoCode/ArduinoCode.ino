#include <LiquidCrystal.h>
#include <SPI.h>
#include <TimeLib.h>
#include <SD.h>
#include <Adafruit_Fingerprint.h>
#include <Wire.h>
#include <DS1307RTC.h>
#include <SoftwareSerial.h>
File file;
SoftwareSerial serial(2, 3);
Adafruit_Fingerprint fingerprint = Adafruit_Fingerprint(&serial);
bool students[80];
const int rs = 9, en = 10, d4 = 7, d5 = 8, d6 = 5, d7 = 14;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);
void setup()
{
  lcd.begin(16, 2);
  putYourFinger();
  Serial.begin(9600);
  while (!Serial);
  setSyncProvider(RTC.get);
  delay(100);
  if (!SD.begin(4)) {
    while (1);
  }
  fingerprint.begin(57600);
 }
void loop()
{
  String date = String(year()) + "/" + String(month()) + "/" + String(day());
  if (Serial.readString() == date) {
    file = SD.open(date + "/l");
    if (file) {
      while (file.available()) {
        Serial.write(file.read());
      }
      file.close();
    }
  }
  getFingerprintIDez();
  delay(50);
}
uint8_t getFingerprintID() {
  uint8_t p = fingerprint.getImage();
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
  p = fingerprint.image2Tz();
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
  p = fingerprint.fingerFastSearch();
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
  /*Serial.print("Found ID #"); Serial.print(fingerprint.fingerID);
    Serial.print(" with confidence of "); Serial.println(fingerprint.confidence); */
  return fingerprint.fingerID;
}
// returns -1 if failed, otherwise returns ID #
int getFingerprintIDez() {
  uint8_t p = fingerprint.getImage();
  if (p != FINGERPRINT_OK)  return -1;
  p = fingerprint.image2Tz();
  if (p != FINGERPRINT_OK)  return -1;
  p = fingerprint.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -1;
  processStudent(fingerprint.fingerID);
  // found a match!
  /*Serial.print("Found ID #"); Serial.print(fingerprint.fingerID);
    Serial.print(" with confidence of "); Serial.println(fingerprint.confidence);*/
  return fingerprint.fingerID;
}
void presenceMarked()
{
  lcd.clear();
  lcd.print( "Presence Marked");
}
void putYourFinger()
{
  lcd.clear();
  lcd.print( "Put Your Finger");
}
void  saveOnMemory()
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
  //String file = String(hour()) + ":" + String(minute());
  //String F=String(day())+"/"+String(month())+"/"+String(year());
  String date = String(year()) + "/" + String(month()) + "/" + String(day());
  SD.mkdir(date);
  //SD.remove("ESTSB.PRS");
  file = SD.open(date + "/l", FILE_WRITE);
  // if the file opened okay, write to it:
  if (file) {
    file.println(date + "-" + file);
    for (int y = 0; y < 80; y++)
    {
      if (students[y] == false)
      {
        file.println(String((y + 1)) );
        
      }
    }
    // close the file:
    file.close();
    //Serial.println("Done");
  } else {
  }
  // re-open the file for reading:
  file = SD.open("l");
  if (file) {
    // Serial.println("file :");
    // read from the file until there's nothing else in it:
    while (file.available()) {
     //Serial.write(file.read());
    }
    file.close();
  } else {
    // if the file didn't open, print an error:
    // Serial.println("error opening file");
  }
  lcd.clear();
  lcd.print( "Processing End");
  delay(1500);
  putYourFinger();
}
void alreadyMarked() {
  lcd.clear();
  lcd.print( "Already Marked");
}
void processStudent(int i)
{
  if (i >= 1 && i <= 80)
  {
    if (students[i - 1] == false)
    {
      students[i - 1] = true;
      presenceMarked();
    } else {
      alreadyMarked();
    }
  }
  delay(1000);
  putYourFinger();
  if (i == 115 || i == 116 )
  {
    saveOnMemory();
  }
}
