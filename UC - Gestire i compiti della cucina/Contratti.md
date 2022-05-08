# Contratti per lo UC "Gestire i compiti in cucina"

**Pre-condizione generale**: l'utente è identificato come Chef *ch*

## 1.  apriFoglio(*servizio*: Servizio)

**Pre-condizioni:**
**Post-condizioni:**
- [**Se esiste** già un foglio associato al servizio ]
	- viene aperto # **Un po' tanto vago**
- [**Altrimenti**]
	- crea un'istanza *f* di FoglioRiepilogativo
	- f->pubblicato = no
	- riferisci il foglio *f* al servizio
	- visualizza a schermo i dettagli dell'oggetto *servizio*

## 2. apriTabelloneTurni()

**Pre-condizioni:**
- È in corso la modifica di un FoglioRiepilogativo *f*

**Post-condizioni**:

## 3. InserisciMansione(*ricetta?*: Ricetta, *preparazione?*: Preparazione)

**Pre-condizioni**:
- È in corso la modifica di un FoglioRiepilogativo *f*
- possono essere specificate ricetta o preparazione ma non entrambe

**Post-condizioni**:
-  crea un'istanza *m* di Mansione
-  [**Se** preparazione specificato] *m* si rifersice a preparazione
-  [**Se** ricetta specificato] *m* si rifersice a ricetta

## 4.1 nuovoCompito(*mansioneDiCucina*: Mansione, *cuocoAssegnato?*: Cuoco, *quantità?*: Testo, *porzione?*: Testo, *stimaTempo?*: Testo)

**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo *f*

**Post-condizioni:**
- è stata creata un'istanza *c* di Compito che **contiene** *mansioneDiCucina*
- c->completato = no;
- c->porzione = *porzione*
- c->quantità = *quantità*
- c->stimaTempo = *stimaTempo*
- c è associato a un'istanza di Ricetta **o** un'istanza di Preparazione
- [**Se** cuocoAssegnato specificato] c è assegnato a cuocoAssegnato
- f **contiene** c

## 4a.1 cambiaOrdineCompito(*compito*: Compito)
**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo f

**Post-condizioni:**
- L'ordine della lista di istanze di Compito contenute in *f* è aggiornato

## 4b.1 annotaCuocoNonNecessario(*compito*: Compito)

**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo f
- *compito* è **contenuto** in *f*

**Post-condizioni:**
- compito->cuoco = NON_NECESSARIO

## 4c.1 eliminaCompito(*compito*: Compito)
**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo f

**Post-condizioni:**
- *compito* viene rimosso da *f*

## 4d.1 modificaInfoCompito(*compito*: Compito, *quantità?*: Testo, *porzione?*: Testo, *stimaTempo?*: Testo, *cuoco?*: Cuoco)
**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo f
- compito è **contenuto** in *f*
- compito->completato = no

**Post-condizioni:**
- [**Se** *quantità*] compito->quantità = *quantità*
- [**Se** *porzione*] compito->porzione = *porzione*
- [**Se** *stimaTempo*] compito->stimaTempo = *stimaTempo*
- [**Se** *cuco*] compito è assegnato a *cuoco*

## 4e.1 resettaFoglio()
**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo f

**Post-condizioni:**
- ogni istanza di Compito contenuta in *f* viene eliminata

## 5 pubblicaFoglio()

**Pre-condizioni:**
- È in corso la modifica di un foglio riepilogativo f

**Post-condizioni:**
- f->pubblicato = sì

[^1]: di default potrebbe esserci una sezione con nome vuoto in maniera tale che le voci possano essere anche inserite fuori da una sezione senza gestire casi separati