---
layout: download
---

# Installation

Die JEE-Generatoren sind in Java implementiert, daher muss die Java Runtime Environment installiert sein.

Es gibt zwei einfache Wege, die JEE-Generatoren ans Laufen zu bringen. Eine
vorkonfigurierte Eclipse-Distribution ist bereits mit allen notwendigen
Plugins vorinstalliert verfügbar.  Alternativ kann über eine Update Site ein
Eclipse mit allen notwendigen Plugins nachinstalliert werden.

## Installiere vorkonfiguriertes Eclipse

 * Laden Sie sich die Distribution zum passenden Betriebssystem herunter.
 * Das Archiv muss im Verzeichnis Ihrer Wahl ausgepackt werden. Windows Benutzer sollten ein Verzeichnis möglichst in der Nähe des Wurzelverzeichnisses wählen, da Windows nur komplette Pfade mit maximal 256 Zeichen akzeptiert und Eclipse eine tiefe Verzeichnisstruktur benutzt.
 * Danach kann Eclipse gestartet werden und der gewünschte Workspace ausgewählt werden.

## JEE-Generatoren von der Update Site installieren

Wenn Eclipse läuft:

 * Wähle _Help -> Install New Software..._ aus der Menüleiste und _Add..._ Geben Sie eine der Update Site URLs von oben an. Diese Site fasst alle benötigten Komponenten und Abhängigkeiten für die JEE-Generatoren zusammen.
 * Wähle den passenden JEE-Generator aus der Kategorie JEE-Generators und durch wiederholtes Klicken von _Next_ und einem abschließenden _Finish_ wird die Installation abgeschlossen.
 * Nach einem kurzen Download und Restart des Eclipse stehen die JEE-Generatoren zum Gebrauch zur Verfügung.

##FAQ

### Was ist ein Workspace?

Ein Workspace ist das Verzeichnis für die Benutzerdaten und Projektdateien.

### Was ist eine Updatesite? 

Eclipse hat einen eingebauten Updatemanager, dieser arbeitet mit so
genannten Updatesites als Quelle. Dadurch bleibt die Software immer aktuall.
In Eclipse öffne das _Help -> Menü_ und
klicke auf _Install new Software..._

### Was ist die Lizenz der JEE-Generatoren?

Die JEE-Generatoren sind unter der [Eclipse Public License (EPL)](http://www.eclipse.org/legal/epl-v10.html) veröffentlicht.
