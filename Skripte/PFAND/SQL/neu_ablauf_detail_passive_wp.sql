-- SPA 20170902 - Nicht mehr fix 009, sondern auf Basis der originator_id von kredit_daten gesetzt - hier bisher nicht relevant
set linesize 200
set heading off
set pagesize 0
set colsep ';'
set feedback off

spool F:\TXS_009_REPORTS\CMC\ablauf_detail_passive_wp.txt

--tabelle zum merken des Lieferdatums, da dies an den manuellen (=midas) leer ist
create table cmc_temp(maxdat date);
--größtes Datum aus kredit_daten
insert into cmc_temp (select max(gueltig_zum) from kredit_daten);

-- header
select 'ISIN;KONTO;REG;RESTKAP;WAEHR;NOMZINS;ZINSTYP;FAELLIGKEIT;ENDEDATUM;GD945B;QUELLE' from dual;

-- select einmal für MAVISPASSIV dann union mit DARLEHEN-LOANIQ-WP um es bezüglich der kredit_daten_prop einfach zu halten
select 
  nvl(substr(kd.ISIN,1,12),substr(kd.extkey,1,12)) ISIN
  ,'0'
  ,decode(substr(sb.KONFIGURATION_BEZEICHNUNG,1,12),'EBKommunal','11','EBHypotheken', '12', 'EBOEPGKomm', '41', 'EBSchiff', '30','EBFlugzeug', '20','99') cmc_Deckungsmerkmal
  ,to_char(s.Restkapital,'999999999999D99') Restkapital
  ,substr(fx.TEXT,1,3) iso
  , to_char(wpk.nominalzinssatz,'00D99999')
  , decode(wpk.zinstyp,11733,'F',11734,'V',11736,'Z','X')
  ,to_char(wpk.FAELLIGKEIT,'DD.MM.YYYY')
  ,to_char(wpk.Endedatum,'DD.MM.YYYY')
  ,'        ' 
  ,substr(quelle.text,1,11)
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
  ,enum kredittyp
  ,enum kat	
  ,enum ap	
  ,enum quelle	
  ,cmc_temp	
where  eb.KREDIT_ID = kd.KREDIT_ID		
  and  kd.KREDIT_ID = kk.KREDIT_ID(+)	
  and  kd.WERTPAPIER_ID = wp.KREDIT_ID(+)	
  and  wp.KREDIT_ID = wpk.KREDIT_ID(+)	
--  and  nvl(kk.FAELLIGKEIT, wpk.FAELLIGKEIT) = (select nvl(min(z.FAELLIGKEIT), max(v.FAELLIGKEIT))	
--                         from	kredit_kond z, kredit_kond v, kredit_kond k
--                         where	z.FAELLIGKEIT (+) >= bw.BERECHNUNGSSTICHTAG and
--                                v.FAELLIGKEIT (+) < bw.BERECHNUNGSSTICHTAG and	
--                                v.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
--                                z.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
--                                k.KREDIT_ID = nvl(wp.KREDIT_ID, kd.KREDIT_ID))	
  and  kd.KREDIT_ID = s.KREDIT_ID	
--  and  s.ASSIGNED = 1	
  and s.Restkapital > 0
  and  eb.BARWERT_REPORT_ID = bw.BARWERT_REPORT_ID	
  and  eb.GESCHAEFTSTYP = kredittyp.ID	
  and  bw.SZENARIO_ID = zs.SZENARIO_ID	
  and  s.WAEHRUNG = fx.ID	
  and  bw.BARWERT_REPORT_ID = sr.BARWERT_REPORT_ID
  and  kd.KATEGORIE = kat.ID
  and  s.AKTIVPASSIV = ap.ID
  and  kd.QUELLE = quelle.ID
  and  quelle.text in ('PMAVIPFBG','PMAVIOEPG','PMAVIFLUG','PMAVISCHF')
  and  bw.TRANSAKTION_ID = t.TRANSAKTION_ID
  and  sr.SZENARIO_BASIS_ID = sb.SZENARIO_BASIS_ID
  and  zs.SZENARIO_BEZEICHNUNG = 'normal'
  and  cmc_temp.maxdat =  kd.gueltig_zum
