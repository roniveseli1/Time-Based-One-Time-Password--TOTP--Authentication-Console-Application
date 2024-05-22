# Time-Based-One-Time-Password--TOTP--Authentication-Console-Application



TOTP, i njohur si Time-based One-Time Password, eshte nje algoritem esencial per autentikimin e dyfaktoresh (2FA) qe prodhon fjalekalime unike numerike per nje perdorim te vetem. Keto fjalekalime gjenerohen permes nje algoritmi te standardizuar qe perdor kohen aktuale si nje input. Te qenit i bazuar ne kohe, TOTP eshte nje zgjerim i algoritmit HMAC-Based One-Time Password (HOTP), por ndryshon ne ate qe fjalekalimi varet nga koha aktuale dhe jo nga numerimi i transaksioneve. Kjo veçori e ben ate ideal per perdorim ne autentikimin e llogarive ne internet, ku siguria e shtuar eshte thelbesore. Fjalekalimet e gjeneruara jane te vlefshme per nje periudhe te shkurter kohore, duke i bere ato te sigurta dhe te pershtatshme per perdorim ne situata ku qasje offline eshte e nevojshme. 


Si funksionon TOTP?

TOTP, ose Time-based One-Time Password, funksionon duke kombinuar nje sekret te perbashket mes klientit dhe serverit me nje vlere kohore
per te gjeneruar fjalekalime te nje perdorimi qe jane te vlefshme per nje periudhe te kufizuar kohore, zakonisht 30 sekonda. Procesi i 
gjenerimit te ketyre fjalekalimeve perfshin disa hapa kyç:

Sekreti i perbashket: Nje çeles i ndare i sigurt perdoret nga te dyja palet (klienti dhe serveri). Ky çeles eshte themeli i algoritmit dhe duhet te mbahet konfidential.

Vlera kohore: Koha aktuale merr persiper rolin e nje inputs ne funksionin kriptografik. Koha zakonisht matet ne sekonda qe nga nje pike
reference caktuar (e.g., 1 Janar 1970) dhe me pas ndahet me nje hapesire kohore, siç jane 30 sekondat. Kjo prodhon nje vlere te quajtur "timestamp" qe rritet me kalimin e kohes dhe ndihmon ne gjenerimin e nje fjalekalimi te ri kur koha kalon.

Funksioni HMAC: Sekreti i perbashket dhe timestamp-i si input perdoren per te krijuar nje kod HMAC (Hash-Based Message Authentication Code). HMAC eshte nje lloj funksioni hash qe ofron integritet dhe autentikim te mesazheve, duke u siguruar qe te dhenat nuk jane modifikuar gjate transmetimit.

Gjenerimi i kodit te TOTP: Pasi te llogaritet HMAC, nje pjese e ketij rezultati zgjidhet dhe konvertohet ne nje numer te shkurter numerik. Ky numer eshte fjalekalimi i nje perdorimi qe perdoruesi mund te hyje per te vertetuar identitetin e tij.

Rigjenerimi i fjalekalimit: Pas skadimit te intervalit kohor, siç eshte 30 sekonda, nje fjalekalim i ri gjenerohet duke perdorur te njejten metode. Kjo siguron qe fjalekalimet te jene te vlefshme vetem per nje periudhe te shkurter dhe te ndihmoje ne mbrojtjen kunder sulmeve qe perpiqen te perdorin fjalekalimet e vjetra.

Verifikimi:Perdoruesi fut kodin e TOTP ne nje forme verifikimi. Serveri perserit te njejtin gjenerim te kodit duke perdorur sekretin e ndare dhe kohen aktuale. Nese kodi qe ka futur perdoruesi perputhet me kodin e gjeneruar nga serveri, autentikimi eshte i suksesshem.


Perdorimi i gjere i TOTP perfshin:

Sistemet Bankare: Siguron transaksione te sigurta online dhe qasje ne llogarite bankare.
Sherbimet e emailit: Mbron llogarite e emailit nga qasjet e paautorizuara, veçanerisht te dobishme kunder sulmeve te phishing.
Platformat Cloud: Siguron qasje te sigurt ne burime te ndryshme cloud, duke mbrojtur te dhenat e ruajtura dhe aplikacionet.
Aplikacionet e Shendetesise elektronike: Mbron informacionin e ndjeshem shendetesor, duke siguruar qe vetem personeli i autorizuar ka qasje ne te dhenat e pacienteve.
Sistemet e Menaxhimit te Identitetit dhe Qasjes (IAM): Perdoret per te rritur sigurine ne proceset e identifikimit dhe autorizimit ne infrastruktura korporative.



Serveri:
![Server](https://github.com/roniveseli1/Time-Based-One-Time-Password--TOTP--Authentication-Console-Application/assets/148273893/0833b675-363c-4c00-b2fe-78e70859aca3)

Klienti:
![client](https://github.com/roniveseli1/Time-Based-One-Time-Password--TOTP--Authentication-Console-Application/assets/148273893/b6405976-1b84-41f0-afa6-f5e1e24947e5)
