-- SPA 20170902 - Nicht mehr fix 009, sondern auf Basis der originator_id von kredit_daten gesetzt
-- SPA 20170903 - where-klausel für Mandant 004 BLB
set linesize 200
set heading off
set pagesize 0
set colsep ';'
set feedback off

ALTER SESSION SET NLS_NUMERIC_CHARACTERS = '.,';

--datumsmerker droppen
drop table cmc_temp;
--tabelle zum merken des Lieferdatums, da dies an den manuellen (=midas) leer ist
create table cmc_temp(maxdat date);
--größtes Datum aus kredit_daten
insert into cmc_temp (select max(gueltig_zum) from kredit_daten);

spool F:\TXS_009_REPORTS\ABA\ABA.txt

select 'INSTNR;ANWNR;KTONRE;ISIN;STZART;USCHL;decktyp;deckpos;Restkapital;Barwert;iso;Stichtag;DepotNr' from dual;

select 
   decode(kd.originator_id,1,'009',2,'004') mandant
  ,decode(substr(quelle.text,1,2),'AM','84','AD','30','99')
  ,'00'
  ,substr(kd.ISIN,1,12) ISIN
  ,'00'
  ,'0000000000'
  ,decode(substr(sb.KONFIGURATION_BEZEICHNUNG,1,12),'EBKommunal','11','EBHypotheken', '12', 'EBOEPGKomm', '41', 'EBSchiff', '30','EBFlugzeug', '20','99') cmc_Deckungsmerkmal
  ,decode(deckungstyp + 2*nur_liqui ,8177,'1',8188,'2',8179,'3',8190,'3',13240,'1',13242,'3','9') cmc_Deckungsposition
  ,to_char(s.Restkapital,'999999999999D99') Restkapital
  ,to_char(eb.BETRAG * (s.Restkapital / kd.restkapital_ist) ,'999999999999D99') Barwert
  ,substr(fx.TEXT,1,3) iso
  ,to_char(kd.gueltig_zum,'DD.MM.YYYY')
  ,substr(kdp.value,1,10) DepotNr
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
  ,kredit_daten_prop kdp
  ,propertydef pd	
where  eb.KREDIT_ID = kd.KREDIT_ID		
  and  kd.KREDIT_ID = kk.KREDIT_ID(+)	
  and  kd.WERTPAPIER_ID = wp.KREDIT_ID(+)	
  and  wp.KREDIT_ID = wpk.KREDIT_ID(+)	
  -- and  nvl(kk.FAELLIGKEIT, wpk.FAELLIGKEIT) = (select nvl(min(z.FAELLIGKEIT), max(v.FAELLIGKEIT))	
  --                        from	kredit_kond z, kredit_kond v, kredit_kond k
  --                        where	z.FAELLIGKEIT (+) >= bw.BERECHNUNGSSTICHTAG and
  --                               v.FAELLIGKEIT (+) < bw.BERECHNUNGSSTICHTAG and	
  --                               v.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
  --                               z.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
  --                               k.KREDIT_ID = nvl(wp.KREDIT_ID, kd.KREDIT_ID))	
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
  and  quelle.text in ('AMAVISCHF','AMAVIPFBG')
  and  bw.TRANSAKTION_ID = t.TRANSAKTION_ID
  and  sr.SZENARIO_BASIS_ID = sb.SZENARIO_BASIS_ID
  and  zs.SZENARIO_BEZEICHNUNG = 'normal'
  and  cmc_temp.maxdat =  kd.gueltig_zum
  and  kd.originator_id = 2
  and 
  (kdp.object_id = kd.kredit_id
   and 
   kdp.propertydefinition_id = pd.propertydef_id
   and
   pd.name = 'Depotnummer')
union all
select 
   decode(kd.originator_id,1,'009',2,'004') mandant
  ,decode(substr(quelle.text,1,2),'AM','84','AD','30','99')
  ,'00'
  ,substr(kd.extkey,1,12) ISIN
  ,'00'
  ,'0000000000'
  ,decode(substr(sb.KONFIGURATION_BEZEICHNUNG,1,12),'EBKommunal','11','EBHypotheken', '12', 'EBOEPGKomm', '41', 'EBSchiff', '30','EBFlugzeug', '20','99') cmc_Deckungsmerkmal
  ,decode(deckungstyp + 2*nur_liqui ,8177,'1',8188,'2',8179,'3',8190,'3',13240,'1',13242,'3','9') cmc_Deckungsposition
  ,to_char(s.Restkapital,'999999999999D99') Restkapital
  ,to_char(eb.BETRAG * (s.Restkapital / kd.restkapital_ist) ,'999999999999D99') Barwert
  ,substr(fx.TEXT,1,3) iso
  ,to_char(kd.gueltig_zum,'DD.MM.YYYY')
  ,substr(kdp.value,1,10) DepotNr
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
  ,kredit_daten_prop kdp
  ,propertydef pd	
