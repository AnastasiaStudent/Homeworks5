> Schreiben Sie ein verteiltes Programm, das eine **Online-Meinungsumfrage zu einem vorgegebenen Thema** umsetzt.
> 
> Auf dem **serverseitigen Teil** wird dabei der aktuelle Stand der Meinungsumfrage (Abstimmmöglichkeiten: **Zustimmung=ja, Ablehnung=nein, Enthaltung=sonstiges)** verwaltet.

>Der momentane Abstimmungsstand soll dabei **persistent in einer Datei abgespeichert werden**. Dieser Serverteil soll als **konkurrenter Server** umgesetzt werden.
> 
> Unter Nutzung des **Cliententeil** können beliebig viele Nutzer sowohl ihre Meinung zu diesem Thema kundtun (ja, nein, Enthaltung) als auch den aktuellen Stand der Abstimmung vom Server erfragen.

> **Diese Aufgabe 3 ist unter Nutzung des verteilten Programmieransatzes Socket-Programmierung zu bearbeiten.**
> 
> Die ablaufenden Klientenprogrammaufrufe (Abstimmwillige in beliebiger Zahl von verschiedenen Rechnern) erteilen ihre 
> Aufträge (Meinungsäußerungen) an den Server über das Netzwerk.
> 
> Da viele solche Meinungsäußerungen auch gleichzeitig über das Netzwerk beim Server eintreffen können, überlegen Sie, 
> ob die Bearbeitung dieser Anforderungen durch den Server synchronisiert werden muss.
> 
> Die Abnahme des Programms erfolgt nach Absprache mit mir, zur Vorführung des lauffähigen Programms müssen mehrere Rechner eingesetzt werden. 



