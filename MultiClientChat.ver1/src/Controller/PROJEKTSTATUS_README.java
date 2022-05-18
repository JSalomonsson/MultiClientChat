package Controller;

public class PROJEKTSTATUS_README {
    /*
    1. För att köra programmet, starta ServerMain först, och sedan ClientMain
    2. Struktursaker vi bör ändra på:
       - Hur vi skapar en användare är jätterörigt, jag föreslår att vi samlar all den funktionen
         i LoginWindow så gott det går, tror det gör det tusen gånger enklare att skapa diagramen sen
         om vi inte har tusen relationer kors och tvärs
    3. Vi måste vara noga med att hålla isär client och server, vi har mergeat dem lite, på i klassernas
    men också att hålla isär flöder *helt*, med separata controllers
    4. Föreslår att alla meddelande skickar från clienten till servern som networkMessage (även chatMessage)
       och att vi i servern kollar vilken typ av meddelande det är, Network-message kan hålla Object, dvs ett chatmessage.
       Anledningen är att vi kommer behöva skicka många olika typer av meddelanden client -> server (chatmeddelande, att någon loggar på,
       loggar ut etc) och om vi helt enkelt samlar det i en switch-sats i servern så tar vi bort väldigt mkt potentiella fel
       och vi undviker att röra ihop det. Se nedan hur tanken är!
     5. Vore fint om vi kommenterar all kod vi lägger till, det är svårt att läsa annars :D


     HUR DET ÄR TÄNKT KRING NETWORKMESSAGE vs CHATMESSAGE:

     Såhär hänger det ihop:

- Allt som skickas mellan server och klient (oavsett riktning, eller vad det är) skickas som ett NetworkMessage.
- En NetworkMessage består av två saker: en sträng som talar om vad det är för typ av information som skickas, och ett objekt som är själva informationen

När man ska skicka ett ChatMeddelande så packar man in det i ett NetworkMessage, och talar om att "det här är ett chatmeddelande":

msg = new NetworkMessage("chatmessage", theChatMessageObject);

...om man istället ska skicka ett User-objekt:

msg = new NetworkMessage("userinfo", theUserObject);

När man tar emot ett meddelande så använder man informationen för att packa upp data:

msg = (NetworkMessage) ois.readObject();

if (msg.getMessageType() == "userinfo") {
    user = (User) msg.getDataObject();
}


     */
}
