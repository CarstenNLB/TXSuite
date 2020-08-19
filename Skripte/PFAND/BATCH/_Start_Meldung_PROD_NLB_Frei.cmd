REM *************************************************************************************************************************************************************************
REM _Start_Meldung_PRODFREE.cmd
REM Vorlaeufer
REM --- Part 2: Spiegelung der Produktion in die Barwert-DB - _Start_SPIEGELUNG_NLB.cmd
REM REM Part 3: Meldung an FB - PROD frei - hier
REM Nachfolger
REM --- Part 4: CMC und ABACUS beliefern
REM ---------4.1  _start_cmc_nlb.bat
REM ---------4.2  _start_aba_nlb.bat
REM SPA 20190704 Redesign 
REM *************************************************************************************************************************************************************************
REM Ab hier kann im Produktionssystem gearbeitet werden
REM *************************************************************************************************************************************************************************
powershell F:\TXS_009_PROD\PROGRAMM\LB\nlbprodready_mail.ps1
REM *************************************************************************************************************************************************************************
rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Meldung_PROD_NLB_Frei.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
REM *************************************************************************************************************************************************************************