> Schreiben Sie ein verteiltes Programm zur Umsetzung des Produzenten-/Konsumentenproblems.
> 
> Der serverseitige Teil verwaltet einen Ringpuffer, in den Produzenten- und Konsumenten-Klientenprogramme ihre Produkte einstellen / entnehmen können.
> **Klientenprogramme sollen außerdem beim Server anfragen können, wie viele Einträge gerade** im Ringpuffer vorhanden sind.
> 
> Die Klientenprogramme (Produzenten und Konsumenten in beliebiger Zahl) erteilen ihre Aufträge an den Server über das Netzwerk.
> 
> Da viele solche Anforderungen auch gleichzeitig über das Netzwerk eintreffen können, überlegen Sie, wie die Abarbeitung dieser Anforderungen durch den Server synchronisiert werden müssen.
> 
> Für diese Aufgabe 4 ist für den verteilten Programmieransatz RMI zu nehmen.
> 
> Die Abnahme des Programms erfolgt nach Absprache mit mir, zur Vorführung des lauffähigen Programms sollen mehrere Rechner eingesetzt werden. 
