REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM Kopieren der TXS-Reports ggf. inklusive von Auswertungen zum Ultimo
REM auf das Fachbereichslaufwerk
REM SU 20170120
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

F:
REM ggf. Ultimoreports zum Archiv hinzufügen
cd F:\TXS_009_REPORTS\TXS

zip -m ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip *.xl*

REM Überbleibsel löschen
del /q *.xl*
del /q *.pdf
del /q *.csv

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM "zip mit daypointer" archivieren
copy ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip archiv

REM "zip taeglich" mit daypointer archivieren
copy ReportTXS_009_taeglich.zip F:\TXS_009_REPORTS\TXS\archiv\ReportTXS_009_taeglich_%date:~6,4%%date:~3,2%%date:~0,2%.zip

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM ********* VORSICHT *********
REM copy auf das Gruppenlaufwerk Fachbereich NLB

net use Y: /delete /y

REM CT 11.09.2014
REM SU 26.02.2016 dedizierter Rechnername vor kbk entfernt
net use Y: \\kbk.NordLB.local\Dbe\107920\107241 Edrpwd100 /USER:KBK\TXSTRV01

copy ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip Y:\"3 TXS-Reports"
copy ReportTXS_009_taeglich.zip Y:\"3 TXS-Reports"

net use Y: /delete /y
REM ********* VORSICHT *********
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

REM Arbeitstand löschen
del /q ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip
del /q ReportTXS_009_taeglich.zip
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

