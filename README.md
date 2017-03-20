# :warning: Achtung
__Die Pflege dieses Projekts default erfolgt AUSSCHLIESSLICH durch *F-I-TS Mitarbeiter*__.
<br><br>
## Projekt NORDLB/DEFAULT

>Dieses Projekt dient als __Vorlage__ für neue Projekte der Gruppe nordlb. Bei Anforderung zum Betrieb einer weiteren Anwendung durch die NordLB kann die Struktur kopiert werden.
>Die neue Anwendung und das entsprechende gleichnamige Projekt wird in diesem _Readme_ als _[AppX]_ im Folgenden referenziert.


## Vorgehensweise
  Das Projekt der neuen Anwendung in GITLab durch unser F-I-TS Anwendungsplaner Team anlegen lassen. Bitte einen [Owner](https://git.f-i-ts.de/groups/nordlb/group_members) der Gruppe <code>nordlb</code> ansprechen. Benutzer der Rolle [Master](https://git.f-i-ts.de/help/permissions/permissions#Group) oder [Developer](https://git.f-i-ts.de/help/permissions/permissions#Group) haben dafür keine Berechtigung.

  Der Projektname ergibt sich aus dem Kürzel der Anwendung, siehe <a href="onenote:http://intranet.officelan.izb/oe/56030/Middleware/OneNote_Global/TLSApplSrv/NordLB.one#NORD/LB%20-%20WAS%20Anwendungen&section-id={3FFB38FE-49EB-48F5-8BB7-4606DADE4FCC}&page-id={CE8118A1-FD9E-4B31-90DD-3E78E7599261}&end">NORD/LB - WAS Anwendungen</a>. Wichtig ist, im Projekt Pfad _nordlb_ auszuwählen.

  Nach Projektanlage bitte in das Projekt wechseln (https://git.f-i-ts.de/nordlb/&lt;project&gt;).
  <br>
  :zap: _Update 27.4.2016: In der Gruppe **nordlb** wurde der Benutzer ansible als Reporter hinzugefügt. Daher muss dies nicht mehr bei jedem Projekt erfolgen._
  <br><br>
  Zudem ist der Benutzer __ansible__ mit Rolle **_Reporter_** der Gruppe zugewiesen. Damit hat Ansible automatisch Zugriff auf das neue Projekt.
  <br><br>:exclamation: Nur der Inhaber der GIT-Rolle **_Owner_** oder **_Master_** hat die Berechtigung Änderungen vorzunehmen.
  <br><br>
  Anmelden am Server mit lokalem GIT Repository (_H&uuml;pfburg login1x_):<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>cd /appdata/daten/appusr/git/[userid]</code><br><br>
  Falls noch nicht geschehen, das Projekt _default_ lokal klonen:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git clone git@git.admnet.fits:nordlb/default</code><br>
  <br>
  Auf jeden Fall den aktuellen Status holen:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>cd default</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git pull</code><br>
  <br>

> :zap: __Best Practise Tipp__: Projekt inklusive Projektstruktur per Skript erstellen<br>
  &nbsp;&nbsp;&nbsp;&nbsp;Die folgenden Schritte (Abschnitt Manuelle Schritte) k&ouml;nnen mit dem Skript<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>createGitRepoStructure.sh</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;ausgef&uuml;hrt werden. Das Skript befindet sich im Projekt *__middleware/Development__* im Unterordner <br>
  &nbsp;&nbsp;&nbsp;&nbsp;*__GitScripts__*.<br><br>
  &nbsp;&nbsp;&nbsp;&nbsp;Als Parameter sind der Projektname inkl. Beschreibung, dass Referenzprojekt (default), der<br>
  &nbsp;&nbsp;&nbsp;&nbsp;Workspacepfad, die Namespace-ID (nordlb=153), die Brances und die protected Brances zu &uuml;bergeben.<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>createGitRepoStructure.sh -p [DIR2CREATE_REPO] -s [ORIGINAL-REPO] -d [REPO] -c [DESCRIPTION]<br></code>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>-n [Namespace-ID] -b “[BRANCH1] [BRANCH2] …“ -z “[BRANCH2] …“</code><br></code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;Ein Beispiel anhand von Baseone-Keys:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>createGitRepoStructure.sh -p /appdata/daten/appusr/git/[userid] -s default -d boky</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>-c "Base/ONE Keys (AL1621-20100401)" -n 153 -b “TENT TABS PREP PROD“ -z “PREP PROD“</code><br><br>
  :exclamation: Die Projektanlage erfolgt aus dem o.g. Skript heraus und muss somit nicht mehr manuell erfolgen.<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Die weiter u.g. Anlage erfordert die Erstellung des Projekts wiederum über die GIT-Lab-GUI

## Manuelle Schritte:
  Bitte das neue Projekt lokal klonen:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git clone git@git.admnet.fits:nordlb/[appX]</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>cd [appX]</code><br>

  Jetzt kann die Struktur des _Default_ Projektes (Template) kopiert werden:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>cp -r ../default/* . </code><br>


### Struktur
  Es gibt in diesem Default Projekt nur den _master_ Branch. In den geklonten Projekten werden dagegen die Branches
* TENT
* TABS
* PREP
* PROD
  entsprechend den vorgegebenen Stages verwendet.

### Stages anlegen
  In das neue Projekt lokal wechseln:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>cd /appdata/daten/appusr/git/[userid]/[appX]</code><br>
  Sicherstellen, dass der HEAD auf master zeigt:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git checkout master</code><br>

  Neue Branches TENT, TABS, PREP und PROD basiserend auf dieser Struktur erstellen:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git branch TENT</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git branch TABS</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git branch PREP</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git branch PROD</code><br><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git checkout TENT</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git push -u origin TENT</code><br>
  <br>
  Anschliessend TENT als __default__ Branch (über die GITLAB Web Oberfläche) setzen und den _master_ Branch löschen. Diese Aktionen auch im lokalen Repository ausführen:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git branch -d master</code><br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>git push</code><br>

  Die Branches PREP und PROD sind als __protected__ zu markieren (über die GITLAB Web Oberfläche).<br>
  Die Features _Issues_ und _Builds_ sind in den Projekteinstellungen zu deaktivieren.<br>

## Weitere Schritte
### Ansible Repository
  Das ansible Repository liegt in GIT Projekt _deployment/applications_.
  In das Projekt <code>deployment/applications</code> wechseln.

  Das neue Repository __nordlb/[appX]__ ist in der Datei <code>projects.yaml</code> einzutragen.
  <br><br>:exclamation: Dieser Schritt kann nur von einem sehr eingeschränktem Benutzerkreis ausgeführt werden.

  Beispiel für ein neues Projekt:<br>
```YAML
          -
            type: "ssh"
            user: "git"
            owner: "nordlb"
            repository: "appX"
            branch: "TENT"
            productowner: "nordlb"
            productname: "appX"
            stage: "TENT"
            auto: "active"
            password-file: "/appexec/ansible/vault.txt"
            schedule: "@every 30s"
            ansibleversion: "2"
```
  :zap: Auto: _"active"_ bedeutet ein automatisches Deployment in der TENT Umgebung, wenn die Entwickler eine neue Version einchecken.
  Anschließend wird ein Unterverzeichnis im Verzeichnis <code>nordlb/</code> mit dem Projektnamen ([appX]) angelegt und jeweils ein Unterverzeichnis pro Stage.
  In diesen Verzeichnissen ist dann auch die Hostdatei *__hosts__* zu pflegen.<br>
> :zap: __Best Practise Tipp__: Dateien und Verzeichnisse können als Kopie mit <code>cp -pr</code> aus einem bestehenden Projekt einer NORDLB WAS Anwendung übernommen werden.<br>
  Zu diesem Zweck dient das Skript unterhalb von **deployment/applications**<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>nordlb/create_structure_new_nlb_app.ksh</code><br><br>
  Als Parameter ist der Projektname zu &uuml;bergeben. Beispiel:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;<code>create_structure_new_nlb_app.ksh bobv</code>

### Globin-cli
  Prüfung, ob die Projekte gelesen werden können (dies kann nach Pflege der projects.yaml einige Minuten dauern):<br>
&nbsp;&nbsp;<code>goblin-cli list|grep -i nordlb</code>

### Host Pflege
  Diese hosts Datei für ansible definiert die Server und Gruppen für die Playbooks. Da die Servernamen stageabhängig sind, gibt es für jede Stage eine hosts Datei.

  Verzeichnis in deployment(/applications
  In Projekt wechseln, Ordner nordlb.
  Es ist ein Unterordner mit dem Namen der ANwendung anzulegen.

  Unterordner für jede Stage erstellen.<br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>cd ${appx}</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>mkdir TENT</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>mkdir TABS</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>mkdir PREP</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>mkdir PROD</code><br>

hosts Datei für jede Stage anlegen, siehe NordLB - Aufbau hosts:<br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>vi TENT/hosts</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>vi TABS/hosts</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>vi PREP/hosts</code><br>
&nbsp;&nbsp;&nbsp;&nbsp;<code>vi PROD/hosts</code><br>

> :zap: __Best Practise Tipp__: Eine hosts Datei aus einem anderen App-Projekt der Nord/LB kopieren.<br>


## Weiterführende Informationen
* <a href="onenote:http://intranet.officelan.izb/oe/56030/Middleware/OneNote_Global/TLSApplSrv/NordLB.one#NORD/LB%20-%20HowTo%20Aufbau%20GIT%20/%20Ansible%20/%20Server&section-id={3FFB38FE-49EB-48F5-8BB7-4606DADE4FCC}&page-id={0DA1B852-9AD3-47B4-AC54-D837DF70B729}&end">OneNote - NORD/LB - HowTo: Aufbau GIT / Ansible / Server Strukturen für neue Anwendungen</a>
* <a href="onenote:http://intranet.officelan.izb/oe/56030/Middleware/OneNote_Global/TechnikAllg/Ansible.one#Neues%20Ansible-Projekt%20einrichten&section-id={E8551F18-CFD1-4C19-9923-338245219C99}&page-id={8484E73F-F929-48A7-A776-230DA33122A8}&end">OneNote - Neues Ansible-Projekt einrichten</a>
* <a href="onenote:http://intranet.officelan.izb/oe/56030/Middleware/OneNote_Global/TLSApplSrv/NordLB.one#NORD/LB-%20Deployment%20Verfahren&section-id={3FFB38FE-49EB-48F5-8BB7-4606DADE4FCC}&page-id={B7E5D484-6060-4CC9-9042-84640EB41824}&end">OneNote - NORD/LB- Deployment Verfahren</a>