where  eb.KREDIT_ID = kd.KREDIT_ID		
  and  kd.KREDIT_ID = kk.KREDIT_ID(+)	
  and  kd.WERTPAPIER_ID = wp.KREDIT_ID(+)	
  and  wp.KREDIT_ID = wpk.KREDIT_ID(+)	
  --and  nvl(kk.FAELLIGKEIT, wpk.FAELLIGKEIT) = (select nvl(min(z.FAELLIGKEIT), max(v.FAELLIGKEIT))	
  --                       from	kredit_kond z, kredit_kond v, kredit_kond k
  --                       where	z.FAELLIGKEIT (+) >= bw.BERECHNUNGSSTICHTAG and
  --                              v.FAELLIGKEIT (+) < bw.BERECHNUNGSSTICHTAG and	
  --                              v.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
  --                              z.KREDIT_KOND_ID (+) = k.KREDIT_KOND_ID and	
  --                              k.KREDIT_ID = nvl(wp.KREDIT_ID, kd.KREDIT_ID))	
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
  and  quelle.text in ('AMAVIFLUG')
  and  bw.TRANSAKTION_ID = t.TRANSAKTION_ID
  and  sr.SZENARIO_BASIS_ID = sb.SZENARIO_BASIS_ID
  and  zs.SZENARIO_BEZEICHNUNG = 'normal'
  and  cmc_temp.maxdat =  kd.gueltig_zum
  and  kd.originator_id = 2
  and
  (kdp.object_id = kd.kredit_id
   and 
   kdp.propertydefinition_id = pd.propertydef_id
   and
   pd.name = 'Depotnummer')
union all
select 
   decode(kd.originator_id,1,'009',2,'004') mandant
  ,decode(substr(quelle.text,1,2),'AM','84','AD','30','99')
  ,'00'
  ,substr(kd.extkey,1,12) ISIN
  ,'00'
  ,'0000000000'
  ,decode(substr(sb.KONFIGURATION_BEZEICHNUNG,1,12),'EBKommunal','11','EBHypotheken', '12', 'EBOEPGKomm', '41', 'EBSchiff', '30','EBFlugzeug', '20','99') cmc_Deckungsmerkmal
  ,decode(deckungstyp + 2*nur_liqui ,8177,'1',8188,'2',8179,'3',8190,'3',13240,'1',13242,'3','9') cmc_Deckungsposition
  ,to_char(s.Restkapital,'999999999999D99') Restkapital
  ,to_char(eb.BETRAG * (s.Restkapital / kd.restkapital_ist) ,'999999999999D99') Barwert
  ,substr(fx.TEXT,1,3) iso
  ,to_char(kd.gueltig_zum,'DD.MM.YYYY')
  ,substr(kdp.value,1,10) DepotNr
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
  ,kredit_daten_prop kdp
  ,propertydef pd	
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
  and  s.ASSIGNED = 1	
  and  eb.BARWERT_REPORT_ID = bw.BARWERT_REPORT_ID	
  and  eb.GESCHAEFTSTYP = kredittyp.ID	
  and  bw.SZENARIO_ID = zs.SZENARIO_ID	
  and  s.WAEHRUNG = fx.ID	
  and  bw.BARWERT_REPORT_ID = sr.BARWERT_REPORT_ID
  and  kd.KATEGORIE = kat.ID
  and  s.AKTIVPASSIV = ap.ID
  and  kd.QUELLE = quelle.ID
  and  quelle.text in ('AMAVIOEPG')
  and  bw.TRANSAKTION_ID = t.TRANSAKTION_ID
  and  sr.SZENARIO_BASIS_ID = sb.SZENARIO_BASIS_ID
  and  zs.SZENARIO_BEZEICHNUNG = 'normal'
  and  cmc_temp.maxdat =  kd.gueltig_zum
  and  kd.originator_id = 2
  and  s.Restkapital >0
  and
   (kdp.object_id = kd.kredit_id
   and 
   kdp.propertydefinition_id = pd.propertydef_id
   and
   pd.name = 'Depotnummer')
--order by kd.AKTENZEICHEN
;

--datumsmerker droppen
drop table cmc_temp;

spool off

exit