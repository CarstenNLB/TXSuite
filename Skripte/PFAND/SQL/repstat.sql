set linesize 200
set heading off
set pagesize 0
set feedback off

col QUELLE for a15

--datumsmerker droppen
drop table cmc_temp;
--tabelle zum merken des Lieferdatums, da dies an den manuellen (=midas) leer ist
create table cmc_temp(maxdat date);
--größtes Datum aus kredit_daten
insert into cmc_temp (select max(gueltig_zum) from kredit_daten);

-- spool in Datei
spool F:\TXS_009_PROD\PROTOKOLL\LB\repstat.txt

select 'Anzahl der gelieferten Geschäfte fuer den ' || to_char(maxdat,'YYYY-MM-DD') || ', fuer die ein BW berechnet wurde'  from cmc_temp;

select '-------------------------------------------------------------------------------------' from dual;

select 
  quelle.text QUELLE
  ,count(*) ANZAHL
  , ';'
from 
  einzelbarwert eb
  ,kredit_daten kd
  ,kredit_kond kk
  ,kredit_daten wp
  ,kredit_kond wpk
  ,slice s
  ,barwert_report bw	
  ,zins_szenarien zs
  ,szenario_report sr	
  ,transaktion t	
  ,szenario_basis sb	
  ,enum fx	
  ,enum	kredittyp
  ,enum kat	
  ,enum ap	
  ,enum quelle
  ,cmc_temp	
where  eb.KREDIT_ID = kd.KREDIT_ID		
  and  kd.KREDIT_ID = kk.KREDIT_ID(+)	
  and  kd.WERTPAPIER_ID = wp.KREDIT_ID(+)	
  and  wp.KREDIT_ID = wpk.KREDIT_ID(+)	
  and  nvl(kk.FAELLIGKEIT, wpk.FAELLIGKEIT) = (select nvl(min(z.FAELLIGKEIT), max(v.FAELLIGKEIT))	
                         from	kredit_kond z, kredit_kond v, kredit_kond k
                         where	z.FAELLIGKEIT (+) >= bw.BERECHNUNGSSTICHTAG and
                                v.FAELLIGKEIT (+) < bw.BERECHNUNGSSTICHTAG and	
                                v.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
                                z.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
                                k.KREDIT_ID = nvl(wp.KREDIT_ID, kd.KREDIT_ID))	
  and  kd.KREDIT_ID = s.KREDIT_ID	
  and  s.ASSIGNED = 1	
  and  eb.BARWERT_REPORT_ID = bw.BARWERT_REPORT_ID	
  and  eb.GESCHAEFTSTYP = kredittyp.ID	
  and  bw.SZENARIO_ID = zs.SZENARIO_ID	
  and  s.WAEHRUNG = fx.ID	
  and  bw.BARWERT_REPORT_ID = sr.BARWERT_REPORT_ID
  and  kd.KATEGORIE = kat.ID
  and  s.AKTIVPASSIV = ap.ID
  and  kd.QUELLE = quelle.ID
  and  bw.TRANSAKTION_ID = t.TRANSAKTION_ID
  and  sr.SZENARIO_BASIS_ID = sb.SZENARIO_BASIS_ID
  and  zs.SZENARIO_BEZEICHNUNG = 'normal'
  and  cmc_temp.maxdat =  kd.gueltig_zum
group by quelle.text
order by quelle.text asc
;

select '-------------------------------------------------------------------------------------' from dual;

spool off

--datumsmerker droppen
drop table cmc_temp;

exit