---  
union all
---
select 
nvl(substr(kd.ISIN,1,12),substr(kd.extkey,1,12)) ISIN
,substr(kd.lfdnr,1,10)
  ,decode(substr(sb.KONFIGURATION_BEZEICHNUNG,1,12),'EBKommunal','11','EBHypotheken', '12', 'EBOEPGKomm', '41', 'EBSchiff', '30','EBFlugzeug', '20','99') cmc_Deckungsmerkmal
  ,to_char(s.Restkapital,'999999999999D99') Restkapital
  ,substr(fx.TEXT,1,3) iso
  , to_char(wpk.nominalzinssatz,'00D99999')
  , decode(wpk.zinstyp,11733,'F',11734,'V',11736,'Z','X')
  ,to_char(wpk.FAELLIGKEIT,'DD.MM.YYYY')
  ,to_char(wpk.Endedatum,'DD.MM.YYYY') 
  ,decode(wpp.value,null,'',substr(wpp.value,1,2)||'.'||substr(wpp.value,3,2)||'.'||substr(wpp.value,5,4) )
  ,substr(quelle.text,1,11)
from   
  einzelbarwert eb
  ,kredit_daten kd
  ,kredit_kond kk
  ,kredit_daten wp
  ,kredit_kond wpk
  ,kredit_daten_prop wpp		
  ,slice s
  ,barwert_report bw	
  ,zins_szenarien zs
  ,szenario_report sr	
  ,transaktion t	
  ,szenario_basis sb
  ,enum fx	
  ,enum kredittyp
  ,enum kat	
  ,enum ap	
  ,enum quelle	
  ,cmc_temp	
where  eb.KREDIT_ID = kd.KREDIT_ID		
  and  kd.KREDIT_ID = kk.KREDIT_ID(+)	
  and  kd.WERTPAPIER_ID = wp.KREDIT_ID(+)	
  and  wp.KREDIT_ID = wpk.KREDIT_ID(+)	
--  and  nvl(kk.FAELLIGKEIT, wpk.FAELLIGKEIT) = (select nvl(min(z.FAELLIGKEIT), max(v.FAELLIGKEIT))	
--                         from	kredit_kond z, kredit_kond v, kredit_kond k
--                         where	z.FAELLIGKEIT (+) >= bw.BERECHNUNGSSTICHTAG and
--                                v.FAELLIGKEIT (+) < bw.BERECHNUNGSSTICHTAG and	
--                                v.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
--                                z.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
--                                k.KREDIT_ID = nvl(wp.KREDIT_ID, kd.KREDIT_ID))	
  and  kd.KREDIT_ID = s.KREDIT_ID	
--  and  s.ASSIGNED = 1	
  and s.Restkapital > 0
  and  eb.BARWERT_REPORT_ID = bw.BARWERT_REPORT_ID	
  and  eb.GESCHAEFTSTYP = kredittyp.ID	
  and  bw.SZENARIO_ID = zs.SZENARIO_ID	
  and  s.WAEHRUNG = fx.ID	
  and  bw.BARWERT_REPORT_ID = sr.BARWERT_REPORT_ID
  and  kd.KATEGORIE = kat.ID
  and  s.AKTIVPASSIV = ap.ID
  and  kd.QUELLE = quelle.ID
  and  quelle.text in ('PDARLPFBG','PDARLOEPG','PDARLSCHF','PDARLFLUG','PLIQPFBG','PLIQOEPG','PLIQSCHF','PLIQFLUG')
  and  bw.TRANSAKTION_ID = t.TRANSAKTION_ID
  and  sr.SZENARIO_BASIS_ID = sb.SZENARIO_BASIS_ID
  and  zs.SZENARIO_BEZEICHNUNG = 'normal'
  and  cmc_temp.maxdat =  kd.gueltig_zum
  and  wpp.propertydefinition_id (+) = 100068
  and  wpp.object_id (+) = wp.KREDIT_ID  
order by ISIN 
;

--datumsmerker droppen
drop table cmc_temp;

spool off

exit