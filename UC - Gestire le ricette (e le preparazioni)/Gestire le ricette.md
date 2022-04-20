# Estratto dal documento Attori e UC Brevi

## GESTIRE LE RICETTE

Gestire le ricette significa inserire/modificare una ricetta o preparazione nel ricettario (associare tag, dare informazioni sulle tempistiche, specificare gli ingredienti, specificare la dose degli ingredienti, specificare le porzioni o quantità del prodotto risultante, inserire una sequenza di istruzioni. Si potranno eliminare ricette o preparazioni e crearne una copia.
Si potrà inoltre estrapolare una parte di una ricetta in una preparazione.1

## Indicazioni generali

Per questa parte dovrete svolgere l’analisi dei requisiti e successivamente la progettazione dell’UC “Gestire le ricette” che abbiamo associato, nella fase preliminare dell’ideazione, agli attori Chef e Cuoco.
Per effettuare l’analisi dovrete partire innanzitutto dal testo del progetto, che definisce cosa intendono per ricetta o preparazione i committenti del progetto e come si aspettano che una ricetta o preparazione siano strutturate. Per comprendere il processo con cui gli attori raggiungono il loro obiettivo potete analizzare invece le user stories introdotte qui sotto.
Sarà possibile svolgere l’analisi (requisiti + progettazione) completa, oppure, con una limitazione sul voto finale (max 24)2, optare per una versione ridotta in cui alcune richieste dei committenti non vengono considerate.

Per la versione ridotta è possibile dunque ignorare le seguenti richieste:

- estrapolazione di preparazioni da una ricetta (menzionata nell’UC breve qui sopra e nel testo del progetto)
- definizione, in una ricetta, di istruzioni strutturate (anche dette “complesse”), ossia “varianti”, “ripetizioni” e “raggruppamenti” (menzionati nel testo del progetto)

**Le user stories non menzionano esplicitamente queste caratteristiche, che, se si sceglie di realizzare, dovranno essere desunte dal testo del progetto.**
La possibilità di estrapolare preparazioni da ricette esistenti, e di inserire (modificare/eliminare) istruzioni strutturate all’interno di una ricetta o preparazione, potranno essere espresse nell’UC dettagliato come estensioni a partire dalla versione base.
È dunque possibile iniziare realizzando l’analisi della versione ridotta, e successivamente (un po’ come se fosse una ulteriore iterazione) andare ad aggiungere le caratteristiche della versione completa, estendendo di conseguenza la documentazione. In questo modo sarà possibile valutare in corso d’opera se “fermarsi” alla versione ridotta o completare l’analisi.
Se invece si è già certi di voler optare per la versione completa, si può realizzare direttamente l’UC dettagliato tenendo conto di tutte le caratteristiche richieste dal committente.

## User stories per l’UC “Gestire ricette”

### Cuoco Antonio

Quando penso a una nuova ricetta (che sia una ricetta vera e propria di un piatto finito, o una preparazione di base) inizio a scriverla di getto seguendo i passi da fare per arrivare al risultato finale. Se mi vengono in mente varianti della ricetta principale me lo annoto su un foglio a parte, di solito nel mio ricettario ogni variante è riportata a sé con un nome suo, che però richiama quello della ricetta di partenza. 
Alla fine della prima stesura rileggo tutto e inizio a segnare a parte gli ingredienti in modo da poter comporre la lista degli ingredienti e le dosi. Cerco poi di capire se posso servirmi di qualche preparazione già presente nel ricettario. Spesso a questo punto mi capita di creare varianti3 di preparazioni già presenti nel ricettario. 
Infine dettaglio i singoli passi  in maniera abbastanza precisa perchè mi è capitato di vedere eseguire dei disastri e poi prendermi la colpa perchè i vari passaggi non erano spiegati bene!  Come conseguenza adesso dettaglio ogni singolo passo con precisione. 
Per definire le tempistiche mi servono un po di tentativi quindi spesso le modifico 2 o 3 volte dopo le prime volte che ho eseguito una nuova ricetta. Non sono un amante delle classificazioni ma il nostro capo ci ha chiesto di classificare le ricette secondo un po di criteri in modo da poter comporre più velocemente i menù per gli eventi (es finger food, vegetariano, vegano etc.) e anche di segnalare quali parti possono essere preparate in anticipo se la cucina e il luogo di consumazione sono in posti diversi… questi dettagli tecnici sono quelli che mi annoiano di più e non sempre li azzecco al primo tentativo…

### Cuoca Giulia

Di solito metto una ricetta per iscritto in modo “ufficiale” (ossia, perché possa prepararla anche qualcuno diverso da me!) solo quando è ben collaudata.
Innanzitutto scrivo i passaggi principali, così come ce li ho in mente. Da lì ricavo anche la lista degli ingredienti, e se la conosco metto anche un’indicazione delle dosi e del numero di persone o della quantità che quelle dosi permettono di preparare. Non sempre però so le dosi esatte.
Anche per questo successivamente eseguo la ricetta una volta, e verifico man mano (qualche volta mi faccio aiutare!) se i passaggi che avevo scritto sono corretti, soprattutto se avevo dimenticato qualche passaggio o se ero stata imprecisa in qualche indicazione. Inoltre controllo le dosi, e misuro le quantità precise di quegli ingredienti che magari aggiungevo ad occhio.
A quel punto la ricetta è sostanzialmente finita, mi limito ad annotarla con le caratteristiche principali per permettere di schedarla.
Può capitare che in seguito decida di fare delle modifiche, se nell’eseguire la ricetta in un momento successivo mi accorgo di qualche accorgimento che può essere utile segnalare.
Mi succede a volte di creare un piatto che riusa delle preparazioni di uno che ho già nel ricettario. Allora scrivo delle ricette a parte per queste preparazioni, e nella ricetta del mio piatto le indico come ingredienti. Significa che quelle basi hanno senso anche al di fuori del piatto specifico, meglio che siano raccontate in ricette separate.
Estratto dal testo del progetto
1. Ricette e Preparazioni
Il ricettario contiene ricette e preparazioni; si tratta di concetti molto simili, la differenza è che una ricetta descrive come preparare un piatto da servire a tavola, mentre una preparazione descrive come realizzare un preparato da utilizzare in un’altra. 

Chef e cuochi possono inserire ricette o preparazioni nel ricettario; solo il proprietario di una ricetta o preparazione  (chi la ha inserita) può però eliminarla o modificarla, e può farlo solo fintanto che la ricetta non è in uso in alcun menù. Se un utente vuole modificare una propria ricetta attualmente in uso, o una ricetta di un altro proprietario, può crearne una copia da modificare liberamente.

Le ricette o preparazioni inserite sono inizialmente in stato di bozza, visibili solo dal proprio creatore; perché siano visibili a tutti (e quindi usabili o copiabili) devono essere pubblicate da chi le ha create. Una volta pubblicate non sarà più possibile modificarle, a meno di non “ritirarle dalla pubblicazione”, cosa possibile soltanto però se non sono utilizzate (in un menu o, se preparazioni, per un ingrediente utilizzato in un’altra ricetta).

Una ricetta o preparazione è innanzitutto caratterizzata da un nome, da un proprietario (chi l’ha inserita), opzionalmente da un autore (chi l’ha ideata inizialmente), e può essere accompagnata da una descrizione breve di ciò che realizza o da altre note che si ritiene possano essere di interesse. 
Gli utenti desiderano poter associare alle ricette tag scelti da loro allo scopo di organizzarle e reperirle con maggior facilità (esempi di tag: crudo, vegetariano, finger food, dessert, pasta).

Poiché per organizzare il lavoro è importante sapere quanto tempo ci vuole a cucinare qualcosa, chi scrive la ricetta o preparazione dovrà anche dare una stima sulle tempistiche. La specifica delle tempistiche può contenere indicazioni di natura molto diversa e non solo una quantità in ore o minuti. Ad esempio potrebbe contenere informazioni su:

- il tempo di attività concreta richiesto a chi la prepara
- il tempo totale di preparazione, ossia quanto passa dal momento in cui si inizia a svolgere la ricetta al momento in cui può essere servita (o utilizzata, nel caso di una preparazione). Questo include anche quelli che per il cuoco sono tempi morti: raffreddamenti in frigo, cotture lente,  riposo di impasti, ecc.
- il tempo di ultimazione, ossia quanto tempo serve in fase di servizio per ultimare il piatto; rientrano in questa categoria tutte le cose che devono essere fatte all’ultimo, che possono andare dal semplice impiattamento nel caso di piatti freddi, alla cottura e condimento della pasta, al taglio di un arrosto, ecc.

Per ogni ricetta o preparazione andranno poi specificati gli ingredienti. Gli ingredienti potranno essere ingredienti di base, scelti da un elenco che si immagina predefinito nel software e che dovrà essere il più possibile esaustivo, oppure preparati ottenuti tramite altre preparazioni. 
Degli ingredienti si dovrà poter specificare la dose. Inoltre, chi scrive la ricetta dovrà indicare con quelle dosi quante porzioni si realizzano o quale quantità di preparato risulterà. 

Naturalmente una ricetta o preparazione non sarebbe tale senza le istruzioni! In Cat & Ring le istruzioni di una ricetta o preparazione sono sempre divise in due sezioni, la parte che può essere realizzata in anticipo e quella che deve essere realizzata all’ultimo sul posto dell’evento. Naturalmente è possibile che una delle due sezioni sia vuota. 

Ogni parte contiene un elenco ordinato di istruzioni, in sequenza. L’utente dovrà indicare, quando aggiunge un’istruzione, dove si situa rispetto alle istruzioni già presenti, affinché sia chiaro l’ordine. 

Istruzioni strutturate 
Una istruzione può essere di diversi tipi:

- istruzione semplice: un singolo passaggio di svolgimento; chi scrive la ricetta ha libertà di inserire un qualsivoglia testo, ma l’invito è a separare i passaggi che corrispondono ad attività  distinte in istruzioni differenti.
- raggruppamento: una sequenza di istruzioni che vengono uno dopo l’altro e sono raggruppate per ragioni di leggibilità
- variante: formata da un’istruzione (che può essere semplice, o un raggruppamento) “principale” e da una “variante” (un’altra istruzione, anche qui semplice o raggruppamento). 
- ripetizione: un’istruzione (semplice o raggruppamento) con l’indicazione di una regola di ripetizione


Estrapolazione di preparazioni
Può succedere che si voglia “scorporare” una parte di una ricetta in una preparazione separata. Dovrà allora essere possibile per l’utente scegliere una sotto-sequenza di istruzioni della sua ricetta, un sottoinsieme di ingredienti con relativo dosaggio, e ottenere in modo semi-automatico di creare, a partire da queste informazioni, una preparazione.
Questo avrà due effetti che nel futuro sistema si vorrebbero automatici:

- reare una nuova preparazione con le istruzioni, gli ingredienti scelti e le relative dosi. L’utente dovrà poi completare le informazioni mancanti.
- Rimuovere le istruzioni scelte dalla ricetta corrente, diminuendo in modo opportuno le dosi (o eliminando del tutto se necessario) degli ingredienti usati per la nuova preparazione, e inserire la nuova preparazione fra gli ingredienti. Anche in questo caso è possibile che l’utente debba rivedere o completare.