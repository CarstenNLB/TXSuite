REM CT - angelegt am 12.11.2015 

REM ZIP-Date-Suffix
set ZIP-Date-Suffix=%date:~6,4%%date:~3,2%

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

echo Verarbeitung RefiRegister starten >> F:\REF_009_PROD\PROTOKOLL\LB\RefiRegisterVerarbeitung.txt

echo %date% >> F:\REF_009_PROD\PROTOKOLL\LB\RefiRegisterVerarbeitung.txt
echo %time% >> F:\REF_009_PROD\PROTOKOLL\LB\RefiRegisterVerarbeitung.txt

REM RefiRegister starten
F:
cd F:\REF_009_PROD\PROGRAMM\LB
call RefiRegister.bat

copy F:\REF_009_PROD\DATEN\LB\Kredite_Konsortial_009_TXS.xml F:\REF_009_PROD\IMPORT\Kredite_Konsortial_009_TXS.xml
del /q F:\REF_009_PROD\DATEN\LB\Kredite_Konsortial_009_TXS.xml

cd F:\REF_009_PROD\DATEN\LB
copy F:\REF_009_PROD\DATEN\LB\CMS2TXS_009.txt F:\REF_009_PROD\DATEN\LB\Archiv\CMS2TXS_009_%Date-Suffix%.txt
copy F:\REF_009_PROD\DATEN\LB\Konsortialdarlehen.txt F:\REF_009_PROD\DATEN\LB\Archiv\Konsortialdarlehen_%Date-Suffix%.txt
copy F:\REF_009_PROD\DATEN\LB\AktivkontenDaten.txt F:\REF_009_PROD\DATEN\LB\Archiv\AktivkontenDaten_%Date-Suffix%.txt
copy F:\REF_009_PROD\DATEN\LB\Konsortialgeschaefte.txt F:\REF_009_PROD\DATEN\LB\Archiv\Konsortialgeschaefte_%Date-Suffix%.txt

del /q F:\REF_009_PROD\DATEN\LB\CMS2TXS_009.txt 
del /q F:\REF_009_PROD\DATEN\LB\Konsortialdarlehen.txt
del /q F:\REF_009_PROD\DATEN\LB\AktivkontenDaten.txt
del /q F:\REF_009_PROD\DATEN\LB\Konsortialgeschaefte.txt

cd F:\REF_009_PROD\DATEN\LB\Archiv
zip -m F:\REF_009_PROD\DATEN\LB\Archiv\AktivkontenDaten_%ZIP-Date-Suffix%.zip AktivkontenDaten_%Date-Suffix%.txt
zip -m F:\REF_009_PROD\DATEN\LB\Archiv\CMS2TXS_%ZIP-Date-Suffix%.zip CMS2TXS_009_%Date-Suffix%.txt
zip -m F:\REF_009_PROD\DATEN\LB\Archiv\Konsortialdarlehen_%ZIP-Date-Suffix%.zip Konsortialdarlehen_%Date-Suffix%.txt
zip -m F:\REF_009_PROD\DATEN\LB\Archiv\Konsortialgeschaefte_%ZIP-Date-Suffix%.zip Konsortialgeschaefte_%Date-Suffix%.txt

REM RefiRegister Kundenverarbeitung starten
F:
cd F:\REF_009_PROD\PROGRAMM\LB
call Kunde.bat
copy F:\REF_009_PROD\DATEN\LB\Kunde_RefiReg_TXS.xml F:\REF_009_PROD\IMPORT\Kunde_RefiReg_TXS.xml
del /q F:\REF_009_PROD\DATEN\LB\Kunde_RefiReg_TXS.xml

echo Verarbeitung RefiRegister beendet >> F:\REF_009_PROD\PROTOKOLL\LB\RefiRegisterVerarbeitung.txt
echo %date% >> F:\REF_009_PROD\PROTOKOLL\LB\RefiRegisterVerarbeitung.txt
echo %time% >> F:\REF_009_PROD\PROTOKOLL\LB\RefiRegisterVerarbeitung.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\REF_009_PROD\STATUS\LB\Prost_RefiRegister.txt) do (
  java -jar F:\PROST_REFI\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST_REFI\prost.ini %%A %%B %%C %%D
)